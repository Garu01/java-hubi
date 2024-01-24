package piece;

public class wallcover extends Piece {

	public wallcover(int player, int col, int row) {
		super(player, col, row);
		// TODO Auto-generated constructor stub
		this.name = "wallcover";
		image = getImage("/piece/wallcover");
	}

}
