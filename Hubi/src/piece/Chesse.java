package piece;

public class Chesse extends Piece{

	public Chesse(int player,int col, int row) {
		super(player, col, row);
		this.name = "Token";
		image = getImage("/piece/cheese");
		// TODO Auto-generated constructor stub
	}

}
