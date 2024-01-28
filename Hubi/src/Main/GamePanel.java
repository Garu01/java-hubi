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
import piece.hubi;
import piece.magic_door;
import piece.mouse_door;
import piece.normal_door;
import piece.nothubi;
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
	public boolean checkWin = false;
	
	
	Piece activeP;
	Piece hubi;
	//boolean 
		boolean canMove;
		boolean validSquare;
		
		
	//game state
	    public int gameState;
	    public final int titleState = 0;
	    public final int playState = 1;
	    public final int pauseState = 2;
	   // public final int dialogueState = 3;
	    
	    
	 // SCREEN SETTINGS
	    final int originalTileSize = 16; // 16x16 tile
	    final int scale = 3;

	    public final int tileSize = originalTileSize * scale; // 48x48 tile
	    public final int maxScreenCol = 16;
	    public final int maxScreenRow = 12;
	    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

	    // WORLD SETTINGS
	    public final int maxWorldCol = 50;
	    public final int maxWorldRow = 50;

	    public final int worldWidth = tileSize * maxWorldCol;
	    public final int worldHeight = tileSize * maxWorldRow;
	    
	    
	    // system
	    public UI ui = new UI(this);
	    public KeyHandler keyH = new KeyHandler(this);
		

	
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setBackground(Color.blue);
		this.setDoubleBuffered(true);
	    this.addKeyListener(keyH);
	    this.setFocusable(true);
		
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		
		setPieces();
		copyPieces(pieces,simPieces);
		
	}
	public void setupGame() {

        gameState = titleState;
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
				 if (gameState == pauseState) {
			            // nothing
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
			
			
			// if hitting a piece, remove it from the list
//			if(activeP.hittingP != null) {
//				simPieces.remove(activeP.hittingP.getIndex());
//			}
			// if hitting a wall, remove it from the list
			
			if(activeP.wallCover(activeP.col,activeP.row)== true) {
				simPieces.remove(activeP.hittingP.getIndex());
			
			}

			
			for (Piece piece : pieces) {
				if(piece.name == "Magic_door" && magicKeyCheck(piece.col,piece.row)==false)
				{
					simPieces.remove(piece.getIndex());
					//pieces.remove(piece.getIndex());
					magicKeyCondition = false;
				}
			
			}
			
				// problem : xóa index key thì nó sẽ bị tùm lum -> 
				if(magicKeyCondition==false) {
					if(activeP.isToken(activeP.col,activeP.row) ==true) {
						simPieces.remove(activeP.hittingP.getIndex());
					}
					if(activeP.isToken(activeP.col, activeP.row)==false && isWin(activeP.col,activeP.row)==true) {
						checkWin = true;
					}
				}
				
				validSquare=true;
		}
	}
	
	// magic key condition
	public boolean magicKeyCheck(int targetCol, int targetRow) {

		for (Piece piece1 : simPieces) {
			if(piece1.isWall(targetCol, targetRow)==false) {
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
		}
		return true;
	}
	
	public boolean isWin(int targetCol, int targetRow) {
			for(Piece piece1 : simPieces) {
				for(Piece piece2 : simPieces) {
					if((piece1.name == "Rabbit" && piece2.name=="Mouse"  )&& 
							(piece1.row ==targetRow && piece2.row == targetRow )&&
							(piece2.col == targetCol && piece1.col == targetCol )) {
					for(Piece piece3 : simPieces) {
					if(piece3.name == "hubi" && piece3.row == piece1.row && piece3.col == piece1.col) {
						return true;
					}
					}
				}}
			}
			return false;
		}

	
	// paintComponent is a method in Jcomponent that JPanel inherits and is
		// used to draw objects on the panel
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// convert Graphics to Graphics2D
			Graphics2D g2 = (Graphics2D)g;
			
	        // TITLE SCREEN
	        if (gameState == titleState) {
	            ui.draw(g2);
	        }	       
	        	else {
	        		//draw board
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
	    		    if(checkWin == true) {
	    		    	g2.drawString("win ", 200,700);
	    		    }
	        	}
	        g2.dispose();
			
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
			
			// add nothubi
//			for(int i=0;i<5;i+=2) {
//				for(int j=0;j<5;j+=2) {
//					pieces.add(new nothubi(NP,i,j));
//				}
//			}
			
			//add hubi
			int rand2 = rand.nextInt(3) * 2;
			int rand3 = rand.nextInt(3) * 2;
			pieces.add(new hubi(NP,rand2,rand3));
			
			
			
			//add token
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
					if(checkWin == true) {
						break;
					}
				}
			}
		}
}