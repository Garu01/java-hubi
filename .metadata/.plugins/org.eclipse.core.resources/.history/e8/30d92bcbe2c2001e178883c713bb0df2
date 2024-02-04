package piece;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.Board;
import Main.GamePanel;

public class Piece {
	public BufferedImage image;
	public int x,y;
	public int col, row, preCol, preRow;
	public Piece hittingP;
	public Piece hittingT;
	public String name;
	public int player;
	
	public Piece(int player, int col, int row) {
		this.player = player;
		this.col = col;
		this.row = row;
		x = getX(col);
		y = getY(row);
		preCol = col;
		preRow = row;
	}
	
	// get Image method
	public BufferedImage getImage(String imagePath) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return image;
	}
	public int getX(int col) {
		return col* Board.SQUARE_SIZE;
	}
	public int getY(int row) {
		return row * Board.SQUARE_SIZE;
	}
	public int getCol(int x) {
		return (x+ Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
	}
	public int getRow(int y) {
		return(y + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE,null);
	}
	public void updatePosition() {
		x = getX(col);
		y = getY(row);
		preCol = getCol(x);
		preRow = getRow(y);
	}
	public void resetPosition() {
		col = preCol;
		row = preRow;
		x = getX(col);
		y = getY(row);
	}
	public boolean canMove(int targetCol, int targetRow) {
		return false;
	}
	public boolean isWithinBoard(int targetCol, int targetRow) {
		if(targetCol >=1 && targetCol <= 5 && targetRow >=0 && targetRow<=5) {
			return true;
		}
		else {
			return false;
		}
	}
	public Piece getHittingP(int targetCol, int targetRow) {
		for(Piece piece : GamePanel.simPieces) {
			if(piece.col == targetCol && piece.row == targetRow && piece != this  ) {
				return piece;
			}
		}
		return null;
	}
	
	// hitting token
	public Piece getHittingT(int targetCol, int targetRow) {
		for(Piece piece : GamePanel.simPieces) {
			if(piece.col == targetCol && piece.row == targetRow && piece != this  ) {
				return piece;
			}
		}
		return null;
	}
	
	
	
	public Piece isMagicDoor() {
		for(Piece piece : GamePanel.simPieces) {
			if( piece.name == "Magic_door"  ) {
				return piece;
			}
		}
		return null;
	}
	
	public boolean isValidSquare(int targetCol, int targetRow) {
		hittingP = getHittingP(targetCol,targetRow);
		
		if (hittingP == null) {
			return true;
		}
		
		return false;
	}
	
	public int getIndex() {
		for(int index=0;index<GamePanel.simPieces.size();index++) {
			if(GamePanel.simPieces.get(index)==this) {
				return index;
			}
		}
		return 0;
	}
	public boolean isSamSquare(int targetCol, int targetRow) {
		if (preCol == targetCol && preRow == targetRow) {
			return true;
		}
		return false;
	}
	
	public boolean isWall(int targetCol, int targetRow) {
		for(Piece piece : GamePanel.simPieces) {
			if(piece.col==targetCol && piece.row==targetRow && piece.name == "wallcover" ) {
				return true;
			}
	}
		return false;
	}
	
	public boolean wallCover(int targetCol, int targetRow) {
		// when moving to the left
		for(int c = preCol;c>targetCol;c--) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.col==c && piece.row==targetRow && piece.name == "wallcover" ) {
					hittingP = piece;
					return true;
				}
			}
		}
		// when moving to the right
		for(int c = preCol;c<targetCol;c++) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.col==c && piece.row==targetRow && piece.name == "wallcover") {
					hittingP = piece;
					return true;
				}
			}
		}
		// when moving up
		for(int r = preRow;r>targetRow;r--) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.row==r && piece.col==targetCol && piece.name == "wallcover") {
					hittingP = piece;
					return true;
				}
			}
		}
		// when moving down
		for(int r = preRow;r<targetRow;r++) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.row==r && piece.col==targetCol && piece.name == "wallcover") {
					hittingP = piece;
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean PieceIsOnStraightLine(int targetCol, int targetRow, String door) {
		// when moving to the left
		for(int c = preCol;c>targetCol;c--) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.col==c && piece.row==targetRow && piece.name == door ) {

					return true;
				}
			}
		}
		// when moving to the right
		for(int c = preCol;c<targetCol;c++) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.col==c && piece.row==targetRow && piece.name == door) {

					return true;
				}
			}
		}
		// when moving up
		for(int r = preRow;r>targetRow;r--) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.row==r && piece.col==targetCol && piece.name == door) {

					return true;
				}
			}
		}
		// when moving down
		for(int r = preRow;r<targetRow;r++) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.row==r && piece.col==targetCol && piece.name==door) {
					return true;
				}
			}
		}
		return false;
	}
	
	// check type wall
	public boolean typeWall(int targetCol,int targetRow, String door) {
	
	// go left
	for(Piece piece : GamePanel.simPieces) {
	if(piece.row == (targetRow) && piece.col == (targetCol+1) && piece.name == door ) {
		//hittingP = piece;
		return true;
	}
	}
	
	// go right
	for (Piece piece : GamePanel.simPieces) {
		if(piece.row == (targetRow) && piece.col == (targetCol-1) && piece.name== door )
		{
			//hittingP = piece;
			return true;
		}
	}
	
	// go up 
	for (Piece piece : GamePanel.simPieces) {
		if(piece.row == (targetRow+1) && piece.col == (targetCol) && piece.name == door) {
			//hittingP = piece;
			return true;
		}
	}
	
	// go down
	for(Piece piece : GamePanel.simPieces) {
		if(piece.row == (targetRow-1) && piece.col == (targetCol) && piece.name == door) {
			//hittingP = piece;
			return true;
		}
	}
	return false;
	}
	
	
	//  check token
	public boolean isToken(int targetCol, int targetRow) {
		for(Piece piece : GamePanel.simPieces) {
			if(piece.row == targetRow && piece.col==targetCol && piece.name=="Token") {
				hittingP = piece;
				return true;
			}
		}
		return false;
	}
	

	
	

	
	
	


	
}
