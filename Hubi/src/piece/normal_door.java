package piece;

public class normal_door  extends Piece{

	public normal_door( int col, int row) {
		super( col, row);
		this.name = "Normal_door";
		// TODO Auto-generated constructor stub
		image = getImage("/piece/normal_door");
	}

}
