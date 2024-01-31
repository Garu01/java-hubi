package piece;

public class clock extends Piece{

	public clock(int player, int col, int row) {
		super(player, col, row);
		// TODO Auto-generated constructor stub
		this.name = "Clock";
		image = getImage("/piece/clock");
	}

}
