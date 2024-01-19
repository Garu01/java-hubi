package piece;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Board;
import main.GamePanel;

public class Piece {
	public BufferedImage image;
	public int x,y;
	public int col, row, preCol, preRow;
	public int color;
	public Piece hittingP;
	public boolean moved;
	
	public Piece(int color, int col, int row) {
		this.color = color;
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
		moved = true;
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
		if(targetCol >=0 && targetCol <= 7 && targetRow >=0 && targetRow<=7) {
			return true;
		}
		else {
			return false;
		}
	}
	public Piece getHittingP(int targetCol, int targetRow) {
		for(Piece piece : GamePanel.simPieces) {
			if(piece.col == targetCol && piece.row == targetRow && piece != this) {
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
		else {
			if(hittingP.color != this.color) {
				return true;
			}
			else {
				hittingP = null;
			}
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
	
	public boolean PieceIsOnStraightLine(int targetCol, int targetRow) {
		// when moving to the left
		for(int c = preCol;c>targetCol;c--) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.col==c && piece.row==targetRow) {
					hittingP = piece;
					return true;
				}
			}
		}
		// when moving to the right
		for(int c = preCol;c<targetCol;c++) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.col==c && piece.row==targetRow) {
					hittingP = piece;
					return true;
				}
			}
		}
		// when moving up
		for(int r = preRow;r>targetRow;r--) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.row==r && piece.col==targetCol) {
					hittingP = piece;
					return true;
				}
			}
		}
		// when moving down
		for(int r = preRow;r<targetRow;r++) {
			for(Piece piece : GamePanel.simPieces) {
				if(piece.row==r && piece.col==targetCol) {
					hittingP = piece;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean pieceIsOnDiagonalLine(int targetCol, int targetRow) {
		
		if( targetRow < preRow) {
		// up left
		for(int c= preCol-1;c>targetCol;c--) {
			int diff = Math.abs(c - preCol);
			for(Piece piece : GamePanel.simPieces) {
				if(piece.col == c && piece.row == preRow - diff) {
					hittingP = piece;
					return true;
				}
			}
		}
		// up right
		for(int c= preCol+1;c<targetCol;c++) {
			int diff = Math.abs(c - preCol);
			for(Piece piece : GamePanel.simPieces) {
				if(piece.col == c && piece.row == preRow - diff) {
					hittingP = piece;
					return true;
				}
			}
		}
		}
		
		if (targetRow > preRow) {
		// down left
			for(int c= preCol-1;c>targetCol;c--) {
				int diff = Math.abs(c - preCol);
				for(Piece piece : GamePanel.simPieces) {
					if(piece.col == c && piece.row == preRow + diff) {
						hittingP = piece;
						return true;
					}
				}
			}
		// down right
				for(int c= preCol+1;c<targetCol;c++) {
					int diff = Math.abs(c - preCol);
					for(Piece piece : GamePanel.simPieces) {
						if(piece.col == c && piece.row == preRow + diff) {
							hittingP = piece;
							return true;
						}
					}
				}
	}
		return false;
		}
}