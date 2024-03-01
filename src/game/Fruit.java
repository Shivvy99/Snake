package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Fruit extends Polygon implements Consumables {
    final static Point[] sizePoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};
    private BufferedImage appleImage;


    public Fruit() {
        super(sizePoints, calculateSpawnPoint(), 0.0);

        try {
            appleImage =
                    ImageIO.read(Fruit.class.getResourceAsStream("/Images/apple.png")); //
            // Adjust the path to your image file
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Point calculateSpawnPoint() {
        int gridSize = 25;
        int maxX = (500 / gridSize) - 1;
        int maxY = (500 / gridSize) - 1;
        int x = (int) (Math.random() * maxX) * gridSize;
        int y = (int) (Math.random() * maxY) * gridSize;
        return new Point(x, y);
    }

    public void increaseScore() {

    }




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
