package Main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
public class Main {
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Hubi"); // window name
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program will stop when close window
		window.setResizable(false);// no resize
		
			
		// add game panel to window 
		GamePanel gp = new GamePanel();
		window.add(gp);

		window.pack();

		window.setLocationRelativeTo(null); // if null : show in center of screen, if top left : delete this 
		window.setVisible(true);
		
		gp.setupGame();
		gp.LaunchGame();

	}

}
