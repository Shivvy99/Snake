package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.Timer;

public class SnakeObject extends Polygon implements KeyListener{
    final static Point[] spawnPoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};
    private ArrayList<Point> snakePoints;
    private int direction = KeyEvent.VK_RIGHT;
    private int speed = 1;
    private Timer moveTimer;


    public SnakeObject() {
        super(spawnPoints, new Point(232, 232), 0.0);
        snakePoints = new ArrayList<Point>();
        for(Point point : spawnPoints) {
            snakePoints.add(point);
        }
        moveTimer = new Timer(speed, e -> move());
        moveTimer.start();
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

        switch (direction) {
            case KeyEvent.VK_UP:
                translate(0, 20);
                break;
            case KeyEvent.VK_DOWN:
                translate(0,-20);
                break;
            case KeyEvent.VK_LEFT:
                translate(-20,0);
                break;
            case KeyEvent.VK_RIGHT:
                translate(20,0);
                break;
        }

    }

    private void translate(int x, int y) {
        for (Point p : snakePoints) {
            p.x += x;
            p.y += y;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_RIGHT) && (direction != KeyEvent.VK_LEFT)) {
            direction = KeyEvent.VK_RIGHT;
        } else if ((key == KeyEvent.VK_LEFT) && (direction != KeyEvent.VK_RIGHT)) {
            direction = KeyEvent.VK_LEFT;
        } else if ((key == KeyEvent.VK_UP) && (direction != KeyEvent.VK_DOWN)) {
            direction = KeyEvent.VK_UP;
        } else if ((key == KeyEvent.VK_DOWN) && (direction != KeyEvent.VK_UP)) {
            direction = KeyEvent.VK_DOWN;
        }
    }



}
