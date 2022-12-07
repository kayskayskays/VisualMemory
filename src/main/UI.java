package main;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Graphics2D g2;

    public Rectangle playRect, recordsRect, quitRect, submission;

    Font retro;

    int commandNum = 0;

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
            g2.drawImage(gp.buttonM.button[0], gp.tileSize / 2, gp.tileSize / 4, gp.tileSize / 3, gp.tileSize / 3, null);
            g2.setColor(Color.black);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
            g2.drawString("= " + gp.points, gp.tileSize, 26);

            // submission button
            String text = "Submit";

            int subWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight() + gp.tileSize * 2;
            int subHeight = 3 * gp.tileSize / 4;
            int subX = gp.screenWidth / 2 - subWidth / 2;
            int subY = gp.screenHeight - 5 * subHeight / 4;

            drawSubmissionButton(subX, subY, subWidth, subHeight);

            int textX = centredX(text);
            int textY = subY + (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            g2.setColor(Color.white);
            g2.drawString("Submit", textX, textY);
        }
    }

    public void drawSubmissionButton(int x, int y, int width, int height) {

        Color c = new Color(0, 200, 0);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 25, 25);

        c = new Color(0, 100, 0);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x + 3, y + 3, width - 6, height - 6, 19, 19);
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
        y += gp.tileSize * 2;
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

    public int centredX(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
