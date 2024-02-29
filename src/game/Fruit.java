package game;

import java.awt.*;

public class Fruit extends Polygon implements Consumables{

    public Fruit(Point[] inShape, Point inPosition, double inRotation) {
        super(inShape, inPosition, inRotation);
    }

    public void increaseScore() {

    }

    public void collisions() {

    }

    @Override
    public void paint(Graphics brush) {
        brush.setColor(Color.RED);
        brush.fillOval((int)position.x, (int)position.y, 10, 10);
    }
}
