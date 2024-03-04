package game;
/**
 * Shyam Ganapathy, Shiven Khanna
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;
/**
 * This class represents the powerups used in our game. When the snake collides into a powerup, different effects are
 * applied to the snake. We extended the polygon class since this element is drawn using the polygon class. Additionally,
 * we implemented an interface we created named Consumables, which is also used by the Fruit class.This class directly
 * outputs the different powerups which pop up randomly during the game as well as the different effects applied to the
 * snake.
 */
public class Powerups extends Polygon implements Consumables {

    /**
     * This static array of type Point are the 4 points which make the powerup.
     */
    final static Point[] sizePoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};

    /**
     * Is the powerup image.
     */
    private BufferedImage powerupImage;

    /**
     * The powerup enum type (shorten, speedboost, bonus).
     */
    private PowerupType type;

    /**
     * An instance of the snake game.
     */
    private static Snake snakeGame;

    /**
     * Creates an array of the enum type, PowerupType.
     */
    static PowerupType[] types = PowerupType.values();

    /**
     * Creates a new Powerups instance for the snake game. It calls the super contructor from the polygon superclass,
     * and then outputs the image using setPowerupImage().
     *
     * @param snakeGame The instance of the Snake game.
     */
    public Powerups(Snake snakeGame) {
        super(sizePoints, calculateSpawnPoint(), 0.0);
        setPowerupImage();
        this.snakeGame = snakeGame;
    }

    /**
     * Uses the random class to randomly select a number, 1-3 (length of enum types) and then outputs the appropriate
     * powerUpType. The method then uses the image of the according powerup.
     */
    public void setPowerupImage() {
        Random rand = new Random();
        this.type = types[rand.nextInt(types.length)];

        try {
            switch (this.type) {
                case SHORTEN:
                    powerupImage =
                            ImageIO.read(getClass().getResourceAsStream(
                                    "/Images/shorten.jpeg"));
                    break;
                case SPEED_BOOST:
                    powerupImage =
                            ImageIO.read(getClass().getResourceAsStream(
                                    "/Images/speedBoost.png"));
                    break;
                case EXTRA_POINTS:
                    powerupImage =
                            ImageIO.read(getClass().getResourceAsStream(
                                    "/Images/bonus.jpeg"));
                    break;
                default:
                    powerupImage = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            powerupImage = null;
        }
    }

    /**
     * This method uses the Math class to randomly output the powerup on the screen.
     *
     *  @return The calculated spawn point.
     */
    public static Point calculateSpawnPoint() {
        int gridSize = 25;
        int maxX = (500 / gridSize) - 2;
        int maxY = (500 / gridSize) - 2;
        int x = ((int) (Math.random() * maxX) + 1) * gridSize;
        int y = ((int) (Math.random() * maxY) + 1) * gridSize;
        return new Point(x, y);
    }

    /**
     * If the image is not null, the image is overlayed onto the polygon using the drawImage() method. If it is null
     * a red square will be drawn instead, of 25 x 25 pixels.
     *
     * @param brush Used to draw the image.
     */
    @Override
    public void paint(Graphics brush) {
        if (powerupImage != null) {
            brush.drawImage((Image) powerupImage, (int) position.x,
                    (int) position.y, 25,
                    25, null);
        } else {
            brush.setColor(Color.RED);
            brush.fillRect((int) position.x, (int) position.y, 25, 25);
        }
    }
    /**
     * Gets the type of the powerup.
     *
     * @return The type of the powerup.
     */
    public PowerupType getType() {
        return this.type;
    }

    /**
     * This inner class represents the ShortenSnake powerup and its application.
     */

    public static class ShortenSnake {
        /**
         * This method shortens the size of the snake, if the shortens PowerupType is collided into. This effect will applly
         * to the snake object only if its segments are more than 3. If there are greater than 3 snake segements, the last
         * segement will be removed from the snakeSegment array.
         */
        public static void apply(SnakeObject snake) {
            if(snake.snakeSegments.size() > 3) {
                snake.snakeSegments.remove(snake.snakeSegments.size() - 1);
            }
        }

    }

    /**
     * This inner class represents the IncreaseSpeed powerup and its application.
     */

    public static class IncreaseSpeed {
        /**
         * Increases the speed of the snake object by using the method updateGame().
         */
        public static void apply() {
            snakeGame.updateGame();
        }
    }
    /**
     * This inner class represents the ExtraPoints powerup and its application.
     */
    public static class ExtraPoints {
        /**
         * Increments the score by +5 instead of +1.
         */
        public static void apply() {
            for(int i = 0; i < 5; i++) {
                Snake.increaseScore();
            }
        }
    }

}