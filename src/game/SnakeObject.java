package game;

import java.awt.*;
import java.awt.event.*;

public class SnakeObject extends Polygon implements KeyListener{
    final static Point[] spawnPoints = {new Point(0, 0), new Point(0,
            30),
            new Point(30, 30), new Point(30, 0)};
    private Point[] snakePoints;
    private static boolean north = true, east = false, south = false, west = false;



    public SnakeObject() {
        super(spawnPoints, new Point(300, 300), 0.0);
        snakePoints = spawnPoints;
    }

    public void paint(Graphics brush) {
        Point[] snakePoints = this.getPoints();

        int[] xPoints = new int[snakePoints.length];
        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] = (int) snakePoints[i].getX();
        }

        int[] yPoints = new int[snakePoints.length];
        for (int i = 0; i < yPoints.length; i++) {
            yPoints[i] = (int) snakePoints[i].getY();
        }
        brush.fillPolygon(xPoints, yPoints, xPoints.length);
    }

    public void move() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.equals("VK_d") && !west) {
            east = true; north = false; south = false;
        } else if (e.equals("VK_a") && !east) {
            west = true; north = false; south = false;
        } else if (e.equals("VK_w") && !south) {
            north = true; east = false; west = false;
        } else if (e.equals("VK_s") && !north) {
            south = true; east = false; west = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}