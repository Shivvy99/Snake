package game;
/**
 * Shyam Ganapathy, Shiven Khanna
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class represents the snake object/element used by the user in our game. We extended the polygon class since this
 * element is drawn by the polygon class. Additionally, we implemented the keyListener interface to take in user input.
 * This class directly outputs the snake seen when our program is run.
 */
public class SnakeObject extends Polygon implements KeyListener {
    /**
     * This static array of type Point are the 4 points which make our snake object.
     */
    final static Point[] spawnPoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};
    /**
     * This arraylist of type SnakeSegment contains the snake segments which makes up the snake. This is essentially an
     * arraylist of the body of the snake.
     */
    protected ArrayList<SnakeSegment> snakeSegments = new ArrayList<>();
    /**
     * The direction which the snake is facing based on user input.
     */
    private int direction = KeyEvent.VK_RIGHT, lastDirection = KeyEvent.VK_RIGHT;
    /**
     * The width and height of the game screen.
     */
    private final int GAME_WIDTH = 500, GAME_HEIGHT = 500;
    /**
     * These are the different images we use which make up the snake object.
     */
    private BufferedImage headUpImage, headDownImage, headLeftImage,
            headRightImage, bodyImage, bodyHorizontal;
    /**
     * This is the speed at which the snake moves at.
     */
    protected static int speed = 25;

    /**
     * This is the constructor for the snake. It calls the super method in polygon which initializes our snake object.
     * This is the head of the snake. It then creates three snake segments which, as I said before, is the body of our
     * snake. After initialising these snake segments, they are added to the array. Finally, the method tries finding 6
     * different images which include the head of the snake as well as the body.
     */
    public SnakeObject() {
        super(spawnPoints, new Point(250, 250), 5.0);
        snakeSegments.add(new SnakeSegment(new Point(250, 250)));
        snakeSegments.add(new SnakeSegment(new Point(235, 250)));
        snakeSegments.add(new SnakeSegment(new Point(220, 250)));
        /**
         * If the image is not found in the images folder, our program will throw an exception.
         */
        try {
            headUpImage = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/head.png"));
            headDownImage = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/head_down.png"));
            headLeftImage = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/head_left.png"));
            headRightImage = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/head_right.png"));

            bodyImage = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/body.png"));
            bodyHorizontal = ImageIO.read(SnakeObject.class.getResourceAsStream("/Images/body_horizontal.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method moves the snake by updating the position of the segments. It uses the variable, previousPosition, and
     * another variable, currentPosition to move all the snakeSegments.
     */
    public void move() {
        Point previousPosition = snakeSegments.get(0).position;
        snakeSegments.get(0).position = getNextPosition();
        /**
         * This for loop moves all the snake segment objects to the previousPosition, which was initialized before this
         * for loop in the lines above. The for loop then sets the previousPosition to the currentPosition to move ALL
         * of the snakeSegment objects in the snakSegment array.
         */
        for (int i = 1; i < snakeSegments.size(); i++) {
            Point currentPosition = snakeSegments.get(i).position;
            snakeSegments.get(i).position = previousPosition;
            previousPosition = currentPosition;
        }
        lastDirection = direction;

    }

    /**
     * This method extends the snake by adding a new segment to its tail. The new segment is created at the same position as the
     * current tail segment, lengthening the snake. We created the variable tail to indicate that this new snakeSegment
     * is being added to the tail of the snake.
     */
    public void extend() {
        Point tail = snakeSegments.get(snakeSegments.size() - 1).position;
        snakeSegments.add(new SnakeSegment(new Point(tail.x, tail.y)));
    }

    /**
     * This method paints the snake object. It draws both the head and body segments of the snake based on their
     * positions/direction. It uses the image we imported from the images folder and overlays it onto the polygon object.
     *
     * @param brush The graphics used to paint.
     */
    public void paint(Graphics brush) {
        /**
         * Determines which image it will use for the head, and then using that image to draw over the polygon object.
         */
        BufferedImage headImage = getHeadImageForDirection();
        brush.drawImage(headImage, (int) snakeSegments.get(0).position.x, (int)
                snakeSegments.get(0).position.y, null);

        /**
         * For each of the snakeSegments, it will use the appropriate image and then draw it over the polygon object.
         */
        for (int i = 1; i < snakeSegments.size(); i++) {
            BufferedImage bodyDirectionImage = getBodyImageForSegment(i);
            brush.drawImage(bodyDirectionImage, (int) snakeSegments.get(i).position.x,
                    (int) snakeSegments.get(i).position.y, null);
        }
    }

    /**
     * Determines which head image will be drawn based on user input. By using a series of cases, if the user presses
     * the up arrow, the headUpImage will be used. If the user presses the down arrow, the headDownImage will be used.
     * etc... The default image is the headUpImage if the user presses no keys.
     *
     * @return The appropriate image orientation.
     */
    private BufferedImage getHeadImageForDirection() {
        return switch (direction) {
            case KeyEvent.VK_UP -> headUpImage;
            case KeyEvent.VK_DOWN -> headDownImage;
            case KeyEvent.VK_LEFT -> headLeftImage;
            case KeyEvent.VK_RIGHT -> headRightImage;
            default -> headUpImage;
        };
    }

    /**
     * Returns the appropriate snakeSegment image based on its orientation. The orientation is determined by the
     * positions of the current segment and the previous segment in the snake.
     *
     * @param segmentIndex The index of the current body segment.
     * @return The appropriate image orientation.
     */
    private BufferedImage getBodyImageForSegment(int segmentIndex) {
        Point previousPosition = snakeSegments.get(segmentIndex - 1).position;
        Point currentPosition = snakeSegments.get(segmentIndex).position;
        double xDiff = currentPosition.x - previousPosition.x;
        double yDiff = currentPosition.y - previousPosition.y;

        if (xDiff != 0) {
            if (bodyHorizontal != null) {
                return bodyHorizontal;
            } else {
                return getDefaultImage();
            }
        } else if (yDiff != 0) {
            if (bodyImage != null) {
                return bodyImage;
            } else {
                return getDefaultImage();
            }
        } else {
            if (bodyImage != null) {
                return bodyImage;
            } else {
                return getDefaultImage();
            }
        }
    }
    /**
     * Returns a default image with a size of 25x25 pixels of type ARGB.
     *
     * @return Default BufferedImage.
     */
    private BufferedImage getDefaultImage() {
        return new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Checks if the game is over by determining if the next position of the snake object's head is outside the length
     * and width of the game screen.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return getNextPosition().x < -25 || getNextPosition().x > GAME_WIDTH ||
                getNextPosition().y < -25 || getNextPosition().y >= GAME_HEIGHT;
    }
    /**
     * Calculates and returns the next position that the snake's head will move to based on its current
     * position/direction.
     *
     * @return The next position of the snake object's head.
     */
    private Point getNextPosition() {
        Point headPosition = snakeSegments.get(0).position;
        return switch (direction) {
            case KeyEvent.VK_UP ->
                    new Point(headPosition.x, headPosition.y - 25);
            case KeyEvent.VK_DOWN ->
                    new Point(headPosition.x, headPosition.y + 25);
            case KeyEvent.VK_LEFT ->
                    new Point(headPosition.x - 25, headPosition.y);
            case KeyEvent.VK_RIGHT ->
                    new Point(headPosition.x + 25, headPosition.y);
            default -> headPosition;
        };
    }
    /**
     * This method checks if the snake object has collided into a fruit by using the contains() method. If the snake
     * object contains the same points as the parameter, apple, it will create a new apple, extend the length of the
     * snake, and increase the score by +1.
     *
     *  @param apple passes in an apple object which is used to see if it has been collided in to by the snake object.
     */
    public void appleCollision(Fruit apple) {
        if (apple.contains(snakeSegments.get(0).position)) {
            apple.position = apple.calculateSpawnPoint();
            extend();
            Snake.increaseScore();
        }
    }
    /**
     * This method checks if the snake object has collided into a powerup by using the contains() method. If the snake
     * object contains the same points as the powerup, it will determine what type of powerup the parameter is, and then
     * it will apply the powerups effect accordingly.
     *
     *  @param powerup passes in a powerup which is used to see if it has collided in to.
     */
    public void powerUpCollision(Powerups powerup) {
        if (powerup.contains(snakeSegments.get(0).position)) {
            /**
             * This checks which powerup the parameter is by using getType() and then it will aplly the following
             * effect.
             */
            switch (powerup.getType()) {
                case SHORTEN:
                    Powerups.ShortenSnake.apply(this);
                    break;
                case SPEED_BOOST:
                    Powerups.IncreaseSpeed.apply();
                    break;
                case EXTRA_POINTS:
                    Powerups.ExtraPoints.apply();
                    break;
            }
            /**
             * These lines will ensure the powerup will be created in a new, random location after being collided in to.
             */
            powerup.position = powerup.calculateSpawnPoint();
            powerup.setPowerupImage();
        }
    }

    /**
     * This method checks to see if the snake has collided with itself. It uses a for loop to check if any of the snake
     * segments has the same points as another snake segment by calling the pointCollision method below.
     *
     * @return true if there is a collision, false otherwise.
     */
    public boolean snakeObjectCollision() {
        for (int i = 1; i < snakeSegments.size(); i++) {
            if (pointCollision(snakeSegments.get(0).position, snakeSegments.get(i).position)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if two points are equal to each other, indicating they have collided.
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @return true if the points collide, false otherwise.
     */
    private boolean pointCollision(Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }

    /**
     * This void method is needed for when a key is typed.
     *
     * @param e The KeyEvent representing the key typed event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
    /**
     * This void method is needed for when a key, which was previously pressed, is released.
     *
     * @param e The KeyEvent representing the key typed event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * When an arrow key is pressed, it will be set in that direction.
     *
     * @param e The KeyEvent representing the key press event.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
    /**
    * This series of if else statements updates the direction based on the pressed key, and avoids opposite direction
     * changes.
    */
        if ((key == KeyEvent.VK_RIGHT) && (lastDirection != KeyEvent.VK_LEFT)) {
            direction = key;
        } else if ((key == KeyEvent.VK_LEFT) && (lastDirection != KeyEvent.VK_RIGHT)) {
            direction = key;
        } else if ((key == KeyEvent.VK_UP) && (lastDirection != KeyEvent.VK_DOWN)) {
            direction = key;
        } else if ((key == KeyEvent.VK_DOWN) && (lastDirection != KeyEvent.VK_UP)) {
            direction = key;
        }
    }

    /**
     * This inner class creates the snake segment.
     */
    public class SnakeSegment {
        /**
         * This is the position where the snakeSegment is located.
         */
        private Point position;

        /**
         * Constructs a new snake segment with the specified position.
         *
         * @param position The initial position of the snake segment.
         */
        public SnakeSegment(Point position) {
            this.position = position;

        }
    }
}