package piece;

import java.io.IOException;

import javax.imageio.ImageIO;


public class mouse extends Piece {

	public mouse(int player, int col, int row) {
		super(player,col, row);
		// TODO Auto-generated constructor stub
		this.name = "Mouse";
		image = getImage("/piece/mouse");
	}
	
	public boolean canMove(int targetCol, int targetRow) {
		if(isWithinBoard(targetCol,targetRow) && isSamSquare(targetCol, targetRow)==false) {
			
			// move before the curtain
//			if (Math.abs(targetRow - preRow) + Math.abs(targetCol - preCol) == 2
//					&& Math.abs(targetRow - preRow) * Math.abs(targetCol - preCol) == 0 && 
//					wallCover(targetCol,targetRow)==true ) {
//				return true;
//			}
			
			// move after open the curtain
			if(Math.abs(targetRow - preRow) + Math.abs(targetCol - preCol) ==2 
					&& Math.abs(targetRow - preRow) * Math.abs(targetCol - preCol) == 0  
					){

				if( PieceIsOnStraightLine(targetCol,targetRow,"Rabbit_door")==false ) {
					return true;
				}
			}
		}
			return false;
	}


}