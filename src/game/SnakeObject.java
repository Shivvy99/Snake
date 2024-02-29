package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SnakeObject extends Polygon implements KeyListener {
    static Point[] spawnPoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};

    private ArrayList<SnakeSegment> snakeSegments = new ArrayList<>();
    private ArrayList<Point> positionsHistory = new ArrayList<>();

    private int direction = KeyEvent.VK_RIGHT;
    private Point[] snakePoints;

    public SnakeObject() {
        super(spawnPoints, new Point(250, 250), 0.0);
        snakePoints = spawnPoints.clone();
        snakeSegments.add(new SnakeSegment(new Point(250, 250)));
        snakeSegments.add(new SnakeSegment(new Point(235, 250))); // Middle
        snakeSegments.add(new SnakeSegment(new Point(220, 250))); // Tail

    }

    public void move() {
        Point previousPosition = snakeSegments.get(0).getPosition();

        // Move the head based on the current direction.
        snakeSegments.get(0).setPosition(getNextPosition());

        // Then, for each of the body segments...
        for (int i = 1; i < snakeSegments.size(); i++) {
            // Store the current position of this segment.
            Point currentPosition = snakeSegments.get(i).getPosition();

            // Move this segment to the previous position of the segment in front of it.
            snakeSegments.get(i).setPosition(previousPosition);

            // Update previousPosition for the next segment to use.
            previousPosition = currentPosition;
        }
    }


    public void paint(Graphics brush) {

        brush.setColor(Color.GREEN);

        // Iterate through each segment of the snake
        for (SnakeSegment segment : snakeSegments) {
            segment.paint(brush);
        }
    }






    public boolean isGameOver() {
        if (snakePoints[0].x < 0 || snakePoints[0].x > 500
                || snakePoints[1].y < 0 || snakePoints[1].y > 500) {
            return true;
        }
        return false;
    }

    private Point getNextPosition() {
        Point headPosition = snakeSegments.get(0).getPosition();
        switch (direction) {
            case KeyEvent.VK_UP: return new Point(headPosition.x, headPosition.y - 25);
            case KeyEvent.VK_DOWN: return new Point(headPosition.x, headPosition.y + 25);
            case KeyEvent.VK_LEFT: return new Point(headPosition.x - 25, headPosition.y);
            case KeyEvent.VK_RIGHT: return new Point(headPosition.x + 25, headPosition.y);
            default: return headPosition; // If no direction is pressed, don't move
        }
    }


        @Override
        public void keyTyped (KeyEvent e){
        }
        @Override
        public void keyReleased (KeyEvent e){
        }

        public void keyPressed (KeyEvent e){
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


            public Point getPosition() {
                return position;
            }

            public void setPosition(Point position) {
                this.position = position;
            }
        }


    }