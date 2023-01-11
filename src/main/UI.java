package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// TODO:
//  - save high scores

public class UI {

    GamePanel gp;
    Graphics2D g2;

    public Rectangle playRect, optionsRect, quitRect, submissionRect;
    public Rectangle retryRect, titleRect, fQuitRect;
    public Rectangle dimRect, tileRect, oTitleRect;
    public Rectangle dimPlus, dimMinus, tilePlus, tileMinus;

    public boolean dimAdjust = false, tileAdjust = false;

    public BufferedImage[] plusMinus = new BufferedImage[2];

    List<Point> finalButtons = new ArrayList<>();

    Font retro;

    int commandNum = 0;
    int failCounter = 0;
    int failLimit = 90;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/retro.ttf");
            retro = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        getPlusMinusImage();
    }

    public void getPlusMinusImage() {
        try {
            plusMinus[0] = ImageIO.read(getClass().getResourceAsStream("/options/plus.png"));

            plusMinus[1] = ImageIO.read(getClass().getResourceAsStream("/options/minus.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(retro);
        g2.setColor(Color.magenta);

        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            drawPlayScreen();
        }
        if (gp.gameState == gp.failState) {
            drawFailScreen();
        }
        if (gp.gameState == gp.optionsState || gp.gameState == gp.adjustState) {
            drawOptionsScreen();
        }
    }

    public Rectangle drawSubmissionButton(int x, int y, int width, int height) {

        Rectangle rect = new Rectangle(x, y, width, height);

        Color c = new Color(0, 200, 0);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 25, 25);

        c = new Color(0, 100, 0);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x + 3, y + 3, width - 6, height - 6, 19, 19);

        return rect;
    }

    public void drawTitleScreen() {

        int textWidth, textHeight;

        // background
        g2.setColor(new Color(40, 120, 70));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 54F));
        String text = "Visual Memory";
        int x = centredX(text);
        int y = gp.tileSize * 3;

        // shadow
        g2.setColor(Color.blue);
        g2.drawString(text, x + 7, y + 7);

        // main colour
        g2.setColor(Color.magenta);
        g2.drawString(text, x, y);

        // button image
        x = gp.screenWidth / 2 - gp.tileSize;
        y += gp.tileSize;
        g2.drawImage(gp.buttonM.button[2], x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        text = "PLAY";
        x = centredX(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text, x, y);
        textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        playRect = new Rectangle(gp.screenWidth / 2 - textWidth / 2, y - textHeight, textWidth, textHeight);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        text = "OPTIONS";
        x = centredX(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        optionsRect = new Rectangle(gp.screenWidth / 2 - textWidth / 2, y - textHeight, textWidth, textHeight);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        text = "QUIT";
        x = centredX(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        quitRect = new Rectangle(gp.screenWidth / 2 - textWidth / 2, y - textHeight, textWidth, textHeight);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawPlayScreen() {
        int textHeight;

        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 26F));
        String text = "Score: " + gp.points;
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text, gp.screenWidth - gp.tileSize / 2 - textWidth, 40);

        // submission button
        text = "Submit";
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        int subWidth = textWidth + gp.tileSize * 2;
        int subHeight = 3 * gp.tileSize / 4;
        int subX = gp.screenWidth / 2 - subWidth / 2;
        int subY = gp.screenHeight - 5 * subHeight / 4;

        submissionRect = drawSubmissionButton(subX, subY, subWidth, subHeight);
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        int textX = centredX(text);
        int textY = subY + textHeight;
        g2.setColor(Color.white);
        g2.drawString("Submit", textX, textY);
    }

    public void drawFailScreen() {

        if (failCounter == 0) {
            finalButtons.addAll(gp.correctButtons);
        }
        failCounter++;

        String text;
        int x, y, textWidth, textHeight;

        if (failCounter < failLimit) {
            gp.buttonM.tickCounter = gp.buttonM.tickLimit;
            gp.buttonM.draw(g2);
            for (Point p : finalButtons) {
                g2.drawImage(gp.buttonM.button[2], p.x, p.y, gp.tileSize, gp.tileSize, null);
            }
            gp.livesM.draw(g2);
        } else {
            finalButtons.clear();
            // background
            g2.setColor(new Color(120, 40, 70));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // game over text
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 54F));
            text = "GAME OVER";
            x = centredX(text);
            y = gp.tileSize * 3;

            // shadow
            g2.setColor(Color.blue);
            g2.drawString(text, x + 7, y + 7);

            // main colour
            g2.setColor(Color.magenta);
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
            text = "BEST (" + gp.dimension + " / " + gp.tileCount + "): ";
            x = centredX(text);
            y += gp.tileSize;
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // points
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
            text = "Score: " + gp.points;
            x = centredX(text);
            y += gp.tileSize * 2;
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // try again
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
            text = "Try again?";
            x = centredX(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            retryRect = new Rectangle(gp.screenWidth / 2 - textWidth / 2, y - textHeight, textWidth, textHeight);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            // quit to title
            text = "TITLE SCREEN";
            x = centredX(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            titleRect = new Rectangle(gp.screenWidth / 2 - textWidth / 2, y - textHeight, textWidth, textHeight);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            // quit
            text = "QUIT";
            x = centredX(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            fQuitRect = new Rectangle(gp.screenWidth / 2 - textWidth / 2, y - textHeight, textWidth, textHeight);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }

    }

    public void drawOptionsScreen() {

        int textWidth, textHeight;

        // background
        g2.setColor(new Color(50, 70, 120));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 54F));
        String text = "Options";
        int x = centredX(text);
        int y = gp.tileSize * 2;

        // shadow
        g2.setColor(Color.magenta);
        g2.drawString(text, x + 5, y + 5);

        // main colour
        g2.setColor(Color.orange);
        g2.drawString(text, x, y);

        // dimension
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        text = "GRID SIZE: " + gp.dimension;
        x = centredX(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        dimRect = new Rectangle(gp.screenWidth / 2 - textWidth / 2, y - textHeight, textWidth, textHeight);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }
        if (dimAdjust) {
            x += textWidth + gp.tileSize * 0.5;
            y -= textHeight;
            g2.drawImage(plusMinus[0], x, y, gp.tileSize, gp.tileSize, null);
            dimPlus = new Rectangle(x, y, gp.tileSize, gp.tileSize);
            x += gp.tileSize * 1.5;
            g2.drawImage(plusMinus[1], x, y, gp.tileSize, gp.tileSize, null);
            dimMinus = new Rectangle(x, y, gp.tileSize, gp.tileSize);
            y += textHeight;
        }

        // tile count
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        text = "TILES: " + gp.tileCount;
        x = centredX(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        tileRect = new Rectangle(gp.screenWidth / 2 - textWidth / 2, y - textHeight, textWidth, textHeight);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }
        if (tileAdjust) {
            x += textWidth + gp.tileSize * 0.5;
            y -= textHeight;
            g2.drawImage(plusMinus[0], x, y, gp.tileSize, gp.tileSize, null);
            tilePlus = new Rectangle(x, y, gp.tileSize, gp.tileSize);
            x += gp.tileSize * 1.5;
            g2.drawImage(plusMinus[1], x, y, gp.tileSize, gp.tileSize, null);
            tileMinus = new Rectangle(x, y, gp.tileSize, gp.tileSize);
            y += textHeight;
        }

        // title screen
        text = "TITLE SCREEN";
        x = centredX(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        oTitleRect = new Rectangle(gp.screenWidth / 2 - textWidth / 2, y - textHeight, textWidth, textHeight);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public int centredX(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
