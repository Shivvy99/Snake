package game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SnakeObject extends Polygon implements KeyListener {
    final static Point[] spawnPoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};
    private ArrayList<SnakeSegment> snakeSegments = new ArrayList<>();
    private ArrayList<Point> positionsHistory = new ArrayList<>();
    private Point[] snakePoints;
    private int direction = KeyEvent.VK_RIGHT;
    private final int GAME_WIDTH = 500, GAME_HEIGHT = 500;
    private BufferedImage headUpImage, headDownImage, headLeftImage,
            headRightImage, bodyImage, bodyHorizontal;

    public SnakeObject() {
        super(spawnPoints, new Point(250, 250), 5.0);
        snakePoints = spawnPoints.clone();
        snakeSegments.add(new SnakeSegment(new Point(250, 250)));
        snakeSegments.add(new SnakeSegment(new Point(235, 250))); // Middle
        snakeSegments.add(new SnakeSegment(new Point(220, 250))); // Tail
        try {
            headUpImage = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/head.png"));
            headDownImage = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/head_down.png"));
            headLeftImage = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/head_left.png"));
            headRightImage = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/head_right.png"));

            // Load your body images for different directions
            bodyImage = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/body.png"));
            bodyHorizontal = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/body_horizontal.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void extend() {
        Point tail = snakeSegments.get(snakeSegments.size() - 1).position;
        snakeSegments.add(new SnakeSegment(new Point(tail.x, tail.y)));
    }

    public void paint(Graphics brush) {
        // Determine head image based on the direction
        BufferedImage headImage = getHeadImageForDirection();
        brush.drawImage(headImage, (int) snakeSegments.get(0).position.x, (int)
                snakeSegments.get(0).position.y, null);

        // Draw the body image for each segment starting from the second one
        for (int i = 1; i < snakeSegments.size(); i++) {
            BufferedImage bodyDirectionImage = getBodyImageForSegment(i);
            brush.drawImage(bodyDirectionImage, (int) snakeSegments.get(i).position.x,
                    (int) snakeSegments.get(i).position.y, null);
        }
    }

    private BufferedImage getHeadImageForDirection() {
        if (headUpImage == null) {
            return new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB); // Return an empty image if head images are not loaded
        }
        switch (direction) {
            case KeyEvent.VK_UP:
                return headUpImage;
            case KeyEvent.VK_DOWN:
                return headDownImage;
            case KeyEvent.VK_LEFT:
                return headLeftImage;
            case KeyEvent.VK_RIGHT:
                return headRightImage;
            default:
                return headUpImage;
        }
    }

    private BufferedImage getBodyImageForSegment(int segmentIndex) {
        Point previousPosition = snakeSegments.get(segmentIndex - 1).position;
        Point currentPosition = snakeSegments.get(segmentIndex).position;
        double xDiff = currentPosition.x - previousPosition.x;
        double yDiff = currentPosition.y - previousPosition.y;

        if (xDiff > 0 || xDiff < 0) {
            // Horizontal body image
            return bodyHorizontal != null ? bodyHorizontal : new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
        } else if (yDiff > 0 || yDiff < 0) {
            // Vertical body image
            return bodyImage != null ? bodyImage : new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
        } else {
            // Default body image
            return bodyImage != null ? bodyImage : new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
        }
    }


    public boolean isGameOver() {
        if (getNextPosition().x < -25 || getNextPosition().x > GAME_WIDTH ||
                getNextPosition().y < -25 || getNextPosition().y >= GAME_HEIGHT) {
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

    public void appleCollision(Fruit apple) {
        if (apple.contains(snakeSegments.get(0).position)) {
            apple.position = Fruit.calculateSpawnPoint();
            extend();
            Snake.increaseScore();
        }
    }

    public boolean snakeObjectCollision() {
        Point head = snakeSegments.get(0).position;
        for (int i = 1; i < snakeSegments.size(); i++) {
            if (pointCollision(head, snakeSegments.get(i).position)) {
                return true;
            }
        }
        return false;
    }

    private boolean pointCollision (Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
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
    }
}