package piece;

public class wall extends Piece{

	public wall(int player, int col, int row) {
		super(player, col, row);
		this.name = "wall";
		// TODO Auto-generated constructor stub
		image = getImage("/piece/wall");
	}

}
