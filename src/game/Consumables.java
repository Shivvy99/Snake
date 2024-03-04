package game;
/**
 * Shyam Ganapathy, Shiven Khanna
 */
import java.awt.*;
/**
 * This interface is used by both the Fruit and Powerup Class. It contains two methods for which the Fruit and Powerup
 * classes need in order to function properly.
 */
public interface Consumables {
    /**
     * This method signature outlines the paint method in both the Fruit and Powerups classes.
     */
    public void paint(Graphics brush);
    /**
     * This method signature outlines the calculateSpawnPoint method in both the Fruit and Powerups classes.
     */
    Point calculateSpawnPoint();


}
