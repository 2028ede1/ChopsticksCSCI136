package Chopsticks; 

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO; 

public class GamePiece { 
	BufferedImage leftHand, rightHand; //The left and right hand images 
	Hands hands; //hand object that stores the values of a pair of hands
	int leftx, lefty, rightx, righty; //the (x,y) positions of both hands

	public GamePiece(BufferedImage leftHand, BufferedImage rightHand, Hands hands, int leftx, int lefty, int rightx, int righty){ 
		this.leftHand = leftHand; 
		this.rightHand = rightHand; 
		this.leftx = leftx; 
		this.lefty = lefty;
		this.rightx = rightx; 
		this.righty = righty; 
		this.hands = hands; 
	}
	//Draws the images 
	public void draw(Graphics g, JComponent c){ 
		int leftWidth = leftHand.getWidth()/4; 
		int leftHeight = leftHand.getHeight()/4;
		int rightWidth = rightHand.getWidth()/4; 
		int rightHeight = rightHand.getHeight()/4;

		g.drawImage(leftHand,leftx,lefty, leftWidth, leftHeight,c); 
		g.drawImage(rightHand,rightx,righty, rightWidth, rightHeight,c); 
	}

	//This method determines if the mouse click is within the bounds of the left and right hand.
	public boolean leftContains(int mx, int my){ 
		return mx >= leftx && mx <= leftx+leftHand.getWidth() && my >= lefty && my <= lefty+leftHand.getHeight(); 
	}
	public boolean rightContains(int mx, int my){ 
		return mx >= rightx && mx <= rightx+rightHand.getWidth() && my >= righty && my <= righty+rightHand.getHeight(); 
	}

	public static void main(String[] args){
		//(TEST CODE)
 		JFrame frame = new JFrame("Image Display");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JComponent c = new JComponent(){
	    	GamePiece piece; 
	    	GamePiece piece2; 
	    { 
	    	try {  
	    		BufferedImage left = ImageIO.read(GamePiece.class.getResource("/sprites/left1.png")); 
	    		BufferedImage right = ImageIO.read(GamePiece.class.getResource("/sprites/right1.png")); 
	    		Hands hands = new Hands(1,1); 
	    		Hands hands2 = new Hands(1,1); 
	    		piece = new GamePiece(left,right, hands, 100, 100, 250 ,100); 
	    		piece2 = new GamePiece(left,right, hands2, 450, 100, 600 ,100); 
	    		



	    		} catch (IOException e) { 
	    			e.printStackTrace(); 
	    		}
		}

		@Override 
		protected void paintComponent(Graphics g){ 
			super.paintComponent(g); 
			if(piece != null && piece2 != null){ 
				piece.draw(g, this); 
				piece2.draw(g, this); 
			}
		} 

		@Override 
		public Dimension getPreferredSize(){ 
			return new Dimension(3000,3000); 
		}
	}; 
	    frame.add(c);
	    frame.pack();
	    frame.setVisible(true);

	}

}