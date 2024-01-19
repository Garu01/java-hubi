package piece;

import main.GamePanel;

public class Rock extends Piece{

	public Rock(int color, int col, int row) {
		super(color, col, row);
		// TODO Auto-generated constructor stub
		if (color == GamePanel.WHITE) {
			image = getImage("/piece/w-rook");
		}
		else {
			image = getImage("/piece/b-rook");
		}
	}
	public boolean canMove(int targetCol, int targetRow) {
		if(isWithinBoard(targetCol,targetRow) && isSamSquare(targetCol,targetRow) == false) {
			if(targetCol == preCol || targetRow==preRow ) {
				if(isValidSquare(targetCol,targetRow) && PieceIsOnStraightLine( targetCol,  targetRow)==false){
					return true;	
				}
				
			}
		}
			return false;
	}

}
