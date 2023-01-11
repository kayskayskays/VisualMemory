package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CursorHandler implements MouseListener {

    GamePanel gp;
    Rectangle clickedRect;

    public CursorHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();

        if (gp.gameState == gp.titleState) {
            gp.restart = true;
            if (gp.ui.playRect.contains(p)) {
                gp.gameState = gp.playState;
            }
            if (gp.ui.optionsRect.contains(p)) {
                gp.ui.commandNum = 0;
                gp.ui.dimAdjust = false;
                gp.ui.tileAdjust = false;
                gp.gameState = gp.optionsState;
            }
            if (gp.ui.quitRect.contains(p)) {
                System.exit(0);
            }
        }

        if (gp.gameState == gp.playState) {

            int index = 0;

            while (gp.buttonM.mapButtonNum[index][0] != -1) {
                int[] map = gp.buttonM.mapButtonNum[index];
                index++;
                Rectangle rect = new Rectangle(map[0], map[1], gp.tileSize, gp.tileSize);
                if (rect.contains(p) && gp.buttonM.tickCounter >= gp.buttonM.tickLimit) {
                    boolean fin = true;
                    for (int i = 0; i < gp.clickedButtons.size(); i++) {
                        Point o = gp.clickedButtons.get(i);
                        if (o.x == map[0] && o.y == map[1]) {
                            gp.clickedButtons.remove(o);
                            fin = false;
                            break;
                        }
                    }
                    if (fin) {
                        gp.clickedButtons.add(new Point(map[0], map[1]));
                        break;
                    }
                }
                if (index == gp.maxScreenCol * gp.maxScreenRow) {
                    break;
                }
            }
            if (gp.ui.submissionRect != null) {
                if (gp.ui.submissionRect.contains(p)) {
                    gp.submit = true;
                }
            }
        }

        if (gp.gameState == gp.optionsState) {
            if (gp.ui.dimRect != null && gp.ui.tileRect != null && gp.ui.oTitleRect != null) {
                if (gp.ui.dimRect.contains(p)) {
                    gp.ui.dimAdjust = true;
                    clickedRect = gp.ui.dimRect;
                    gp.ui.commandNum = 0;
                    gp.gameState = gp.adjustState;
                    p = new Point();
                } else if (gp.ui.tileRect.contains(p)) {
                    gp.ui.tileAdjust = true;
                    clickedRect = gp.ui.tileRect;
                    gp.ui.commandNum = 1;
                    gp.gameState = gp.adjustState;
                    p = new Point();
                } else if (gp.ui.oTitleRect.contains(p)) {
                    gp.ui.commandNum = 0;
                    gp.gameState = gp.titleState;
                    p = new Point();
                }
            }
        }

        if (gp.gameState == gp.adjustState) {
            if (gp.ui.dimRect.contains(p) && clickedRect.contains(gp.ui.dimRect)) {
                gp.ui.dimAdjust = false;
                gp.gameState = gp.optionsState;
            }
            if (gp.ui.tileRect.contains(p) && clickedRect.contains(gp.ui.tileRect)) {
                gp.ui.tileAdjust = false;
                gp.gameState = gp.optionsState;
            }
            if (gp.ui.dimPlus != null) {
                if (gp.ui.dimPlus.contains(p)) {
                    gp.dimension++;
                }
                if (gp.ui.dimMinus.contains(p)) {
                    gp.dimension--;
                }
            }
            if (gp.ui.tilePlus != null) {
                if (gp.ui.tilePlus.contains(p)) {
                    gp.tileCount++;
                }
                if (gp.ui.tileMinus.contains(p)) {
                    gp.tileCount--;
                }
            }
        }

        if (gp.gameState == gp.failState && gp.ui.failCounter > gp.ui.failLimit) {
            gp.restart = true;
            if (gp.ui.retryRect.contains(p)) {
                gp.gameState = gp.playState;
            }
            if (gp.ui.titleRect.contains(p)) {
                gp.gameState = gp.titleState;
                gp.ui.commandNum = 0;
            }
            if (gp.ui.quitRect.contains(p)) {
                System.exit(0);
            }
            gp.restart = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
