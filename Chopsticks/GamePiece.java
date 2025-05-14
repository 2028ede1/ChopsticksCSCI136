package Chopsticks; 

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO; 

public class GamePiece { 
	BufferedImage image; 
	int x, y; 

	public GamePiece(BufferedImage image, int x, int y){ 
		this.image = image; 
		this.x = x; 
		this.y = y;
	}
	//Draws the image object
	public void draw(Graphics g, JComponent c){ 
		g.drawImage(image,x,y,c); 
	}
	//This method determines if the mouse click is within the bounds of the image object.
	public boolean contains(int mx, int my){ 
		return mx >= x && mx <= x+image.getWidth() && my >= y && my <= y+image.getHeight(); 
	}

	public static void main(String[] args){
		//(TEST CODE)
 		JFrame frame = new JFrame("Image Display");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JComponent c = new JComponent(){
	    	GamePiece piece; 
	    { 
	    	try {  
	    		BufferedImage img = ImageIO.read(GamePiece.class.getResource("/sprites/right5.png")); 
	    		piece = new GamePiece(img, 100, 100); 
	    		} catch (IOException e) { 
	    			e.printStackTrace(); 
	    		}
		}

		@Override 
		protected void paintComponent(Graphics g){ 
			super.paintComponent(g); 
			if(piece != null){ 
				piece.draw(g, this); 
			}
		} 

		@Override 
		public Dimension getPreferredSize(){ 
			return new Dimension(1000,1000); 
		}
	}; 
	    frame.add(c);
	    frame.pack();
	    frame.setVisible(true);

	}

}