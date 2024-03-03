    package game;

    /*
    CLASS: Snake
    DESCRIPTION: Extending Game, Snake is all in the paint method.
    NOTE: This class is the metaphorical "main method" of your program,
          it is your control center.

    */

    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;

    class Snake extends Game {
        private static SnakeObject snake;
        private Fruit apple;
        private Powerups powerUp;
        protected int time = 100;

        private static int score = 0;
        private Timer timer;


        public Snake() {
            super("Snake!", 500, 500);
            this.setFocusable(true);
            this.requestFocus();
            snake = new SnakeObject();
            this.addKeyListener(snake);
            apple = new Fruit();
            powerUp = new Powerups(this);
            timer = new Timer(time, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                     updateGame();
                }
            });
            timer.start();
        }

        protected void updateGame() {
            snake.move();
            snake.appleCollision(apple);
            snake.powerUpCollision(powerUp);
            if (snake.snakeObjectCollision() || snake.isGameOver()) {
                timer.stop();
                gameOverDialog();
            } else {
                repaint();
            }
        }


        private void gameOverDialog() {
            EventQueue.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Game Over\n" + "Score: " + getScore(), "Snake",
                                JOptionPane.INFORMATION_MESSAGE);
            });

        }

        public void setTime(int time) {
            this.time = time;
        }

        public static void increaseScore() {
            score++;
        }

        public static int getScore() {
            return score;
        }
        public void paint(Graphics brush) {
            drawCheckeredBackground(brush);
            brush.setColor(Color.BLACK);
            brush.drawString("Score: " + score, 10, 10);
            if(snake != null) {
                snake.paint(brush);
                apple.paint(brush);
                powerUp.paint(brush);
            }
        }

        private void drawCheckeredBackground(Graphics g) {
            boolean isDarkBlue = false;
            int width = getWidth();
            int height = getHeight();
            int squareSize = 25;

            for (int y = 0; y < height; y += squareSize) {
                isDarkBlue = !isDarkBlue;

                for (int x = 0; x < width; x += squareSize) {
                    if (isDarkBlue) {
                        g.setColor(new Color(152, 251, 152, 192));
                    } else {
                        g.setColor(new Color(240, 255, 240, 255));
                    }
                    g.fillRect(x, y, squareSize, squareSize);

                    isDarkBlue = !isDarkBlue;
                }
            }
        }

        public void update(Graphics brush) {
            if (!snake.isGameOver() && !snake.snakeObjectCollision()) {
                super.update(brush);
            } else {
                timer.stop();
            }
        }

        public static void main(String[] args) {
            Snake a = new Snake();
            a.repaint();
        }
    }