package Chopsticks; 

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//Notes for future: Create multiple Image objects. Use the current game state to determine the filepath for a hand. 

public class HandsGraphics{ 

	class ImageComponent extends JComponent{
        private BufferedImage image;

        public ImageComponent(String path){ 
        	try { 
        		image = ImageIO.read(getClass().getResource(path)); 
        	} catch (IOException e) { 
        		e.printStackTrace(); 
        	}
        }
        @Override
        protected void paintComponent(Graphics g){ 
        	super.paintComponent(g); 
        	if (image !=null){ 
        		g.drawImage(image, 0,0, this); 
        	}	
        }
    } 

    //when a valid drag is done, update the photos and hand objects 

	public HandsGraphics(){ 
	} 

	public static void main(String[] args){ 
		HandsGraphics hg = new HandsGraphics(); 
	    JFrame frame = new JFrame("Image Display");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    ImageComponent imageComponent = hg.new ImageComponent("/sprites/right5.png");
	    frame.add(imageComponent);
	    frame.pack();
	    frame.setVisible(true);

	}
}