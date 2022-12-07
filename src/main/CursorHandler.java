package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CursorHandler implements MouseListener {

    GamePanel gp;

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
            if (gp.ui.playRect.contains(p)) {
                gp.gameState = gp.playState;
            }
            if (gp.ui.recordsRect.contains(p)) {
                // records
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
            if (gp.ui.submissionRect.contains(p)) {
                gp.submit = true;
            }
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
