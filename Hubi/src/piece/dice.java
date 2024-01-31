package piece;

public class dice extends Piece {

	public dice(int player, int col, int row) {
		super(player, col, row);
		// TODO Auto-generated constructor stub
		this.name = "dice";
		image = getImage("/piece/dice");
	}
	

}
