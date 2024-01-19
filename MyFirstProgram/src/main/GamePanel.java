package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rock;

public class GamePanel extends JPanel implements Runnable {
	// set up screen size
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	
	final int FPS = 60;
	Thread gameThread; // Thread : keep game run in loop -> implement Runnable

	Board board = new Board();
	Mouse mouse = new Mouse();
	
	//set color 
	public static final int WHITE = 1;
	public static final int BLACK = 0;
	int currentColor = WHITE;
	
	// set pieces
	public static ArrayList<Piece> pieces = new ArrayList<>();
	public static ArrayList<Piece> simPieces = new ArrayList<>();
	Piece activeP; // handle the piece that the player is currently holding
	
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
	
	public void setPieces() {
		// white team
		pieces.add(new Pawn(WHITE,0,6));
		pieces.add(new Pawn(WHITE,1,6));
		pieces.add(new Pawn(WHITE,2,6));
		pieces.add(new Pawn(WHITE,3,6));
		pieces.add(new Pawn(WHITE,4,6));
		pieces.add(new Pawn(WHITE,5,6));
		pieces.add(new Pawn(WHITE,6,6));
		pieces.add(new Pawn(WHITE,7,6));
		pieces.add(new Knight(WHITE,1,7));
		pieces.add(new Knight(WHITE,6,7));
		pieces.add(new Bishop(WHITE,2,7));
		pieces.add(new Bishop(WHITE,4,7));
		pieces.add(new Queen(WHITE,3,4));
		pieces.add(new King(WHITE,4,7));
		pieces.add(new Rock(WHITE,0,7));
		pieces.add(new Rock(WHITE,7,7));
		
		//black team
		pieces.add(new Pawn(BLACK,0,1));
		pieces.add(new Pawn(BLACK,1,1));
		pieces.add(new Pawn(BLACK,2,1));
		pieces.add(new Pawn(BLACK,3,1));
		pieces.add(new Pawn(BLACK,4,1));
		pieces.add(new Pawn(BLACK,5,1));
		pieces.add(new Pawn(BLACK,6,1));
		pieces.add(new Pawn(BLACK,7,1));
		pieces.add(new Knight(BLACK,1,0));
		pieces.add(new Knight(BLACK,6,0));
		pieces.add(new Bishop(BLACK,2,0));
		pieces.add(new Bishop(BLACK,5,0));
		pieces.add(new Queen(BLACK,3,0));
		pieces.add(new King(BLACK,4,0));
		pieces.add(new Rock(BLACK,0,0));
		pieces.add(new Rock(BLACK,7,0));
		
	}
	private void copyPieces(ArrayList<Piece> source, ArrayList<Piece>target) {
		target.clear();
		for(int i=0;i<source.size();i++) {
			target.add(source.get(i));
		}
	}

	
	private void update() {
		// Mouse button pressed
		if(mouse.pressed) {
			if(activeP == null) {
			// If the activeP is null , check if you can pick up a piece
			for(Piece piece : simPieces) {
				// If a mouse is on an ally piece, pick it up as acriveP
				if(piece.color == currentColor &&
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
	
	private void simulate() {
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
			if(activeP.hittingP != null) {
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
		
		//draw pieces 
		for (Piece p : simPieces) {
			p.draw(g2);
			
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
	}
	
	public void LaunchGame() {
		gameThread = new Thread(this);
		gameThread.start();
		
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