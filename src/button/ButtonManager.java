package button;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ButtonManager {

    GamePanel gp;

    public BufferedImage[] button = new BufferedImage[3];
    public int[][] mapButtonNum;

    public int tickCounter = 0;
    public int tickLimit = 6000;

    public ButtonManager(GamePanel gp) {
        this.gp = gp;

        mapButtonNum = new int[gp.maxScreenCol * gp.maxScreenRow][2];

        getButtonImage();
    }

    public void getButtonImage() {
        try {
            button[0] = ImageIO.read(getClass().getResourceAsStream("/button/button.png"));

            button[1] = ImageIO.read(getClass().getResourceAsStream("/button/compressed_button.png"));

            button[2] = ImageIO.read(getClass().getResourceAsStream("/button/flash_button.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int widthIncrement = (gp.screenWidth - gp.dimension * gp.tileSize) / (gp.dimension + 1);
        int heightIncrement = (gp.screenHeight - 2 * gp.tileSize - gp.dimension * gp.tileSize) / (gp.dimension + 1);

        int col = widthIncrement;
        int row = heightIncrement + gp.tileSize;

        int colButtons = 0;
        int rowButtons = 0;

        int index = 0;

        while (row < gp.maxScreenRow * gp.tileSize && rowButtons < gp.dimension) {

            int buttonNum = 0;
            for (int i = 0; i < gp.clickedButtons.size(); i++) {
                Point p = gp.clickedButtons.get(i);
                if (p.x == col && p.y == row) {
                    buttonNum = 1;
                    break;
                }
            }

            g2.drawImage(button[buttonNum], col, row, gp.tileSize, gp.tileSize, null);

            if (tickCounter < tickLimit & gp.correctButtons != null) {
                tickCounter++;
                for (int i = 0; i < gp.correctButtons.size(); i++) {
                    Point p = gp.correctButtons.get(i);
                    g2.drawImage(button[2], p.x, p.y, gp.tileSize, gp.tileSize, null);
                }
            }

            mapButtonNum[index][0] = col;
            mapButtonNum[index][1] = row;
            index++;

            col += widthIncrement + gp.tileSize;
            colButtons += 1;

            if (col >= gp.maxScreenCol * gp.tileSize || colButtons == gp.dimension) {
                colButtons = 0;
                rowButtons += 1;
                col = widthIncrement;
                row += heightIncrement + gp.tileSize;
            }
        }
        if (mapButtonNum[index][0] == 0) {
            mapButtonNum[index][0] = -1;
        }
    }

}
