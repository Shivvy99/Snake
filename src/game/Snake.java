package game;
/**
 * Names: Shyam Ganapathy, Shiven Khanna
 */

    /*
    CLASS: Snake
    DESCRIPTION: Extending Game, Snake is all in the paint method. The class initializes all the elements of the game,
    such as the powerups, fruit, and snake. It also draws the background and
    calls the pain methods for the appropriate
    game elements.
    NOTE: This class is the metaphorical "main method" of your program,
          it is your control center.

    */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Snake extends Game {
    /**
     * The Snake object/element which is controlled by the user.
     */
    private static SnakeObject snake;
    /**
     * The fruit object (apple) that randomly generates on the screen and increases the number of points/length of the
     * snake object when the snake object "collides" into it.
     */

    private Fruit apple;
    /**
     * The powerup object that randomly generates on the screen and either increases the snake objects speed,
     * shortens the length snake object, or incerases the number of points by +5 when the snake object "collides"
     * into it.
     */

    private Powerups powerUp;
    /**
     * Every 100 milliseconds, the game will update.
     */

    protected int time = 100;
    /**
     * The score when the game starts, and increases when the snake object "collides" with a fruit object or certain
     * powerup.
     */
    private static int score = 0;
    /**
     * The timer for updating the game at regular intervals
     */
    private Timer timer;

    /**
     * Constructs a new game instance. It calls the super constructor, sets the width/height of the screen where the game
     * will take place. Additionally, it initializes the snake, apple, and powerup objects. Finally, it creates a timer
     * for the game, which updates the game in regular intervals.
     */
    public Snake() {
        super("Snake!", 500, 500);
        this.setFocusable(true);
        this.requestFocus();
        snake = new SnakeObject();
        this.addKeyListener(snake);
        apple = new Fruit();
        powerUp = new Powerups(this);
        /**
         * This line initializes and starts a timer for the game updates.
         * The timer triggers the {@link #updateGame()} method at regular intervals.
         * The interval is determined by the 'time' attribute of the Snake instance.
         */
        timer = new Timer(time, new ActionListener() {
            /**
             * Invoked when the timer triggers an action event.
             *
             * @param e The action event associated with the timer trigger.
             */
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });
        timer.start();
    }
    /**
     * Everytime an action event is performed, updateGame takes place. This method calls the collision methods which
     * checks if a collision has taken place. It also checks if the game is over or not, and displays the correct dialog.
     * If the game is not over the game will continue.
     */
    protected void updateGame() {
        snake.move();
        snake.appleCollision(apple);
        snake.powerUpCollision(powerUp);
        /**
         * If the snake has collided with itself or the game is over, the timer will stop and the game will end.
         */
        if (snake.snakeObjectCollision() || snake.isGameOver()) {
            timer.stop();
            gameOverDialog();
        } else {
            repaint();
        }
    }

    /**
     * Displays the correct dialog in a JOptionPane if the game has ended. It will output the score as well as the
     * "Game Over" dialog.
     */
    private void gameOverDialog() {
        EventQueue.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Game Over\n" + "Score: " + getScore(), "Snake",
                    JOptionPane.INFORMATION_MESSAGE);
        });

    }

    /**
     * Sets the time interval for game updates.
     *
     * @param time The time interval in milliseconds.
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Increases the player's score.
     */
    public static void increaseScore() {
        score++;
    }

    /**
     * Retrieves the current player's score.
     *
     * @return The player's score.
     */
    public static int getScore() {
        return score;
    }

    /**
     * Creates the screen color, outputs the score in the top left corner of the screen, and calls the paint method of the snake,
     * apple, and powerup objects appropriately.
     *
     * @param brush The graphics used to paint.
     */
    public void paint(Graphics brush) {
        drawCheckeredBackground(brush);
        brush.setColor(Color.BLACK);
        brush.drawString("Score: " + score, 10, 10);
        if (snake != null) {
            snake.paint(brush);
            apple.paint(brush);
            powerUp.paint(brush);
        }
    }
    /**
     * Draws a checkered background with alternating colors.
     *
     * @param g The graphics used to paint.
     */

    private void drawCheckeredBackground(Graphics g) {
        boolean isLightGreen = false;
        int width = getWidth();
        int height = getHeight();
        int squareSize = 25;
        /**
         * This for loop creates a board with the alternating colors, light green, and a whitish color. It checks if
         * every other square is light green or not. If it is not, it will initiate a square on the board(of size 25)
         * with the whitish color.
         */
        for (int y = 0; y < height; y += squareSize) {
            isLightGreen = !isLightGreen;

            for (int x = 0; x < width; x += squareSize) {
                if (isLightGreen) {
                    g.setColor(new Color(152, 251, 152, 192));
                } else {
                    g.setColor(new Color(240, 255, 240, 255));
                }
                g.fillRect(x, y, squareSize, squareSize);

                isLightGreen = !isLightGreen;
            }
        }
    }
    /**
     * Updates the game graphics if the game isn't over or if the snake hasn't collided with itself.
     *
     * @param brush The graphics context to paint on.
     */
    public void update(Graphics brush) {
        if (!snake.isGameOver() && !snake.snakeObjectCollision()) {
            super.update(brush);
        } else {
            timer.stop();
        }
    }
    /**
     * This main method starts the game of snake.
     *
     * @param args .
     */
    public static void main(String[] args) {
        Snake a = new Snake();
        a.repaint();
    }
}