package Main;

import java.awt.AlphaComposite; 



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import piece.mouse;
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
	
	public static final int R = 0;  // rabbit
	public static final int M = 1;  // mouse
	public static final int NP = -1; // not a player
	int currentPlayer = R;
	
	public boolean magicKeyCondition = true;
	
	
	Piece activeP;
	
	//boolean 
		boolean canMove;
		boolean validSquare;
		
	TitleScreenHandler tsHandler = new TitleScreenHandler();
	
	
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
						if(     piece.player == currentPlayer &&
								piece.col == mouse.x/Board.SQUARE_SIZE &&
								piece.row == mouse.y/Board.SQUARE_SIZE &&
								(piece.name == "Rabbit" || piece.name == "Mouse"))
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
							changePlayer();
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
			if (magicKeyCheck(pieces.get(11).col,pieces.get(11).row)==false) {
				simPieces.remove(11);
				magicKeyCondition = false;
			}
		}
	}
	
	// magic key condition
	public boolean magicKeyCheck(int targetCol, int targetRow) {
 
		for (Piece piece1 : simPieces) {
			for(Piece piece2 : simPieces) {
			if( (piece1.name=="Rabbit" && piece1.col==(targetCol-1) && piece1.row==targetRow) && (piece2.name== "Mouse" && piece2.col==(targetCol+1) && piece2.row==targetRow ) ){
				return false;
			}
			else if((piece1.name=="Mouse" && piece1.col==(targetCol-1) && piece1.row==targetRow) && (piece2.name== "Rabbit" && piece2.col==(targetCol+1) && piece2.row==targetRow) ) {
				return false;
			}
			else if((piece1.name=="Mouse" && piece1.row==(targetRow-1) && piece1.col==targetCol) && (piece2.name== "Rabbit" && piece2.row==(targetRow+1) && piece2.col==targetCol ) ) {
				return false;
			}
			else if((piece1.name=="Rabbit" && piece1.row==(targetRow-1) && piece1.col==targetCol) && (piece2.name== "Mouse" && piece2.row==(targetRow+1) && piece2.col==targetCol) ) {
				return false;
			}
			}
		}
		return true;
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
			
			// status message
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setFont(new Font("Book Antiqua",Font.PLAIN,40));
			g2.setColor(Color.white);
			
			if(currentPlayer == R) {
				g2.drawString("Rabbit turn", 540, 550);
			}
			else {
				g2.drawString("Mouse turn", 540,250);
			}
		
		    if (magicKeyCondition == false) {
		    	g2.drawString("Magic door is open, find hubi ", 100,600);
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
				pieces.add(new normal_door(NP,0,1));
				pieces.add(new normal_door(NP,0,3));
				pieces.add(new normal_door(NP,1,0));
				pieces.add(new normal_door(NP,1,4));
				pieces.add(new normal_door(NP,3,2));
				pieces.add(new normal_door(NP,4,1));
				pieces.add(new normal_door(NP,4,3));
				
				// add mouse door
				pieces.add(new mouse_door(NP,1,2));
				pieces.add(new mouse_door(NP,3,4));
				
				
				// add rabbit door
				pieces.add(new rabbit_door(NP,2,1));
				pieces.add(new rabbit_door(NP,3,0));

				// add Magic 
				pieces.add(new magic_door(NP,2,3));
			}
			
			if(random==0) {
				//add normal door
				pieces.add(new normal_door(NP,1,0));
				pieces.add(new normal_door(NP,3,0));
				pieces.add(new normal_door(NP,0,1));
				pieces.add(new normal_door(NP,4,1));
				pieces.add(new normal_door(NP,2,3));
				pieces.add(new normal_door(NP,1,4));
				pieces.add(new normal_door(NP,3,4));
				
				// add mouse door
				pieces.add(new mouse_door(NP,2,1));
				pieces.add(new mouse_door(NP,4,3));
				
				
				// add rabbit door
				pieces.add(new rabbit_door(NP,1,2));
				pieces.add(new rabbit_door(NP,0,3));

				// add Magic 
				pieces.add(new magic_door(NP,3,2));
			}
			
			// add wall cover
			for(int i=0;i<5;i++) {
				if(i%2==0) {
					for(int j=1;j<5;j+=2) {
						pieces.add(new wallcover(NP,i,j));
					}
				}
				else {
					for(int j=0;j<5;j+=2) {
						pieces.add(new wallcover(NP,i,j));
					}
				}
			}
			
			
			
			int count1=0;
			int count2=0;
			for(int i=0 ; i<5 ;i+=2) {
				for(int j=0 ;j<5 ;j+=2){
					if(random<=4 ) {
						if(count1<5) {
							pieces.add(new Carrot(NP,i,j));
							count1++;
						}
						else {
							pieces.add(new Chesse(NP,i,j));
						}
						
					}
					else{
						if(count2 <5) {
							pieces.add(new Chesse(NP,i,j));
							count2++;
						}
						else {
							pieces.add(new Carrot(NP,i,j));
						}
						
					}
				}
			}
			
			
			//add rabbit
			pieces.add(new Rabbit(R,0,0));
			//add mouse
			pieces.add(new mouse(M,4,4));
			
		}
		
		private void copyPieces(ArrayList<Piece> source, ArrayList<Piece>target) {
			target.clear();
			for(int i=0;i<source.size();i++) {
				target.add(source.get(i));
			}
		}
		
		// change turn
		private void changePlayer() {
			if(currentPlayer==R) {
				currentPlayer = M;
			}
			else {
				currentPlayer = R;
			}
			activeP = null;
		}
		
		public class TitleScreenHandler implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				
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