package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;

public class Powerups extends Polygon implements Consumables {
    final static Point[] sizePoints = {new Point(0, 0), new Point(0,
            25),
            new Point(25, 25), new Point(25, 0)};
    private BufferedImage powerupImage;
    private PowerupType type;
    private static Snake snakeGame;
    static PowerupType[] types = PowerupType.values();

    public Powerups(Snake snakeGame) {
        super(sizePoints, calculateSpawnPoint(), 0.0);
        setPowerupImage();
        this.snakeGame = snakeGame;
    }

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


    public static Point calculateSpawnPoint() {
        int gridSize = 25;
        int maxX = (500 / gridSize) - 2;
        int maxY = (500 / gridSize) - 2;
        int x = ((int) (Math.random() * maxX) + 1) * gridSize;
        int y = ((int) (Math.random() * maxY) + 1) * gridSize;
        return new Point(x, y);
    }


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
    public PowerupType getType() {
        return this.type;
    }

    public static class ShortenSnake {
        public static void apply(SnakeObject snake) {
            if(snake.snakeSegments.size() > 3) {
                snake.snakeSegments.remove(snake.snakeSegments.size() - 1);
            }
        }

    }
    public static class IncreaseSpeed {
        public static void apply() {
            snakeGame.updateGame();
        }
    }

    public static class ExtraPoints {
        public static void apply() {
            for(int i = 0; i < 5; i++) {
                Snake.increaseScore();
            }
        }
    }

}