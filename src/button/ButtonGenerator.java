package button;

import main.GamePanel;

import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class ButtonGenerator {

    public static List<Point> gen(GamePanel gp) {

        List<Point> correctPoints = new ArrayList<>();
        int[][] map = gp.buttonM.mapButtonNum;
        int index;

        while (correctPoints.size() < 4) {
            index = 0;
            Random rnd = new Random();

            int rndX = rnd.nextInt(gp.screenWidth);
            int rndY = rnd.nextInt(gp.screenHeight);

            int minDist = -1;

            Point correctPoint = null;
            boolean duplicate = false;

            while (map[index][0] != -1) {

                int xDist = Math.abs(map[index][0] - rndX);
                int yDist = Math.abs(map[index][1] - rndY);
                int dist = (int) Math.sqrt(xDist * xDist + yDist * yDist);

                if (minDist == -1 || dist < minDist ) {
                    minDist = dist;
                    correctPoint = new Point(map[index][0], map[index][1]);
                }
                index++;
                if (index == gp.maxScreenCol * gp.maxScreenRow) {
                    break;
                }
            }

            if (correctPoint != null) {
                for (Point point : correctPoints) {
                    if (correctPoint.x == point.x && correctPoint.y == point.y) {
                        duplicate = true;
                        break;
                    }
                }

                if (!duplicate) {
                    correctPoints.add(correctPoint);
                }
            }
        }
        return correctPoints;
    }
}
