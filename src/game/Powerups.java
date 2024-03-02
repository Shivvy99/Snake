package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Powerups extends Polygon implements Consumables {
    final static Point[] sizePoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};
    private BufferedImage powerup1;


    public Powerups() {
        super(sizePoints, calculateSpawnPoint(), 0.0);

        try {
            powerup1 =
                    ImageIO.read(Powerups.class.getResourceAsStream("/Images/powerup1.png")); //
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


    @Override
    public void paint(Graphics brush) {
        if (powerup1 != null) {
            brush.drawImage((Image) powerup1, (int) position.x,
                    (int) position.y, 25,
                    25, null);
        } else {
            brush.setColor(Color.RED);
            brush.fillRect((int) position.x, (int) position.y, 25, 25);
        }
    }

    public class Effects {
        private boolean invincibility;
        private boolean shorter;
        private boolean faster;
        private boolean slower;
        private int speedBoostDuration; // Duration for speed boost in game ticks
        private int invincibilityDuration; // Duration for invincibility in game ticks

        public Effects() {
            invincibility = false;
            shorter = false;
            faster = false;
            slower = false;
            speedBoostDuration = 0;
            invincibilityDuration = 0;
        }

        public void activateInvincibility(int duration) {
            invincibility = true;
            invincibilityDuration = duration;
        }

        public void deactivateInvincibility() {
            invincibility = false;
            invincibilityDuration = 0;
        }

        public void activateShorter() {
            shorter = true;
        }

        public void deactivateShorter() {
            shorter = false;
        }

        public void activateFaster(int duration) {
            faster = true;
            speedBoostDuration = duration;
        }

        public void deactivateFaster() {
            faster = false;
            speedBoostDuration = 0;
        }

        public void activateSlower(int duration) {
            slower = true;
            speedBoostDuration = duration;
        }

        public void deactivateSlower() {
            slower = false;
            speedBoostDuration = 0;
        }

        public void updateEffects() {
            if (invincibility && invincibilityDuration > 0) {
                invincibilityDuration--;
                if (invincibilityDuration == 0) {
                    deactivateInvincibility();
                }
            }

            if (faster && speedBoostDuration > 0) {
                speedBoostDuration--;
                if (speedBoostDuration == 0) {
                    deactivateFaster();
                }
            }

            if (slower && speedBoostDuration > 0) {
                speedBoostDuration--;
                if (speedBoostDuration == 0) {
                    deactivateSlower();
                }
            }
        }
    }
}