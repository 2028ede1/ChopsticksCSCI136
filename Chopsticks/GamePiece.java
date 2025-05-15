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
		//Scaling the sizes
		int leftWidth = leftHand.getWidth()/4; 
		int leftHeight = leftHand.getHeight()/4;
		int rightWidth = rightHand.getWidth()/4; 
		int rightHeight = rightHand.getHeight()/4;

		g.drawImage(leftHand,leftx,lefty, leftWidth, leftHeight,c); 
		g.drawImage(rightHand,rightx,righty, rightWidth, rightHeight,c); 
	}

	//These methods determine if the mouse click is within the bounds of the left and right hand.
	public boolean leftContains(int mx, int my){ 
		return mx >= leftx && mx <= leftx+(leftHand.getWidth()/4) && my >= lefty && my <= lefty+(leftHand.getHeight()/4); 
	}
	public boolean rightContains(int mx, int my){ 
		return mx >= rightx && mx <= rightx+(rightHand.getWidth()/4) && my >= righty && my <= righty+(rightHand.getHeight()/4); 
	}
	public void resetLeft(){ 
		this.leftx = origLeftx; 
		this.lefty = origLefty; 
	}
	public void resetRight(){ 
		this.rightx = origRightx; 
		this.righty = origRighty; 
	}


	public static void main(String[] args){
		//(TEST CODE)
 		JFrame frame = new JFrame("Chopsticks");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JComponent c = new JComponent(){	   
	     	OurGameState gameState = new OurGameState(new Hands(1,1), new Hands(1,1), false); 
	    	AiTurn aiLogic = new AiTurn(); 

	    	GamePiece piece; 
	    	GamePiece piece2; 
	    	GamePiece dragging = null; 
	    	String draggingHand = ""; 
	    	BufferedImage vs;
	    	BufferedImage aiImg; 


	    	int offsetX = 0; 
	    	int offsetY = 0; 
	    { 
	    	try {  
	    		updateImages(); 
	    		vs = ImageIO.read(GamePiece.class.getResource("/sprites/VS.png"));
	    		aiImg = ImageIO.read(GamePiece.class.getResource("/sprites/AI.png")); 
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

                		if(piece2.leftContains(mx,my)){ 
                			performMove("player",draggingHand, "ai", "left");
                		}else if (piece2.rightContains(mx,my)){ 
                			performMove("player",draggingHand, "ai", "right");
                		} else if (piece.leftContains(mx,my) || piece.rightContains(mx,my)){ 
                			if (gameState.getPlayerHands().canBump()){ 
                				gameState.getPlayerHands().bump(); 
                			}
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
            	

            	//Prevent moves if the hands are out
            	if ((fromHand.equals("left") && p1.isLeftHandOut()) || (fromHand.equals("right") && p1.isRightHandOut())){
            		return; 
            	}

            	if(fromHand.equals("left")){ 
            		numFingers = p1.getLeftFingers(); 
            	}else{ 
            		numFingers = p1.getRightFingers(); 
            	} 

            	if(toHand.equals("left")){ 
            		p2.addLeftHand(numFingers); 
            		System.out.println(numFingers); 
            	}else{ 
            		p2.addRightHand(numFingers); 
            		System.out.println(numFingers); 
            	}

            	if(fromPlayer.equals("player")){ 
            		gameState.setIsAiPlayerTurn(true); 
            		updateImages(); 
            		repaint();
            		SwingUtilities.invokeLater(() -> updateAiTurn());//Built in Swing method to call ai turn after the player move is performed using lambda expression
            	}else{ 
            		updateImages(); 
            		repaint();

            	}
            	
            }

         	//This helper method updates the gamestate based on the ai's turn
	        public void updateAiTurn(){ 
	        	if(!gameState.getIsAiPlayerTurn()) return; 
	        	OurGameState newState = aiLogic.findBestMove(gameState); 
	        	gameState = newState; 
	        	updateImages(); 
	        	repaint(); 
	        	gameState.setIsAiPlayerTurn(false); 
	        } 
            	

        //This helper method updates the player and ai images based on the current gamestate
        public void updateImages(){ 
        	try { 
        		BufferedImage pLeft = ImageIO.read(getClass().getResource("/sprites/left" + gameState.getPlayerHands().getLeftFingers() +".png"));
        		BufferedImage pRight = ImageIO.read(getClass().getResource("/sprites/right" + gameState.getPlayerHands().getRightFingers() +".png"));
        		BufferedImage aiLeft = ImageIO.read(getClass().getResource("/sprites/left" + gameState.getPlayerHands().getLeftFingers() +".png"));
        		BufferedImage aiRight = ImageIO.read(getClass().getResource("/sprites/right" + gameState.getPlayerHands().getRightFingers() +".png"));
        		piece = new GamePiece(pLeft, pRight, gameState.getPlayerHands(), 100, 0, 250, 0); 
        		piece2 = new GamePiece(aiLeft, aiRight, gameState.getAiHands(), 600, 0, 750, 0); 

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
				g.drawImage(vs, 425,50,nw,nh,this); 
			}
			if(aiImg != null){ 
				int nw = aiImg.getWidth()/4;
				int nh = aiImg.getHeight()/4;
				g.drawImage(aiImg, 875,25,nw,nh,this); 
			}

		} 

		@Override 
		public Dimension getPreferredSize(){ 
			return new Dimension(1000,250); 
		}
	}; 
	    frame.add(c);
	    frame.pack();
	    frame.setVisible(true);

	}

} 
