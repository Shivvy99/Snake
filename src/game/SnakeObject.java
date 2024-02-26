package game;

import java.awt.*;
import javax.swing.*;


public class SnakeObject extends Polygon {
    final static Point[] snakePoints = {new Point(0, 0), new Point(0, 10),
            new Point(10, 10), new Point(10, 0)};

    public SnakeObject() {
        super(snakePoints, new Point(0, 0), 0);
    }

    public void paint(Graphics g) {
        Point[] snakePoints = this.getPoints();

        int[] xPoints = new int[snakePoints.length];
        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] = (int) snakePoints[i].getX();
        }

        int[] yPoints = new int[snakePoints.length];
        for (int i = 0; i < yPoints.length; i++) {
            yPoints[i] = (int) snakePoints[i].getY();
        }

        g.drawPolygon(xPoints, yPoints, snakePoints.length);
    }
}

