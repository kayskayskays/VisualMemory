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

//    public void draw(Graphics2D g2) {
//
//        for (int i = gp.tileSize / 2; i < 3 * totalLives * gp.tileSize / 4; i += 3 * gp.tileSize / 4) {
//            g2.drawImage(hearts[0], i, 4, gp.tileSize / 2, gp.tileSize / 2, null);
//        }
//
//        for (int i = gp.tileSize / 2; i < 3 * (totalLives - lives) * gp.tileSize / 4; i += 3 * gp.tileSize / 4) {
//            g2.drawImage(hearts[1], i, 4, gp.tileSize / 2, gp.tileSize / 2, null);
//        }
//
//    }

}
