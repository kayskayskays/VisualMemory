package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UI {

    GamePanel gp;
    Graphics2D g2;

    public Rectangle playRect, recordsRect, quitRect, submissionRect;
    public Rectangle retryRect, titleRect, fQuitRect;

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
        text = "RECORDS";
        x = centredX(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        recordsRect = new Rectangle(gp.screenWidth / 2 - textWidth / 2, y - textHeight, textWidth, textHeight);
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

    public int centredX(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
