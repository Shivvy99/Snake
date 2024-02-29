package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SnakeObject extends Polygon implements KeyListener {
    final static Point[] spawnPoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};
    private ArrayList<SnakeSegment> snakeSegments = new ArrayList<>();
    private ArrayList<Point> positionsHistory = new ArrayList<>();
    private int direction = KeyEvent.VK_RIGHT;
    private Point[] snakePoints;
    private final int GAME_WIDTH = 500;
    private final int GAME_HEIGHT = 500;
    public SnakeObject() {
        super(spawnPoints, new Point(250, 250), 5.0);
        snakePoints = spawnPoints.clone();
        snakeSegments.add(new SnakeSegment(new Point(250, 250)));
        snakeSegments.add(new SnakeSegment(new Point(235, 250))); // Middle
        snakeSegments.add(new SnakeSegment(new Point(220, 250))); // Tail
    }

    public void move() {
        Point previousPosition = snakeSegments.get(0).position;
        snakeSegments.get(0).position = getNextPosition();

        for (int i = 1; i < snakeSegments.size(); i++) {
            Point currentPosition = snakeSegments.get(i).position;
            snakeSegments.get(i).position = previousPosition;
            previousPosition = currentPosition;
        }
    }

    public void paint(Graphics brush) {
        brush.setColor(Color.GREEN);
        for (SnakeSegment segment : snakeSegments) {
            segment.paint(brush);
        }
    }

    public boolean isGameOver() {
        if (getNextPosition().x < -25 || getNextPosition().x > GAME_WIDTH ||
                getNextPosition().y < -25 || getNextPosition().y >= GAME_HEIGHT) {

            EventQueue.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Game Over", "Snake", JOptionPane.INFORMATION_MESSAGE);
            });
            return true;
        }
        return false;
    }

    private Point getNextPosition() {
        Point headPosition = snakeSegments.get(0).position;
        switch (direction) {
            case KeyEvent.VK_UP:
                return new Point(headPosition.x, headPosition.y - 25);
            case KeyEvent.VK_DOWN:
                return new Point(headPosition.x, headPosition.y + 25);
            case KeyEvent.VK_LEFT:
                return new Point(headPosition.x - 25, headPosition.y);
            case KeyEvent.VK_RIGHT:
                return new Point(headPosition.x + 25, headPosition.y);
            default:
                return headPosition;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

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

    public class SnakeSegment {
        private Point position;

        public SnakeSegment(Point position) {
            this.position = position;

        }

        private void translate(int x, int y) {
            position.x = position.x + x;
            position.y = position.y + y;
        }

        public void paint(Graphics brush) {
            brush.setColor(Color.GREEN);
            brush.fillRect((int) position.x, (int) position.y, 25, 25);
        }

    }
}