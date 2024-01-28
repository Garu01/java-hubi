package piece;

public class Carrot extends Piece{

	public Carrot(int player, int col, int row) {
		super(player, col, row);
		this.name = "Token";
		image = getImage("/piece/carrot");
		// TODO Auto-generated constructor stub
	}

}
