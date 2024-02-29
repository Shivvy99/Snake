package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Fruit extends Polygon implements Consumables{
    final static Point[] spawnPoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};
    private BufferedImage appleImage;
    public Fruit(int x, int y) {

        super(spawnPoints, new Point(x, y), 0.0);

    }

    public void increaseScore() {

    }

    public void collisions() {

    }

    @Override
    public void paint(Graphics brush) {
        brush.setColor(Color.RED);
        brush.fillRect((int) 250, (int) 250, 20, 20);

        // Overlay the apple image on top of the square
            brush.drawImage(appleImage, (int) 0, (int) 0, 20, 20, null);
    }
}
