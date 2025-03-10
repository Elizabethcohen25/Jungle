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
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

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
	private Animal iguana;

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
       
      //variable and objects
      //create (construct) the objects needed for the game and load up 
		MonkeyPic = Toolkit.getDefaultToolkit().getImage("Monkey.png"); //load the picture
		monkey = new Animal(800,100);

		jungleBackground = Toolkit.getDefaultToolkit().getImage("Jungle Background.jpg");

		elephantPic = Toolkit.getDefaultToolkit().getImage("elephant 2.png");
		elephant = new Animal(20,150);

		iguanaPic = Toolkit.getDefaultToolkit().getImage("iguana.png");
		iguana = new Animal(150,300);

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

         moveThings();  //move all the game objects
			crash();
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		monkey.move();
		elephant.wrap();
		iguana.move();
	}

	public void crash(){
		if (monkey.hitbox.intersects(iguana.hitbox)== true){
			monkey.dx=-monkey.dx;
			iguana.dx=-iguana.dx;
		}
		if(iguana.hitbox.intersects(elephant.hitbox)==true){
			iguana.width=iguana.width+5;
			elephant.width=iguana.width+8;

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

      //draw the image of the astronaut
		g.drawImage(jungleBackground,0,0,1000, 700,null);
		g.drawImage(MonkeyPic, monkey.xpos, monkey.ypos, monkey.width, monkey.height, null);
		g.drawImage(elephantPic,elephant.xpos, elephant.ypos, elephant.width,elephant.height, null);
		g.drawImage(iguanaPic,iguana.xpos, iguana.ypos, iguana.width,iguana.height, null);

		g.setColor(Color.yellow);
		g.drawRect(elephant.hitbox.x, elephant.hitbox.y, elephant.hitbox.width, elephant.hitbox.height);


		g.dispose();

		bufferStrategy.show();
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