//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener {

	//Variable Definition Section
	//Declare the variables used in the program
	//You can set their initial values too

	//Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

	//Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
	public JPanel panel;

	public BufferStrategy bufferStrategy;
	public Image MonkeyPic;
	public Image jungleBackground;
	public Image elephantPic;
	public Image iguanaPic;
	//Declare the objects used in the program
	//These are things that are made up of more than one variable type
	private Animal monkey;
	private Animal elephant;
	private Animal[] elephantArray = new Animal[5];
	private Animal iguana;
	private Animal[] iguanaArray = new Animal[5];

	public boolean gameOver = false;
	public int score = 0;

	// Main method definition
	// This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


	// Constructor Method
	// This has the same name as the class
	// This section is the setup portion of the program
	// Initialize your variables and construct your program objects here.
	public BasicGameApp() {

		setUpGraphics();
		canvas.addKeyListener(this);

		//variable and objects
		//create (construct) the objects needed for the game and load up
		MonkeyPic = Toolkit.getDefaultToolkit().getImage("Monkey.png"); //load the picture
		monkey = new Animal(800,100);
		monkey.dx = 0;
		monkey.dy = 0;

		jungleBackground = Toolkit.getDefaultToolkit().getImage("Jungle Background.jpg");

		elephantPic = Toolkit.getDefaultToolkit().getImage("elephant 2.png");
//		elephant = new Animal(20,150);
		for (int x = 0; x < elephantArray.length; x++) {
			elephantArray[x] = new Animal((int)(Math.random()*900), (int)(Math.random()*600));
		}

		iguanaPic = Toolkit.getDefaultToolkit().getImage("iguana.png");
		iguana = new Animal(150,300);
		for (int x = 0; x < iguanaArray.length; x++) {
			iguanaArray[x] = new Animal((int)(Math.random()*900), (int)(Math.random()*600));
		}

	}// BasicGameApp()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

	// main thread
	// this is the code that plays the game after you set things up
	public void run() {

		//for the moment we will loop things forever.
		while (true) {

			if (gameOver == false) { // if game is not over yet, move and crash
				moveThings();  //move all the game objects
				crash();
			}
			render();  // paint the graphics
			pause(20); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
		//calls the move( ) code in the objects
		monkey.move();
//		elephant.wrap();
		for (int x = 0; x < elephantArray.length; x++) {
			elephantArray[x].wrap(); // move all elephants by wrapping
		}
//		iguana.move();
		for (int x = 0; x < iguanaArray.length; x++) {
			iguanaArray[x].move(); // move all iguanas by bouncing
		}
	}

	public void crash(){
		// if monkey hits iguana, iguana disappears
		for (int x = 0; x < iguanaArray.length; x++) {
			if (monkey.hitbox.intersects(iguanaArray[x].hitbox) == true && iguanaArray[x].isAlive == true) {
				iguanaArray[x].isAlive = false;
				score = score + 1; // tracks how many iguanas you collect
				// if you collect them all, you win!
				if (score == 5) {
					gameOver = true;
				}
			}
		}
		// if monkey hits elephant, game is over/you lose
		for (int x = 0; x < elephantArray.length; x++) {
			if (monkey.hitbox.intersects(elephantArray[x].hitbox) == true) {
				gameOver = true;
			}
		}
	}

	//Pauses or sleeps the computer for the amount specified in milliseconds
	public void pause(int time ){
		//sleep
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {

		}
	}

	//Graphics setup method
	private void setUpGraphics() {
		frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

		panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
		panel.setLayout(null);   //set the layout

		// creates a canvas which is a blank rectangular area of the screen onto which the application can draw
		// and trap input events (Mouse and Keyboard events)
		canvas = new Canvas();
		canvas.setBounds(0, 0, WIDTH, HEIGHT);
		canvas.setIgnoreRepaint(true);

		panel.add(canvas);  // adds the canvas to the panel.

		// frame operations
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
		frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
		frame.setResizable(false);   //makes it so the frame cannot be resized
		frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

		// sets up things so the screen displays images nicely.
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		canvas.requestFocus();
		System.out.println("DONE graphic setup");

	}


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);

		//draw the image of the jungle and characters
		g.drawImage(jungleBackground,0,0,1000, 700,null);
		g.drawImage(MonkeyPic, monkey.xpos, monkey.ypos, monkey.width, monkey.height, null);
//		g.drawImage(elephantPic,elephant.xpos, elephant.ypos, elephant.width,elephant.height, null);
		for (int x = 0; x < elephantArray.length; x++) {
			g.drawImage(elephantPic,elephantArray[x].xpos, elephantArray[x].ypos, elephantArray[x].width,elephantArray[x].height, null);
		}
		for (int x = 0; x < iguanaArray.length; x++) {
			if (iguanaArray[x].isAlive == true) {
				g.drawImage(iguanaPic, iguanaArray[x].xpos, iguanaArray[x].ypos, iguanaArray[x].width, iguanaArray[x].height, null);
			}
		}

		g.setColor(Color.yellow);
//		g.drawRect(elephant.hitbox.x, elephant.hitbox.y, elephant.hitbox.width, elephant.hitbox.height);

		// game over screen
		if (gameOver == true) {
			// make font big and readable
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.setColor(Color.BLACK);
			if (score == iguanaArray.length) { // if you collected all iguanas, you win
				g.drawString("WINNER", 300, 300);
			}
			else { // if you hit an elephant before getting all iguanas, you lose
				g.drawString("GAME OVER", 300, 300);
			}
		}


		g.dispose();
		bufferStrategy.show();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// controls monkey movement
		if (e.getKeyCode() == 38) { // up
			System.out.println("going up");
			monkey.dy = -5;
		}
		if (e.getKeyCode() == 37) { // left
			System.out.println("going left");
			monkey.dx = -5;
		}
		if (e.getKeyCode() == 40) { // down
			System.out.println("going down");
			monkey.dy = 5;
		}
		if (e.getKeyCode() == 39) { // right
			System.out.println("going right");
			monkey.dx = 5;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}

/*

Aquarium game expectations:


Have at least 3 different characters with their own picture



Have one character move by wrapping. Have one character move by bouncing



Have at least 2 intersections



Comments your code so I can understand what is happening



The variable, class, and method names should be contextual to the project you are making (aka no Astros unless making a space themed game with astronauts)




If the above conditions are met, your grade will be a B+.



To move into the A-, A, A+ range, add extra spice like more characters, more interactions, lives, or a  win game screen to name a few.


Submission: a github link AND video of it working

 */