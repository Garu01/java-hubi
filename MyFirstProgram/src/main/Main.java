package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame("Simple chess"); // window name
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program will stop when close window
		window.setResizable(false); // no resize 
		
		// add game panel to window 
		GamePanel gp = new GamePanel();
		window.add(gp);
		window.pack();
		
		window.setLocationRelativeTo(null); // if null : show in center of screen, if top left : delete this 
		window.setVisible(true);
		
		gp.LaunchGame();
	}

}
