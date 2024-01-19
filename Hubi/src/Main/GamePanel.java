package Main;

import java.awt.AlphaComposite; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import Main.Mouse;
import piece.Carrot;
import piece.Chesse;
import piece.Piece;
import piece.Rabbit;
import piece.magic_door;
import piece.mouse_door;
import piece.normal_door;
import piece.rabbit_door;
import piece.wallcover;
import Main.Board;


import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
	// set up screen size
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	
	final int FPS = 60;
	Thread gameThread; // Thread : keep game run in loop -> implement Runnable

	Board board = new Board();
	Mouse mouse = new Mouse();
	

	Piece activeP;
	
	//boolean 
		boolean canMove;
		boolean validSquare;
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setBackground(Color.blue);
		
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		
		setPieces();
		copyPieces(pieces,simPieces);
		
	}
	
	private void update() {
		// Mouse button pressed
				if(mouse.pressed) {
					if(activeP == null) {
					// If the activeP is null , check if you can pick up a piece
					for(Piece piece : simPieces) {
						// If a mouse is on an ally piece, pick it up as acriveP
						if(     piece.name == "Rabbit" &&
								piece.col == mouse.x/Board.SQUARE_SIZE &&
								piece.row == mouse.y/Board.SQUARE_SIZE)
							activeP = piece;
					}
					}
					else {
						// if the player is holding piece, simulate the move
						simulate();
					}
				}
				if (mouse.pressed ==false) {
					if(activeP != null) {
						if(validSquare) {
							// move confirmed
							
							//update the pieces list in case a piece has been captured
							// and removed during the simulation
							copyPieces(simPieces, pieces);
							activeP.updatePosition();
						}
						else {
							// the move is not valid so reset everything
							copyPieces(pieces,simPieces);
							activeP.resetPosition();
							activeP = null;
						}
						
					}
				}
	}
	public void simulate() {
		canMove = false;
		validSquare = false;
		
		// reset the piece list in every loop
		// this is basically for restoring the removed pieced during the simulation
		copyPieces(pieces,simPieces);
		
		// if the piece is being held, update its position
		activeP.x = mouse.x - Board.HALF_SQUARE_SIZE;
		activeP.y = mouse.y - Board.HALF_SQUARE_SIZE;
		activeP.col = activeP.getCol(activeP.x);
		activeP.row = activeP.getRow(activeP.y);
		
		if(activeP.canMove(activeP.col, activeP.row)) {
			canMove = true;
			validSquare=true;
			
			// if hitting a piece, remove it from the list
//			if(activeP.hittingP != null) {
//				simPieces.remove(activeP.hittingP.getIndex());
//			}
			// if hitting a wall, remove it from the list
			if(activeP.wallCover(activeP.col,activeP.row)== true) {
				simPieces.remove(activeP.hittingP.getIndex());
			}
		}
	}
	// paintComponent is a method in Jcomponent that JPanel inherits and is
		// used to draw objects on the panel
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// convert Graphics to Graphics2D
			Graphics2D g2 = (Graphics2D)g;
			
			// draw board
			board.draw(g2);
			
			// draw piece
			for (Piece p : simPieces) {
				p.draw(g2);
			}
			
			if (activeP !=null) {
				if(canMove)
				{
				g2.setColor(Color.green);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.7f));
				g2.fillRect(activeP.col*Board.SQUARE_SIZE, activeP.row*Board.SQUARE_SIZE
						, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
				}
				activeP.draw(g2);
			}
			}
		
		
		public void LaunchGame() {
			gameThread = new Thread(this);
			gameThread.start();
			
		}
		
		public static ArrayList<Piece> pieces = new ArrayList<>();
		public static ArrayList<Piece> simPieces = new ArrayList<>();
		
		public void setPieces() {
			Random rand = new Random();
			int random = rand.nextInt(2);
			
			
			if(random==1) {
				//add normal door
				pieces.add(new normal_door(0,1));
				pieces.add(new normal_door(0,3));
				pieces.add(new normal_door(1,0));
				pieces.add(new normal_door(1,4));
				pieces.add(new normal_door(3,2));
				pieces.add(new normal_door(4,1));
				pieces.add(new normal_door(4,3));
				
				// add mouse door
				pieces.add(new mouse_door(1,2));
				pieces.add(new mouse_door(3,4));
				
				
				// add rabbit door
				pieces.add(new rabbit_door(2,1));
				pieces.add(new rabbit_door(3,0));

				// add Magic 
				pieces.add(new magic_door(2,3));
			}
			
			if(random==0) {
				//add normal door
				pieces.add(new normal_door(1,0));
				pieces.add(new normal_door(3,0));
				pieces.add(new normal_door(0,1));
				pieces.add(new normal_door(4,1));
				pieces.add(new normal_door(2,3));
				pieces.add(new normal_door(1,4));
				pieces.add(new normal_door(3,4));
				
				// add mouse door
				pieces.add(new mouse_door(2,1));
				pieces.add(new mouse_door(4,3));
				
				
				// add rabbit door
				pieces.add(new rabbit_door(1,2));
				pieces.add(new rabbit_door(0,3));

				// add Magic 
				pieces.add(new magic_door(3,2));
			}
			
			// add wall cover
			for(int i=0;i<5;i++) {
				if(i%2==0) {
					for(int j=1;j<5;j+=2) {
						pieces.add(new wallcover(i,j));
					}
				}
				else {
					for(int j=0;j<5;j+=2) {
						pieces.add(new wallcover(i,j));
					}
				}
			}
			
			
			
			int count1=0;
			int count2=0;
			for(int i=0 ; i<5 ;i+=2) {
				for(int j=0 ;j<5 ;j+=2){
					if(random<=4 ) {
						if(count1<5) {
							pieces.add(new Carrot(i,j));
							count1++;
						}
						else {
							pieces.add(new Chesse(i,j));
						}
						
					}
					else{
						if(count2 <5) {
							pieces.add(new Chesse(i,j));
							count2++;
						}
						else {
							pieces.add(new Carrot(i,j));
						}
						
					}
				}
			}
			
			
			//add rabbit
			pieces.add(new Rabbit(0,0));
			
		}
		
		private void copyPieces(ArrayList<Piece> source, ArrayList<Piece>target) {
			target.clear();
			for(int i=0;i<source.size();i++) {
				target.add(source.get(i));
			}
		}
		
		// create a game loop that keeps calling these 2 methods at a certain interval
		public void run() {
			// game loop : The game loop is a sequence of processes that run continuously 
			// as long as the game is running 
			// Here we use System.nanoTime() to measure the elapsed time and call update and 
			// repaint methods once every 1/60 of a second.
			double drawInterval = 1000000000 / FPS;
			double delta = 0;
			long lastTime = System.nanoTime();
			long currentTime;
			
			while (gameThread != null) {
				currentTime = System.nanoTime();
				
				delta += (currentTime - lastTime) / drawInterval;
				lastTime = currentTime;
				
				if (delta >1) {
					update();
					repaint();
					delta--;
				}
			}
		}
}