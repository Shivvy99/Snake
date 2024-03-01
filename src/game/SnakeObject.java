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
    private int direction = KeyEvent.VK_RIGHT;
    private Point[] snakePoints;
    private final int GAME_WIDTH = 500;
    private final int GAME_HEIGHT = 500;
    private BufferedImage headUpImage;
    private BufferedImage headDownImage;
    private BufferedImage headLeftImage;
    private BufferedImage headRightImage;
    private BufferedImage bodyImage;
    private BufferedImage bodyHorizontal;

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
        BufferedImage headImage;
        if (headUpImage != null) {
            switch (direction) {
                case KeyEvent.VK_UP:
                    headImage = headUpImage;
                    break;
                case KeyEvent.VK_DOWN:
                    headImage = headDownImage;
                    break;
                case KeyEvent.VK_LEFT:
                    headImage = headLeftImage;
                    break;
                case KeyEvent.VK_RIGHT:
                    headImage = headRightImage;
                    break;
                default:
                    headImage = headUpImage; // Default or initial direction
            }

            brush.drawImage(headImage, (int) snakeSegments.get(0).position.x, (int) snakeSegments.get(0).position.y, null);
        } else {
            brush.setColor(new Color(0, 255, 0, 128)); // Semi-transparent green
            brush.fillRect((int) snakeSegments.get(0).position.x, (int) snakeSegments.get(0).position.y, 25, 25);
        }

        // Draw the body image for each segment starting from the second one
        if (bodyImage != null) {
            for (int i = 1; i < snakeSegments.size(); i++) {

                BufferedImage bodyDirectionImage;
                switch (direction) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                        bodyDirectionImage = bodyImage;
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                        bodyDirectionImage = bodyHorizontal;
                        break;
                    default:
                        bodyDirectionImage = bodyImage; // Default or initial direction
                }

                // Check if the current direction image is different from the previous segment's direction image
                if (i > 1 && !bodyDirectionImage.equals(getDirectionImage(snakeSegments.get(i - 1).position, snakeSegments.get(i).position))) {
                    // If different, use the previous segment's direction image
                    bodyDirectionImage = getDirectionImage(snakeSegments.get(i - 1).position, snakeSegments.get(i).position);
                }

                brush.drawImage(bodyDirectionImage, (int) snakeSegments.get(i).position.x, (int) snakeSegments.get(i).position.y, null);
            }
        } else {
            for (int i = 1; i < snakeSegments.size(); i++) {
                brush.setColor(new Color(0, 255, 0, 128)); // Semi-transparent green
                brush.fillRect((int) snakeSegments.get(i).position.x, (int) snakeSegments.get(i).position.y, 25, 25);
            }
        }
    }

    private BufferedImage getDirectionImage(Point previousPosition, Point currentPosition) {
        double x = currentPosition.x - previousPosition.x;
        double y = currentPosition.y - previousPosition.y;

        if (x > 0) {
            return bodyHorizontal;  // Assuming bodyHorizontal is for right direction
        } else if (x < 0) {
            return bodyHorizontal;  // Assuming bodyHorizontal is for left direction
        } else if (y > 0) {
            return bodyImage;  // Assuming bodyImage is for down direction
        } else if (y < 0) {
            return bodyImage;  // Assuming bodyImage is for up direction
        } else {
            return bodyImage;  // Default or unchanged direction
        }
    }


    public boolean isGameOver() {
        if (getNextPosition().x < -25 || getNextPosition().x > GAME_WIDTH ||
                getNextPosition().y < -25 || getNextPosition().y >= GAME_HEIGHT) {

            EventQueue.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Game Over", "Snake", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Score: " + Snake.getScore(), "Snake", JOptionPane.INFORMATION_MESSAGE);

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

    public void appleCollision(Fruit apple) {
        if (apple.contains(snakeSegments.get(0).position)) {
            apple.position = Fruit.calculateSpawnPoint();
            extend();
            Snake.increaseScore();
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

    public ArrayList<SnakeSegment> getSnakeSegments() {

        return snakeSegments;
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

    }
}