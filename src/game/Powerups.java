package game;

import java.awt.*;

public class Powerups extends Polygon implements Consumables {

    public Powerups(Point[] inShape, Point inPosition, double inRotation) {
        super(inShape, inPosition, inRotation);
    }

    @Override
    public void increaseScore() {

    }

    @Override
    public void paint(Graphics brush) {
        brush.setColor(Color.RED);

    }
}
