package game;

import java.awt.Point.*;
// 
public class SnakeObject extends Polygon {
    final static Point[] snakePoints = {new Point(0, 0), new Point(0, 10),
            new Point(10, 10), new Point(10, 0)};

    public SnakeObject() {
        super(snakePoints, new Point(0, 0), 0);
    }

    public void paint() {

        double[] xPoints = new double[snakePoints.length];
        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] = snakePoints[i].x;
        }
    }
}

