package game;

/*
CLASS: Snake
DESCRIPTION: Extending Game, Snake is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;

class Snake extends Game {
	static int counter = 0;
	private SnakeObject snake;

	public int score = 0;


  public Snake() {
    super("Snake!",490,490);
    this.setFocusable(true);
	this.requestFocus();
	snake = new SnakeObject();
	this.addKeyListener(snake);
  }
  
	public void paint(Graphics brush) {
    	
    	// sample code for printing message for debugging
    	// counter is incremented and this message printed
    	// each time the canvas is repainted
		drawCheckeredBackground(brush);
    	counter++;
		brush.setColor(Color.BLUE);
    	brush.drawString("Counter is " + counter,10,10);
		brush.setColor(Color.GREEN);
		snake.paint(brush);

  }

	private void drawCheckeredBackground(Graphics g) {
		boolean isBlack = false;
		int width = getWidth();
		int height = getHeight();
		int squareSize = 25;

		for (int y = 0; y < height; y += squareSize) {
			isBlack = !isBlack;

			for (int x = 0; x < width; x += squareSize) {
				if (isBlack) {
					g.setColor(Color.black);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect(x, y, squareSize, squareSize);

				isBlack = !isBlack;
			}
		}
	}
  
	public static void main (String[] args) {
   		Snake a = new Snake();
		a.repaint();
  }
}