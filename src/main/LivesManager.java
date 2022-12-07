package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LivesManager {

    GamePanel gp;

    public final int totalLives = 3;
    public int lives = 3;

    public BufferedImage[] hearts = new BufferedImage[2];

    public LivesManager(GamePanel gp) {
        this.gp = gp;
        getLifeImage();
    }

    public void getLifeImage() {
        try {
            hearts[0] = ImageIO.read(getClass().getResourceAsStream("/lives/empty_x.png"));
            hearts[1] = ImageIO.read(getClass().getResourceAsStream("/lives/red_x.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        for (int i = gp.tileSize / 2; i < totalLives * gp.tileSize; i += gp.tileSize) {
            g2.drawImage(hearts[0], i, gp.tileSize / 3, 3 * gp.tileSize / 4, 3 * gp.tileSize / 4, null);
        }

        for (int i = gp.tileSize / 2; i < (totalLives - lives) * gp.tileSize; i += gp.tileSize) {
            g2.drawImage(hearts[1], i, gp.tileSize / 3, 3 * gp.tileSize / 4, 3 * gp.tileSize / 4, null);
        }

    }

}
