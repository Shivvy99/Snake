package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SnakeObject extends Polygon implements KeyListener {
    static Point[] spawnPoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};

    private ArrayList<SnakeSegment> snakeSegments =
            new ArrayList<SnakeSegment>();
    private int direction = KeyEvent.VK_RIGHT;
    private Point[] snakePoints;

    public SnakeObject() {
        super(spawnPoints, new Point(250, 250), 0.0);
        snakePoints = spawnPoints.clone();
        snakeSegments.add(new SnakeSegment(new Point(250, 250)));

    }

    public void paint(Graphics brush) {

        brush.setColor(Color.GREEN);

        // Iterate through each segment of the snake
        for (SnakeSegment segment : snakeSegments) {
            Point position = segment.getPosition();

            // Determine the size of each segment
            int segmentSize = 10; // Example size, adjust as needed

            // Draw each segment as a rectangle (or circle if you prefer)
            brush.fillRect((int) position.x, (int) position.y, segmentSize,
                    segmentSize);
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
        // Calculate next position based on current direction
        // This is a simplified example. You'll need to adjust based on your grid/canvas size and direction.
        Point currentPosition = snakeSegments.get(0).getPosition();
        switch (direction) {
            case KeyEvent.VK_UP:
                return new Point(currentPosition.x, currentPosition.y - 5);
            case KeyEvent.VK_DOWN:
                return new Point(currentPosition.x, currentPosition.y + 5);
            case KeyEvent.VK_LEFT:
                return new Point(currentPosition.x - 5, currentPosition.y);
            case KeyEvent.VK_RIGHT:
                return new Point(currentPosition.x + 5, currentPosition.y);
            default:
                return currentPosition; // No change if no valid direction is found
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
                move();
                brush.setColor(Color.GREEN);
                brush.fillRect((int) position.x, (int) position.y, 25, 25);


                // Getters and setters for position

            }
            public void move() {
                if (!isGameOver()) {
                    switch (direction) {
                        case KeyEvent.VK_UP:
                            translate(0, -5);
                            break;
                        case KeyEvent.VK_DOWN:
                            translate(0, 5);
                            break;
                        case KeyEvent.VK_LEFT:
                            translate(-5, 0);
                            break;
                        case KeyEvent.VK_RIGHT:
                            translate(5, 0);
                            break;
                    }
                }
            }
            public Point getPosition() {
                return position;
            }

            public void setPosition(Point position) {
                this.position = position;
            }
        }


    }