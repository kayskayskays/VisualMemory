package main;

import button.ButtonGenerator;
import button.ButtonManager;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 12;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int failState = 2;
    public final int optionsState = 3;
    public final int adjustState = 4;

    public int dimension = 7;
    public int tileCount = 4;

    // FPS
    final int FPS = 60;

    // Buttons
    public List<Point> clickedButtons;
    public List<Point> correctButtons;

    // Buttons
    public ButtonManager buttonM = new ButtonManager(this);

    // Lives
    public LivesManager livesM = new LivesManager(this);

    // UI
    public UI ui = new UI(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CursorHandler cursorH = new CursorHandler(this);

    public int points = 0;
    public boolean submit = false;
    public boolean restart = true;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        clickedButtons = new ArrayList<>();
        correctButtons = new ArrayList<>();

        addMouseListener(cursorH);

        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        restartGame();
        if (buttonM.mapButtonNum[0][0] != buttonM.mapButtonNum[1][0]) {
            if (correctButtons == null || correctButtons.size() == 0) {
                correctButtons = ButtonGenerator.gen(this);
            }
            submission();
            submit = false;
        }
        if (livesM.lives == 0) {
            gameState = failState;
        }
    }

    private void restartGame() {
        if (restart) {
            ui.failCounter = 0;
            restart = false;
            points = 0;
            livesM.lives = 3;
            submit = false;
            buttonM.tickCounter = 0;
            clickedButtons = new ArrayList<>();
            correctButtons = new ArrayList<>();
        }
    }

    public void submission() {
        if (submit) {
            if (correctButtons != null && clickedButtons != null) {
                correctButtons.sort(Comparator.comparingInt(p -> p.y));
                clickedButtons.sort(Comparator.comparingInt(p -> p.y));
                correctButtons.sort(Comparator.comparingInt(p -> p.x));
                clickedButtons.sort(Comparator.comparingInt(p -> p.x));

                if (correctButtons.equals(clickedButtons) && correctButtons.size() > 0) {
                    correctButtons = new ArrayList<>();
                    clickedButtons = new ArrayList<>();
                    points += 1;
                    buttonM.tickCounter = 0;
                } else {
                    livesM.lives--;
                }
            }
            submit = false;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        ui.draw(g2);
        if (gameState == playState) {
            buttonM.draw(g2);
            livesM.draw(g2);
        }
        if (gameState == failState) {
            correctButtons.clear();
        }

        g2.dispose();
    }
}
