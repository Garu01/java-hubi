package Main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Board {
	final int MAX_COL = 5;
	final int MAX_ROW = 5;
	public static final int SQUARE_SIZE = 100;
	public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2; 
	
//	public void draw(Graphics2D g2) {
//		int c = 0;
//		for(int row=0;row < MAX_ROW; row++) {
//			for(int col=0; col < MAX_COL;col++) {
//				if(c==0) {
//					g2.setColor(new Color(255,255,255));
//					c=1;
//				}
//				else {
//					g2.setColor(new Color(0,0,0));
//					c = 0;
//				}
//				
//				g2.fillRect(col*SQUARE_SIZE, row*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE); // x,y,w,h
//			}
//			if(c==0) {
//				c = 1;
//			}
//			else {
//				c = 0;
//			}
//		}
//	}
	public void draw(Graphics2D g2) {
		for(int row=0; row < MAX_ROW;row++) {
			if(row %2==0) {
				for(int col=0; col < MAX_COL; col++) {
					if(col %2==0) {
						g2.setColor(Color.white);
					}
					else {
						g2.setColor(Color.black);
					}
					g2.fillRect(col*SQUARE_SIZE, row*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE); // x,y,w,h
				}
			}
			else {
			for(int col=0; col < MAX_COL ; col++) {
				g2.setColor(Color.black);
				g2.fillRect(col*SQUARE_SIZE, row*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE); // x,y,w,h
			}
			}
		}
	}
}
