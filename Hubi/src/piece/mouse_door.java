package piece;

public class mouse_door extends Piece {

	public mouse_door(int player, int col, int row) {
		super(player, col, row);
		this.name = "Mouse_door";
		// TODO Auto-generated constructor stub
		image = getImage("/piece/mouse_door");
	}

}
