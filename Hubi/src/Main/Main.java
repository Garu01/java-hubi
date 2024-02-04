package Main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javazoom.jl.player.Player;

import javax.swing.JButton;
public class Main {

	public static void main(String[] args) {
		//String musicFilePath = "/Hubi/res/sound/music.mp3";
		
		JFrame window = new JFrame("Hubi"); // window name
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program will stop when close window
		window.setResizable(false);// no resize
		

		// add game panel to window 
		GamePanel gp = new GamePanel();
		window.add(gp);

		window.pack();

		window.setLocationRelativeTo(null); // if null : show in center of screen, if top left : delete this 
		window.setVisible(true);
		//playMusicInBackground(musicFilePath);
		
		gp.setupGame();
		gp.LaunchGame();

	}
	
	
	private static void playMusicInBackground(String musicFilePath) {
        // Use a separate thread for music playback to keep the UI responsive
        new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(musicFilePath);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {
                Player player = new Player(bis);
                player.play();
            } catch (Exception e) {
                System.out.println("Problem playing file " + musicFilePath);
                e.printStackTrace();
            }
        }).start();
    }

}
