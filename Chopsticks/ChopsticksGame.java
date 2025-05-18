package Chopsticks; 

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO; 
import java.awt.event.*;

public class ChopsticksGame { 

	//The left and right hand images 
	BufferedImage leftHandImage;
	BufferedImage rightHandImage; 

	//hand object that stores the values of a pair of hands
	Hands hands; 

	//the (x,y) positions of both hands
	int leftHandXCord;
	int leftHandYCord; 
	int rightHandXCord;
	int rightHandYCord;

	// the (x,y) positions of both hands to reset to
	int ogLeftHandXCord;
	int ogLeftHandYCord;
	int ogRightHandXCord;
	int ogRightHandYCord; 

	//scale factor for images 
	private static double scale = 0.25;

	public ChopsticksGame(BufferedImage leftHand, BufferedImage rightHand, Hands hands, int leftx, int lefty, int rightx, int righty){ 
		this.leftHandImage = leftHand; 
		this.rightHandImage = rightHand; 

		this.leftHandXCord = leftx;
		this.leftHandYCord = lefty;
		this.rightHandXCord = rightx; 
		this.rightHandYCord = righty; 

		this.hands = hands; 

		//original x,y positions
		this.ogLeftHandXCord = leftHandXCord; 
		this.ogLeftHandYCord = leftHandYCord;
		this.ogRightHandXCord = rightHandXCord; 
		this.ogRightHandYCord = rightHandYCord;

	}

	//Draws the images 
	public void draw(Graphics g, JComponent c){ 
		g.drawImage(leftHandImage,leftHandXCord,leftHandYCord, getLeftHandImageWidth(), getLeftHandImageHeight(),c); 
		g.drawImage(rightHandImage,rightHandXCord,rightHandYCord, getRightHandImageWidth(), getRightHandImageHeight(),c); 
	}

	//These methods determine if the mouse click is within the bounds of the left and right hand.
	public boolean leftHandContains(int mouseX, int mouseY){ 
		return mouseX >= leftHandXCord && mouseX <= leftHandXCord+getLeftHandImageWidth() && mouseY >= leftHandYCord && mouseY <= leftHandYCord+getLeftHandImageHeight(); 
	}
	public boolean rightHandContains(int mouseX, int mouseY){ 
		return mouseX >= rightHandXCord && mouseX <= rightHandXCord+getRightHandImageWidth() && mouseY >= rightHandYCord && mouseY <= rightHandYCord+getRightHandImageHeight(); 
	}

	//These methods reset the positions of the two hand images
	public void resetLeftHand(){ 
		this.leftHandXCord = ogLeftHandXCord; 
		this.leftHandYCord = ogLeftHandYCord; 
	}
	public void resetRightHand(){ 
		this.rightHandXCord = ogRightHandXCord; 
		this.rightHandYCord = ogRightHandYCord; 
	}

	//These methods scale and return the heights and widths of the two hand images
	public int getLeftHandImageWidth(){ 
		return (int)(leftHandImage.getWidth() * scale); 
	}

	public int getLeftHandImageHeight(){ 
		return (int)(leftHandImage.getHeight() * scale); 
	}


	public int getRightHandImageWidth(){ 
		return (int)(rightHandImage.getWidth() * scale); 
	}

	public int getRightHandImageHeight(){ 
		return (int)(rightHandImage.getHeight() * scale); 
	}

	public void initializeGame() {

	}

	public static void main(String[] args){
		//(TEST CODE)

 		JFrame frame = new JFrame("Chopsticks");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JComponent c = new JComponent(){	   
	     	OurGameState gameState = new OurGameState(new Hands(1,1), new Hands(1,1), false); 
	    	AiDecisionMaker aiLogic = new AiDecisionMaker(); 

	    	ChopsticksGame piece; 
	    	ChopsticksGame piece2; 
	    	ChopsticksGame dragging = null; 
	    	String draggingHand = ""; 
	    	BufferedImage vs;
	    	BufferedImage aiImg;
	    	BufferedImage youImg;  


	    	int offsetX = 0; 
	    	int offsetY = 0; 
	    { 
	    	try {  
	    		updateImages(); 
	    		vs = ImageIO.read(ChopsticksGame.class.getResource("/sprites/VS.png"));
	    		aiImg = ImageIO.read(ChopsticksGame.class.getResource("/sprites/AI.png")); 
	    		youImg = ImageIO.read(ChopsticksGame.class.getResource("/sprites/You.png"));

	    		} catch (IOException e) { 
	    			e.printStackTrace(); 
	    		}

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                	int mouseX = e.getX(); 
                	int mouseY = e.getY(); 

                	if(piece!=null && piece.leftHandContains(mouseX,mouseY)){ 
                		dragging = piece; 
                		draggingHand = "left"; 
                		offsetX = mouseX-piece.leftHandXCord; 
                		offsetY = mouseY-piece.leftHandYCord; 
                	}else if(piece!=null && piece.rightHandContains(mouseX,mouseY)){ 
                		dragging = piece; 
                		draggingHand = "right"; 
                		offsetX = mouseX-piece.rightHandXCord; 
                		offsetY = mouseY-piece.rightHandYCord; 
                	}
                } 

                @Override
                public void mouseReleased(MouseEvent e) {
                	int mouseX = e.getX(); 
                	int mouseY = e.getY();
                	if(dragging != null && dragging ==piece){ 

                		if(piece2.leftHandContains(mouseX,mouseY) && piece2.hands.getLeftFingers() < 5){ 
                			performMove("player",draggingHand, "ai", "left");
                		}else if (piece2.rightHandContains(mouseX,mouseY) && piece2.hands.getRightFingers() < 5){ 
                			performMove("player",draggingHand, "ai", "right");
                		} else if ((piece.leftHandContains(mouseX,mouseY) && draggingHand == "right") || (piece.rightHandContains(mouseX,mouseY) && draggingHand == "left")){ 
                			performMove("player",draggingHand,"player","bump"); 
                		}

            			if(draggingHand.equals("left")){ 
            				dragging.resetLeftHand(); 
            			} 
            			if(draggingHand.equals("right")){ 
                        	dragging.resetRightHand(); 
            			}
            			repaint(); 
                	}
                    dragging = null;
                    draggingHand = ""; 
                }
            });

            addMouseMotionListener(new MouseMotionAdapter(){ 
            	@Override
            	public void mouseDragged(MouseEvent e) {
            		if(dragging != null){ 
            			int mouseX = e.getX(); 
            			int mouseY = e.getY(); 
            			int dx = mouseX - offsetX; 
            			int dy = mouseY - offsetY; 

            			if(draggingHand.equals("left")){ 
                        	dragging.leftHandXCord = dx;
                        	dragging.leftHandYCord = dy;
            			}
            			if(draggingHand.equals("right")){ 
                        	dragging.rightHandXCord = dx;
                        	dragging.rightHandYCord = dy;
            			}

            			repaint(); 
            		}
            	} 
            });  

            }

            public void performMove(String fromPlayer, String fromHand, String toPlayer, String toHand){ 
            	int numFingers; 
            	Hands p1,p2; 

            	//initializes the hands based on gamestate 
            	if(fromPlayer.equals("player")){ 
            		p1 = gameState.getPlayerHands(); 
            	}else{ 
            		p1 = gameState.getAiHands(); 

            	}

            	if(toPlayer.equals("player")){ 
            		p2 = gameState.getPlayerHands(); 
            	}else{ 
            		p2 = gameState.getAiHands(); 
            	}
            	
            	System.out.println("--- PERFORM MOVE CALLED ---");

            	//Handles bumps 
            	if(toPlayer.equals(fromPlayer) && toHand.equals("bump") ){ 
            		if(p1.canBump()){ 
            			p1.bump(); 
            			endTurn(fromPlayer);
            			System.out.println("THE " + fromPlayer + " DRAGGED THEIR " + fromHand + "HAND " + "TO THE " + " OUT HAND TO BUMP.");
            			System.out.println("PERFORMING BUMP............PLAYER NOW HAS " + p1.getLeftFingers() + " FINGERS ON BOTH HANDS");
            			System.out.println("....AI NOW MAKES A MOVE....");
		            	System.out.println("....IS IS NOW YOUR TURN....");
		            	System.out.println("----------------------------------------------------------------------");
            		}
            		return; 
            	}

            	//Prevent moves if the hands are out
            	if ((fromHand.equals("left") && p1.isLeftHandOut()) || (fromHand.equals("right") && p1.isRightHandOut()) ){
            		System.out.println("ILLEGAL MOVE: CANNOT ADD FINGERS FROM A HAND THAT IS ALREADY OUT.........");
            		return; 
            	}	

            	System.out.println("THE " + fromPlayer + " DRAGGED THEIR " + fromHand + "HAND " + "TO THE " + toPlayer + " "+ toHand + " HAND.");
            	System.out.println("BEFORE THE MOVE AI HAS " + p2.getLeftFingers() + " LEFT FINGER(s) AND " + p2.getRightFingers() + " RIGHT FINGER(s).");

            	//initializes the current finger counter
            	if(fromHand.equals("left")){ 
            		numFingers = p1.getLeftFingers(); 
            	}else{ 
            		numFingers = p1.getRightFingers(); 
            	} 


            	if(toHand.equals("left") && p2.getLeftFingers() < 5){ 
            		p2.addLeftHand(numFingers); 
            		System.out.println("AFTER MOVE AI NOW HAS " + p2.getLeftFingers() + " LEFT FINGERS.");
            	}
            	else if (toHand.equals("right") && p2.getRightFingers() < 5){ 
            		p2.addRightHand(numFingers); 
            		System.out.println("AFTER MOVE AI NOW HAS " + p2.getRightFingers() + " RIGHT FINGERS(s).");
            	}

            	endTurn(fromPlayer); 
            	System.out.println("....AI NOW MAKES A MOVE....");
            	System.out.println("....IS IS NOW YOUR TURN....");
            	System.out.println("----------------------------------------------------------------------");
            }
            //Helper method for determining the game ending conditions 
            public void endTurn(String fromPlayer){ 
            	//Win Condition: If either Ai or Player hands are both out
            	if(gameState.getPlayerHands().bothHandsOut() || gameState.getAiHands().bothHandsOut()){ 
            		gameState.printWinner(); 
            		return; 
            	}

            	if(fromPlayer.equals("player")){ 
            		gameState.setIsAiPlayerTurn(true); 
            		updateImages(); 
            		repaint();
            		SwingUtilities.invokeLater(() -> updateAiDecisionMaker()); //Built in Swing method to call ai turn after the player move is performed using lambda expression
            	}else{ 
            		gameState.setIsAiPlayerTurn(false); 
            		updateImages(); 
            		repaint(); 
            	}

            }

         	//This helper method updates the gamestate based on the ai's turn
	        public void updateAiDecisionMaker(){ 
	        	if(!gameState.getIsAiPlayerTurn()) return; 
	        	OurGameState newState = aiLogic.findBestMove(gameState); 
	        	gameState = newState; 

	        	// EMMANUEL: ADDED THESE TWO LINES for properly reflecting state after ai wins
	        	updateImages();
	        	repaint();

				if(gameState.getPlayerHands().bothHandsOut() || gameState.getAiHands().bothHandsOut()){ 
            		gameState.printWinner(); 
            		return; 
            	}

	        	updateImages(); 
	        	repaint(); 
	        	gameState.setIsAiPlayerTurn(false); 
	        } 
            	

        //This helper method updates the player and ai images based on the current gamestate
        public void updateImages(){ 
        	try { 
        		BufferedImage pLeft = ImageIO.read(getClass().getResource("/sprites/left" + gameState.getPlayerHands().getLeftFingers() +".png"));
        		BufferedImage pRight = ImageIO.read(getClass().getResource("/sprites/right" + gameState.getPlayerHands().getRightFingers() +".png"));

        		// EMMANUEL: ALL I DID HERE WAS CHANGE to properly use getAiHands instead of getPlayerHands
        		BufferedImage aiLeft = ImageIO.read(getClass().getResource("/sprites/left" + gameState.getAiHands().getLeftFingers() +".png"));
        		BufferedImage aiRight = ImageIO.read(getClass().getResource("/sprites/right" + gameState.getAiHands().getRightFingers() +".png"));
        		piece = new ChopsticksGame(pLeft, pRight, gameState.getPlayerHands(), 100, 0, 300, 0); 
        		piece2 = new ChopsticksGame(aiLeft, aiRight, gameState.getAiHands(), 750, 0, 950, 0); 

        	} catch (IOException e) { 
        		e.printStackTrace(); 
        	}

        }
        //Draws the images on the canvas
		@Override 
		protected void paintComponent(Graphics g){ 
			super.paintComponent(g); 
			if(piece != null) piece.draw(g, this); 	
			if(piece2 != null) piece2.draw(g, this);

			if(vs != null){ 
				int nw = vs.getWidth()/4;
				int nh = vs.getHeight()/4;
				g.drawImage(vs, 550,50,nw,nh,this); 
			}
			if(aiImg != null){ 
				int nw = aiImg.getWidth()/4;
				int nh = aiImg.getHeight()/4;
				g.drawImage(aiImg, 1100,25,nw,nh,this); 
			}
			if(youImg != null){ 
				int nw = aiImg.getWidth()/4;
				int nh = aiImg.getHeight()/4;
				g.drawImage(youImg, 50,25,nw,nh,this); 
			}

		} 

		@Override 
		public Dimension getPreferredSize(){ 
			return new Dimension(1920,1080); 
		}
	}; 
	    frame.add(c);
	    frame.pack();
	    frame.setVisible(true);

	}

} 
