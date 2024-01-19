package piece;

import main.GamePanel;

public class Bishop extends Piece {

	public Bishop(int color, int col, int row) {
		super(color, col, row);
		// TODO Auto-generated constructor stub
		if (color == GamePanel.WHITE) {
			image = getImage("/piece/w-bishop");
		}
		else {
			image = getImage("/piece/b-bishop");
		}
	}
	
	public boolean canMove(int targetCol, int targetRow) {
		if(isWithinBoard(targetCol,targetRow) && isSamSquare(targetCol,targetRow) == false) {
			if(Math.abs(targetRow - preRow) == Math.abs(targetCol - preCol) ) {
				if(isValidSquare(targetCol,targetRow) && pieceIsOnDiagonalLine( targetCol,  targetRow)==false){
					return true;	
				}
				
			}
		}
			return false;
	}

}
