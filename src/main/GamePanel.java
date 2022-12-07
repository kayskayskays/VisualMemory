package main;

import button.ButtonGenerator;
import button.ButtonManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.Key;
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

    // FPS
    final int FPS = 60;

    // Clicked point
    public List<Point> clickedButtons;

    // Buttons
    public ButtonManager buttonM = new ButtonManager(this);
    public List<Point> correctButtons;

    // Lives
    public LivesManager livesM = new LivesManager(this);

    // UI
    public UI ui = new UI(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CursorHandler cursorH = new CursorHandler(this);

    Thread gameThread;
    public int points = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        clickedButtons = new ArrayList<>();

        Main.submit.addActionListener(e -> {
            if (correctButtons != null && clickedButtons != null) {
                correctButtons.sort(Comparator.comparingInt(p -> p.y));
                clickedButtons.sort(Comparator.comparingInt(p -> p.y));
                correctButtons.sort(Comparator.comparingInt(p -> p.x));
                clickedButtons.sort(Comparator.comparingInt(p -> p.x));

                if (correctButtons.equals(clickedButtons) && correctButtons.size() > 0) {
                    correctButtons = new ArrayList<>();
                    clickedButtons = new ArrayList<>();
                    points += 1;
                    Main.pointCounter.setText(Integer.toString(points));
                    buttonM.tickCounter = 0;
                } else {
                    livesM.lives--;
                }
            }
        });

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
        if (buttonM.mapButtonNum[0][0] != buttonM.mapButtonNum[1][0]) {
            if (correctButtons == null || correctButtons.size() == 0) {
                correctButtons = ButtonGenerator.gen(this);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

            ui.draw(g2);
            if (gameState == playState) {
                buttonM.draw(g2);
//                livesM.draw(g2);
            }

        g2.dispose();
    }
}