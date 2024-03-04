package game;
/**
 * Shyam Ganapathy, Shiven Khanna
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Fruit extends Polygon implements Consumables {
    /**
     * This static array of type Point are the 4 points which make our snake object.
     */
    final static Point[] sizePoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};

    static Point spawnPoint = new Point((int) (Math.random() * 18) * 25, (int)
            (Math.random() * 18) * 25);

    /**
     * Is the apple image.
     */
    private BufferedImage appleImage;

    /**
     * Constructs a new instance of a Fruit. This represents a fruit object in the game. The polygon super constructor
     * is called, which uses the 4 points initialized above, a random spawn point, and 0 rotation. It then tries finding
     * an image of the apple.png in the images folder. If it cannot be found, an exception is thrown.
     */
    public Fruit() {
        super(sizePoints, new Point((int) (Math.random() * 18) * 25, (int)
                (Math.random() * 18) * 25), 0.0);

        try {
            appleImage =
                    ImageIO.read(Fruit.class.getResourceAsStream("/Images/apple.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * This method uses the Math class to randomly output the powerup on the screen.
     *
     * @return The calculated spawn point.
     */
    public Point calculateSpawnPoint() {
        int gridSize = 25;
        int maxX = (500 / gridSize) - 1;
        int maxY = (500 / gridSize) - 1;
        int x = (int) (Math.random() * maxX) * gridSize;
        int y = (int) (Math.random() * maxY) * gridSize;
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
        if (appleImage != null) {
            brush.drawImage((Image) appleImage, (int) position.x,
                    (int) position.y, 25,
                    25, null);
        } else {
            brush.setColor(Color.RED);
            brush.fillRect((int) position.x, (int) position.y, 25, 25);
        }
    }
}
