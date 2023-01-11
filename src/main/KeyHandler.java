package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    int selectedRect;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                gp.restart = true;
                switch (gp.ui.commandNum) {
                    case 0:
                        gp.gameState = gp.playState;
                        break;
                    case 1:
                        gp.ui.commandNum = 0;
                        gp.gameState = gp.optionsState;
                        gp.ui.dimAdjust = false;
                        gp.ui.tileAdjust = false;
                        code = -1;
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }

        if (gp.gameState == gp.optionsState) {

            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {

                switch (gp.ui.commandNum) {
                    case 0:
                        gp.ui.dimAdjust = true;
                        gp.gameState = gp.adjustState;
                        selectedRect = 0;
                        code = -1;
                        break;
                    case 1:
                        gp.ui.tileAdjust = true;
                        gp.gameState = gp.adjustState;
                        selectedRect = 1;
                        code = -1;
                        break;
                    case 2:
                        gp.buttonM.mapButtonNum = new int[gp.maxScreenCol * gp.maxScreenRow][2];
                        gp.ui.dimAdjust = false;
                        gp.ui.tileAdjust = false;
                        gp.ui.commandNum = 0;
                        gp.gameState = gp.titleState;
                        break;
                }
            }
        }

        if (gp.gameState == gp.adjustState) {

            if (code == KeyEvent.VK_ENTER) {
                gp.ui.dimAdjust = false;
                gp.ui.tileAdjust = false;
                gp.gameState = gp.optionsState;
            }
            if (code == KeyEvent.VK_UP && selectedRect == 0) {
                gp.dimension++;
            }
            if (code == KeyEvent.VK_DOWN && selectedRect == 0) {
                gp.dimension--;
            }
            if (code == KeyEvent.VK_UP && selectedRect == 1) {
                gp.tileCount++;
            }
            if (code == KeyEvent.VK_DOWN && selectedRect == 1) {
                gp.tileCount--;
            }

        }

        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.submit = true;
            }
        }

        if (gp.gameState == gp.failState && gp.ui.failCounter > gp.ui.failLimit) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                gp.restart = true;
                switch (gp.ui.commandNum) {
                    case 0 -> gp.gameState = gp.playState;
                    case 1 -> {
                        gp.ui.commandNum = 0;
                        gp.gameState = gp.titleState;
                    }
                    case 2 -> System.exit(0);
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
