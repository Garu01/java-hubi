package piece;

import main.GamePanel;

public class Queen extends Piece {

	public Queen(int color, int col, int row) {
		super(color, col, row);
		// TODO Auto-generated constructor stub
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/w-queen");
		}
		else {
			image = getImage("/piece/b-queen");
		}
	}
	
	public boolean canMove(int targetCol, int targetRow) {
		if(isWithinBoard(targetCol,targetRow) && isSamSquare(targetCol,targetRow) == false) {
			if(Math.abs(targetRow - preRow) == Math.abs(targetCol - preCol) ) {
				if(isValidSquare(targetCol,targetRow) && pieceIsOnDiagonalLine( targetCol,  targetRow)==false){
					return true;	
				}
				
			}
			if(targetCol == preCol || targetRow==preRow ) {
				if(isValidSquare(targetCol,targetRow) && PieceIsOnStraightLine( targetCol,  targetRow)==false){
					return true;	
				}
		}
			
	}
		return false;
	} 
}
