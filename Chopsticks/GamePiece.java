package Chopsticks; 

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO; 
import java.awt.event.*;

public class GamePiece { 
	BufferedImage leftHand, rightHand; //The left and right hand images 
	Hands hands; //hand object that stores the values of a pair of hands
	int leftx, lefty, rightx, righty, origLeftx, origLefty, origRightx, origRighty; //the (x,y) positions of both hands
	private static double scale = 0.25; //scale factor for images 

	public GamePiece(BufferedImage leftHand, BufferedImage rightHand, Hands hands, int leftx, int lefty, int rightx, int righty){ 
		this.leftHand = leftHand; 
		this.rightHand = rightHand; 

		this.leftx = leftx; 
		this.lefty = lefty;
		this.rightx = rightx; 
		this.righty = righty; 

		this.hands = hands; 
		//original x,y positions
		this.origLeftx = leftx; 
		this.origLefty = lefty;
		this.origRightx = rightx; 
		this.origRighty = righty;

	}
	//Draws the images 
	public void draw(Graphics g, JComponent c){ 
		g.drawImage(leftHand,leftx,lefty, getLeftWidth(), getLeftHeight(),c); 
		g.drawImage(rightHand,rightx,righty, getRightWidth(), getRightHeight(),c); 
	}

	//These methods determine if the mouse click is within the bounds of the left and right hand.
	public boolean leftContains(int mx, int my){ 
		return mx >= leftx && mx <= leftx+getLeftWidth() && my >= lefty && my <= lefty+getLeftHeight(); 
	}
	public boolean rightContains(int mx, int my){ 
		return mx >= rightx && mx <= rightx+getRightWidth() && my >= righty && my <= righty+getRightHeight(); 
	}

	//These methods reset the positions of the two hand images
	public void resetLeft(){ 
		this.leftx = origLeftx; 
		this.lefty = origLefty; 
	}
	public void resetRight(){ 
		this.rightx = origRightx; 
		this.righty = origRighty; 
	}

	//These methods scale and return the heights and widths of the two hand images
	public int getLeftWidth(){ 
		return (int)(leftHand.getWidth() * scale); 
	}

	public int getLeftHeight(){ 
		return (int)(leftHand.getHeight() * scale); 
	}


	public int getRightWidth(){ 
		return (int)(rightHand.getWidth() * scale); 
	}

	public int getRightHeight(){ 
		return (int)(rightHand.getHeight() * scale); 
	}


	public static void main(String[] args){
		//(TEST CODE)
 		JFrame frame = new JFrame("Chopsticks");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JComponent c = new JComponent(){	   
	     	OurGameState gameState = new OurGameState(new Hands(1,1), new Hands(1,1), false); 
	    	AiDecisionMaker aiLogic = new AiDecisionMaker(); 

	    	GamePiece piece; 
	    	GamePiece piece2; 
	    	GamePiece dragging = null; 
	    	String draggingHand = ""; 
	    	BufferedImage vs;
	    	BufferedImage aiImg;
	    	BufferedImage youImg;  


	    	int offsetX = 0; 
	    	int offsetY = 0; 
	    { 
	    	try {  
	    		updateImages(); 
	    		vs = ImageIO.read(GamePiece.class.getResource("/sprites/VS.png"));
	    		aiImg = ImageIO.read(GamePiece.class.getResource("/sprites/AI.png")); 
	    		youImg = ImageIO.read(GamePiece.class.getResource("/sprites/You.png"));

	    		} catch (IOException e) { 
	    			e.printStackTrace(); 
	    		}

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                	int mx = e.getX(); 
                	int my = e.getY(); 

                	if(piece!=null && piece.leftContains(mx,my)){ 
                		dragging = piece; 
                		draggingHand = "left"; 
                		offsetX = mx-piece.leftx; 
                		offsetY = my-piece.lefty; 
                	}else if(piece!=null && piece.rightContains(mx,my)){ 
                		dragging = piece; 
                		draggingHand = "right"; 
                		offsetX = mx-piece.rightx; 
                		offsetY = my-piece.righty; 
                	}
                } 

                @Override
                public void mouseReleased(MouseEvent e) {
                	int mx = e.getX(); 
                	int my = e.getY();
                	if(dragging != null && dragging ==piece){ 

                		if(piece2.leftContains(mx,my) && piece2.hands.getLeftFingers() < 5){ 
                			performMove("player",draggingHand, "ai", "left");
                		}else if (piece2.rightContains(mx,my) && piece2.hands.getRightFingers() < 5){ 
                			performMove("player",draggingHand, "ai", "right");
                		} else if ((piece.leftContains(mx,my) && draggingHand == "right") || (piece.rightContains(mx,my) && draggingHand == "left")){ 
                			performMove("player",draggingHand,"player","bump"); 
                		}

            			if(draggingHand.equals("left")){ 
            				dragging.resetLeft(); 
            			} 
            			if(draggingHand.equals("right")){ 
                        	dragging.resetRight(); 
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
            			int mx = e.getX(); 
            			int my = e.getY(); 
            			int dx = mx - offsetX; 
            			int dy = my - offsetY; 

            			if(draggingHand.equals("left")){ 
                        	dragging.leftx = dx;
                        	dragging.lefty = dy;
            			}
            			if(draggingHand.equals("right")){ 
                        	dragging.rightx = dx;
                        	dragging.righty = dy;
            			}

            			repaint(); 
            		}
            	} 
            });  

            }

            public void performMove(String fromPlayer, String fromHand, String toPlayer, String toHand){ 
            	int numFingers; 
            	Hands p1,p2; 

            	System.out.println("\n--- performMove called ---");
			    System.out.println("fromPlayer: " + fromPlayer + ", fromHand: " + fromHand);
			    System.out.println("toPlayer: " + toPlayer + ", toHand: " + toHand);


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
            	
            	System.out.println("Before move: p1 = " + p1 + ", p2 = " + p2);
    			System.out.println("AI Hands state: " + gameState.getAiHands());

            	//Handles bumps 
            	if(toPlayer.equals(fromPlayer) && toHand.equals("bump") ){ 
            		if(p1.canBump()){ 
            			System.out.println("Performing bump...");
            			p1.bump(); 
            			endTurn(fromPlayer);
            		}
            		return; 
            	}

            	//Prevent moves if the hands are out
            	if ((fromHand.equals("left") && p1.isLeftHandOut()) || (fromHand.equals("right") && p1.isRightHandOut()) ){
            		System.out.println("Illegal move: trying to move from a hand that's out.");
            		return; 
            	}	

            	//initializes the current finger counter
            	if(fromHand.equals("left")){ 
            		numFingers = p1.getLeftFingers(); 
            	}else{ 
            		numFingers = p1.getRightFingers(); 
            	} 


            	if(toHand.equals("left") && p2.getLeftFingers() < 5){ 
            		p2.addLeftHand(numFingers); 
            		System.out.println("AI now has: " + p2.getLeftFingers());
            	}
            	else if (toHand.equals("right") && p2.getRightFingers() < 5){ 
            		p2.addRightHand(numFingers); 
            		System.out.println(numFingers); 
            	}

            	endTurn(fromPlayer); 
            	
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
        		piece = new GamePiece(pLeft, pRight, gameState.getPlayerHands(), 100, 0, 300, 0); 
        		piece2 = new GamePiece(aiLeft, aiRight, gameState.getAiHands(), 750, 0, 950, 0); 

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
