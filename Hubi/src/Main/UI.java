package Main;



import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.FlowLayout;
public class UI {

    GamePanel gp;
    Graphics2D g2;
//    Font arial_40, arial_80B;
    Font maruMonica, purisaB;
//    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: the first screen, 1: the second screen
    
	BufferedImage image = getImage("/piece/a");

//    double playTime;
//    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

//        arial_40 = new Font("Cambria", Font.PLAIN, 40);
//        arial_80B = new Font("Arial", Font.BOLD, 80);
//        OBJ_Key key = new OBJ_Key(gp);
//        keyImage = key.image;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {


        this.g2 = g2;
        
        g2.drawImage(image, 0, 0 ,GamePanel.WIDTH, GamePanel.HEIGHT,null);


//        g2.setFont(arial_40);
        g2.setFont(maruMonica);
//        g2.setFont(purisaB);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            // Do playState stuff later

        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

    }
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);

        int y = gp.HEIGHT/2;

        g2.drawString(text, x, y);
    }

    public void drawTitleScreen() {


        if (titleScreenState == 0) {

//            g2.setColor(new Color(0,0,0));
//            g2.fillRect(0, 0, gp.WIDTH, gp.HEIGHT);

        	


            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
            String text = "";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x+5, y+5);
            // MAIN COLOR
            g2.setColor(Color.black);
            g2.drawString(text, x, y);



            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

            text = "Start GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize*3.5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }



            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }
        }

    }



    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public int getXforCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.WIDTH/2 - length/2;
        return  x;
    }
    
    
    
	// get Image method
//    public static void createGameWindow(String imagePath) {
//    	 JFrame frame = new JFrame("Game Window");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setLayout(null);
//         frame.setSize(800, 600);
//         
//    	  ImagePanel imagePanel = new ImagePanel(imagePath);
//          imagePanel.setBounds(0, 0, 800, 450); // Adjust size as needed
//          frame.add(imagePanel);
//          frame.setLocationRelativeTo(null); // Center on screen
//          frame.setVisible(true);
//          
//    }
//    static class ImagePanel extends JPanel {
//        private Image image;
//
//        public ImagePanel(String imagePath) {
//            try {
//                image = ImageIO.read(new File(imagePath)); // Load the image
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2 = (Graphics2D) g;
//            if (image != null) {
//                g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this); // Draw the image scaled to fit the panel
//            }
//        }
//    }

    
//	   static class ImagePanel extends JPanel {
//	        public Image image;
//
//	        public ImagePanel(String imagePath) {
//	            try {
//	                image = ImageIO.read(new File(imagePath));
//	                Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
//	                setPreferredSize(size);
//	                setMinimumSize(size);
//	                setMaximumSize(size);
//	                setSize(size);
//	                setLayout(null);
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	        protected void paintComponent(Graphics g) {
//	            super.paintComponent(g);
//	            Graphics2D g2 = (Graphics2D) g;
//	            g2.drawImage(image, 0, 0, this); // Draws the image at the panel's top-left corner
//	        }
//	    }
    
//    /**
//     * Loads an image from the specified file path.
//     * @param filePath The path to the image file.
//     * @return The loaded image or null if the image could not be loaded.
//     */
//    public static Image loadImageFromFile(String filePath) {
//        try {
//            return ImageIO.read(new File(filePath));
//        } catch (IOException e) {
//            System.err.println("Error loading image from file: " + filePath);
//            e.printStackTrace();
//            return null;
//        }
//    }

	public BufferedImage getImage(String imagePath) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return image;
	}
    
}