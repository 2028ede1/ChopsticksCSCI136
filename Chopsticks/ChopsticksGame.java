package Chopsticks; 

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO; 
import java.awt.event.*;

/**
 * Manages visual/interactive components of the Chopsticks game. It displays
 * and updates player and AI hand images to reflect the current finger counts of the 
 * current game state, handles drag and drop interactions and executes game logic
 * for adding fingers to hands as well as bumping.
 */
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

	// original coordinates for resetting hand positions after drag and drop
	int ogLeftHandXCord;
	int ogLeftHandYCord;
	int ogRightHandXCord;
	int ogRightHandYCord; 

	// scale factor for imamge sizing
	private static double scale = 0.50;

	// constructor
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

	/**
	 * Method for drawing images onto the GUI window
	 */
	public void draw(Graphics g, JComponent c){ 
		g.drawImage(leftHandImage,leftHandXCord,leftHandYCord, getLeftHandImageWidth(), getLeftHandImageHeight(),c); 
		g.drawImage(rightHandImage,rightHandXCord,rightHandYCord, getRightHandImageWidth(), getRightHandImageHeight(),c); 
	}

	/**
	 * Mouse boundary detection for both hands.
	 * @param mouseX x position of the mouse
	 * @param mouseY y position of the mouse
	 * @return boolean of whether or not the coordinates are within the boundary boxes for the hadns
	 */
	public boolean leftHandContains(int mouseX, int mouseY){ 
		return mouseX >= leftHandXCord && mouseX <= leftHandXCord+getLeftHandImageWidth() && mouseY >= leftHandYCord && mouseY <= leftHandYCord+getLeftHandImageHeight(); 
	}
	public boolean rightHandContains(int mouseX, int mouseY){ 
		return mouseX >= rightHandXCord && mouseX <= rightHandXCord+getRightHandImageWidth() && mouseY >= rightHandYCord && mouseY <= rightHandYCord+getRightHandImageHeight(); 
	}

	/**
	 * These two methods reset the positions of the two hand images
	 */
	public void resetLeftHand(){ 
		this.leftHandXCord = ogLeftHandXCord; 
		this.leftHandYCord = ogLeftHandYCord; 
	}
	public void resetRightHand(){ 
		this.rightHandXCord = ogRightHandXCord; 
		this.rightHandYCord = ogRightHandYCord; 
	}

	/**
	 * These four methods scale and return the heights and widths of the two hand image.
	 * @return integer of the width/height for the corresponding hand image loaded
	 */
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


	public static void main(String[] args){

 		JFrame frame = new JFrame("Chopsticks");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    // Handles rendering of images and drag and drop interactions for the game
	    JComponent c = new JComponent(){

	    	// The gamestate object used for the game logic
	     	OurGameState gameState = new OurGameState(new Hands(1,1), new Hands(1,1), false); 

	     	// Used for finding the most optimal move resulting game state
	    	AiDecisionMaker aiDecision = new AiDecisionMaker(); 

	    	// Player and AI game visuals (each holding two hand images and their positions)
	    	ChopsticksGame playerHandsUI; 
	    	ChopsticksGame aiHandsUI; 

	    	// The currently dragged hand object and which hand is being dragged
	    	ChopsticksGame draggedPiece = null; 
	    	String draggingHandSide = ""; 

	    	// Other UI image resources
	    	BufferedImage vs;
	    	BufferedImage aiLabelImg;
	    	BufferedImage youLabelImg;  
	    	String winnerTxt = null; 

	    	// Offset between mouse position and hand corner when dragging an image
	    	int offsetX = 0; 
	    	int offsetY = 0; 
	    	// For loading images and mouse listeners
	    	{ 

	    	try {  
	    		updateImages(); 
	    		vs = ImageIO.read(ChopsticksGame.class.getResource("/sprites/VS.png"));
	    		aiLabelImg = ImageIO.read(ChopsticksGame.class.getResource("/sprites/AI.png")); 
	    		youLabelImg = ImageIO.read(ChopsticksGame.class.getResource("/sprites/You.png"));

	    		} 
	    		catch (IOException e) { 
	    			e.printStackTrace(); 
	    		}

            addMouseListener(new MouseAdapter() {
                @Override

                /**
                 * Begins image draggedPiece if the mouse position is on either player
                 * hands.
                 */
                public void mousePressed(MouseEvent e) {
                	int mouseX = e.getX(); 
                	int mouseY = e.getY(); 

                	//Initializes the draggedPiece playerHandsUI object based on where the mouse was pressed. 
                	if(playerHandsUI!=null && playerHandsUI.leftHandContains(mouseX,mouseY)){ 
                		draggedPiece = playerHandsUI; 
                		draggingHandSide = "left"; 
                		offsetX = mouseX-playerHandsUI.leftHandXCord; 
                		offsetY = mouseY-playerHandsUI.leftHandYCord; 
                	}
                	else if(playerHandsUI!=null && playerHandsUI.rightHandContains(mouseX,mouseY)){ 
                		draggedPiece = playerHandsUI; 
                		draggingHandSide = "right"; 
                		offsetX = mouseX-playerHandsUI.rightHandXCord; 
                		offsetY = mouseY-playerHandsUI.rightHandYCord; 
                	}
                } 

                @Override

                /**
                 * Attempts to perform move based on where the player dropped the image.
                 */ 
                public void mouseReleased(MouseEvent e) {
                	int mouseX = e.getX(); 
                	int mouseY = e.getY();
                	if(draggedPiece != null && draggedPiece == playerHandsUI){ 

                		if (!gameState.getPlayerHands().bothHandsOut() && !gameState.getAiHands().bothHandsOut()) {

                			if(aiHandsUI.leftHandContains(mouseX,mouseY) && aiHandsUI.hands.getLeftFingers() < 5){ 
                			performMove("player",draggingHandSide, "ai", "left");
	                		}
	                		else if (aiHandsUI.rightHandContains(mouseX,mouseY) && aiHandsUI.hands.getRightFingers() < 5){ 
	                			performMove("player",draggingHandSide, "ai", "right");
	                		} 

	                		else if ((playerHandsUI.leftHandContains(mouseX,mouseY) && draggingHandSide == "right") || (playerHandsUI.rightHandContains(mouseX,mouseY) && draggingHandSide == "left")){ 
	                			performMove("player",draggingHandSide,"player","bump"); 
	                		}
                		}


                		 // Reset dragged hand's position after the image is dropped
            			if(draggingHandSide.equals("left")){ 
            				draggedPiece.resetLeftHand(); 
            			} 
            			if(draggingHandSide.equals("right")){ 
                        	draggedPiece.resetRightHand(); 
            			}
            			repaint(); 
                	}
                    draggedPiece = null;
                    draggingHandSide = ""; 
                }
            });

            // Update hand position while draggedPiece
            addMouseMotionListener(new MouseMotionAdapter(){ 
            	@Override
            	public void mouseDragged(MouseEvent e) {
            		if(draggedPiece != null){ 
            			int mouseX = e.getX(); 
            			int mouseY = e.getY(); 
            			int dx = mouseX - offsetX; 
            			int dy = mouseY - offsetY; 

            			if(draggingHandSide.equals("left")){ 
                        	draggedPiece.leftHandXCord = dx;
                        	draggedPiece.leftHandYCord = dy;
            			}
            			if(draggingHandSide.equals("right")){ 
                        	draggedPiece.rightHandXCord = dx;
                        	draggedPiece.rightHandYCord = dy;
            			}

            			repaint(); 
            		}
            	} 
            });  

            }

            /**
         	* Handles logic for adding fingers to the Ai's hands or bumping fingers to 
         	* the other players hand if allowed.
         	*/
            public void performMove(String fromPlayer, String fromHand, String toPlayer, String toHand){ 
            	int numFingers; 
            	Hands p1,p2; 

            	//initializes hand objects based on gamestate 
            	if(fromPlayer.equals("player")){ 
            		p1 = gameState.getPlayerHands(); 
            	}
            	else{ 
            		p1 = gameState.getAiHands(); 

            	}

            	if(toPlayer.equals("player")){ 
            		p2 = gameState.getPlayerHands(); 
            	}
            	else{ 
            		p2 = gameState.getAiHands(); 
            	}
            	
            	System.out.println("--- PERFORM MOVE CALLED ---");

            	// Handles bumps 
            	if(toPlayer.equals(fromPlayer) && toHand.equals("bump") ){ 
            		if(p1.canBump()){ 
            			p1.bump(); 
            			endTurn(fromPlayer);
            			System.out.println("THE " + fromPlayer + " DRAGGED THEIR " + fromHand + "HAND " + "TO THE " + " OTHER HAND TO BUMP.");
            			System.out.println("PERFORMING BUMP............PLAYER NOW HAS " + p1.getLeftFingers() + " FINGERS ON BOTH HANDS");
            			System.out.println("....AI NOW MAKES A MOVE....");
		            	System.out.println("....IT IS NOW YOUR TURN....");
		            	System.out.println("----------------------------------------------------------------------");
            		}
            		return; 
            	}

            	// Prevent moves if the hands are out
            	if ((fromHand.equals("left") && p1.isLeftHandOut()) || (fromHand.equals("right") && p1.isRightHandOut()) ){
            		System.out.println("ILLEGAL MOVE: CANNOT ADD FINGERS FROM A HAND THAT IS ALREADY OUT.........");
            		return; 
            	}	

            	System.out.println("THE " + fromPlayer + " DRAGGED THEIR " + fromHand + "HAND " + "TO THE " + toPlayer + " "+ toHand + " HAND.");
            	System.out.println("BEFORE THE MOVE AI HAS " + p2.getLeftFingers() + " LEFT FINGER(s) AND " + p2.getRightFingers() + " RIGHT FINGER(s).");

            	// Get finger count from attacking hand
            	if(fromHand.equals("left")){ 
            		numFingers = p1.getLeftFingers(); 
            	}else{ 
            		numFingers = p1.getRightFingers(); 
            	} 

            	// Apply finger count to target hand (either the Ai's left or right hand)
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
            	System.out.println("....IT IS NOW YOUR TURN....");
            	System.out.println("----------------------------------------------------------------------");
            }
            

            /**
	         * Updates turn logic and checks win conditions
	         */ 
            public void endTurn(String fromPlayer){

            	// Print the winner to the screen if game is over
            	if(gameState.getPlayerHands().bothHandsOut() || gameState.getAiHands().bothHandsOut()){ 
            		winnerTxt = gameState.returnWinner();
            		System.out.println(winnerTxt);
            		repaint(); 
            		return; 
            	}
            	//If the game is not over, checks whether or not it is the Player's Turn
            	//If it is the player's turn, set to AI's turn and update the gamestate
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

         	/**
	         * Performs the Optimal AI move and updates game state
	         */
	        public void updateAiDecisionMaker(){ 
	        	if(!gameState.getIsAiPlayerTurn()) {
	        		return;
	        	}

	        	if(gameState.getPlayerHands().bothHandsOut() || gameState.getAiHands().bothHandsOut()){ 
            		winnerTxt = gameState.returnWinner();
            		System.out.println(winnerTxt);
            		repaint(); 
            		return; 
            	}

	        	OurGameState newState = aiDecision.findBestMove(gameState); 
	        	gameState = newState; 

	        	updateImages();
	        	repaint();

				if(gameState.getPlayerHands().bothHandsOut() || gameState.getAiHands().bothHandsOut()){ 
            		winnerTxt = gameState.returnWinner();
            		System.out.println(winnerTxt);
            		repaint(); 
            		return; 
            	}

	        	updateImages(); 
	        	repaint(); 
	        	gameState.setIsAiPlayerTurn(false); 
	        } 
            	

        /**
         * Updates player and AI hand images based on current game state
         */
        public void updateImages(){ 
        	try { 

        		// Load player hand and Ai hand images based on finger counts
        		BufferedImage pLeft = ImageIO.read(getClass().getResource("/sprites/left" + gameState.getPlayerHands().getLeftFingers() +".png"));
        		BufferedImage pRight = ImageIO.read(getClass().getResource("/sprites/right" + gameState.getPlayerHands().getRightFingers() +".png"));
        		BufferedImage aiLeft = ImageIO.read(getClass().getResource("/sprites/left" + gameState.getAiHands().getLeftFingers() +".png"));
        		BufferedImage aiRight = ImageIO.read(getClass().getResource("/sprites/right" + gameState.getAiHands().getRightFingers() +".png"));


        		// Get dimensions of the panel to determine relative hand positions

        		int panelWidth = getPreferredSize().width;
        		int panelHeight = getPreferredSize().height;

        		// Positioning for the Player hands and Ai hands relative to panel dimensions

    			int playerHandLeftX = panelWidth - (int)(panelWidth / 1.1);
    			int playerHandLeftY = panelHeight - (int)(panelHeight / 1.2);
    			int playerHandRightX = playerHandLeftX + 300;
    			int playerHandRightY = playerHandLeftY;

    			int aiHandLeftX = panelWidth - playerHandLeftX - 2 * (int)(pLeft.getWidth() * scale);
    			int aiHandLeftY = playerHandLeftY;
    			int aiHandRightX = aiHandLeftX + 300;
    			int aiHandRightY = playerHandLeftY;

    			// Create updated ChopsticksGame components for rendering
        		playerHandsUI = new ChopsticksGame(pLeft, pRight, gameState.getPlayerHands(), playerHandLeftX, playerHandLeftY, playerHandRightX , playerHandRightY); 
        		aiHandsUI = new ChopsticksGame(aiLeft, aiRight, gameState.getAiHands(), aiHandLeftX, aiHandLeftY, aiHandRightX, aiHandRightY); 

        	} 

        	catch (IOException e) { 
        		e.printStackTrace(); 
        	}

        }
        
        /**
         * Main rendering method for drawing the game.
         * Draws the player and AI hands, overlays the "VS", "You", and "AI" images,
         * and if a winner is detected, it displays the winner text near the bottom 
         * of the screen.
         */
		@Override 
		protected void paintComponent(Graphics g){ 

			super.paintComponent(g); 

			// Draw player and AI hand sprites
			if(playerHandsUI != null) {
				playerHandsUI.draw(g, this); 
			}	
			if(aiHandsUI != null) {
				aiHandsUI.draw(g, this);
			}

			// Draw "VS" image centered on the screen
			if(vs != null){ 
				int nw = vs.getWidth()/2;
				int nh = vs.getHeight()/2;

				int vsImgPosX = (int)(this.getPreferredSize().width / 2.25);
				int vsImgPosY = this.getPreferredSize().height / 3;

				g.drawImage(vs, vsImgPosX, vsImgPosY,nw,nh,this); 
			}

			// Draw "AI" tag in top right corner
			if(aiLabelImg != null){ 
				int nw = aiLabelImg.getWidth()/2;
				int nh = aiLabelImg.getHeight()/2;

				int aiImgPosX = (int)(this.getPreferredSize().width - this.getPreferredSize().width / 3.8);
				int aiImgPosY = this.getPreferredSize().height / 24;
				g.drawImage(aiLabelImg, aiImgPosX, aiImgPosY,nw,nh,this); 
			}

			// Draw "You" tag in top left corner
			if(youLabelImg != null){ 
				int nw = aiLabelImg.getWidth()/2;
				int nh = aiLabelImg.getHeight()/2;

				int youImgPosX = (int)(this.getPreferredSize().width / 4.61);
				int youImgPosY = this.getPreferredSize().height / 24;
				g.drawImage(youLabelImg, youImgPosX,youImgPosY,nw,nh,this); 
			}
			
			// Draw winner text if the game is over at the bottom center of the window
			if(winnerTxt != null){ 
				g.setColor(Color.BLACK); 
				g.setFont(new Font ("Serif", Font.BOLD, 100));
				FontMetrics metrics = g.getFontMetrics(g.getFont());
				int winnerTxtWidth = metrics.stringWidth(winnerTxt) / 2;
				int winTextPosX = this.getPreferredSize().width / 2 - winnerTxtWidth;
				int winTextPosY = (int)(this.getPreferredSize().height / 1.1);
				g.drawString(winnerTxt, winTextPosX,winTextPosY); 
			}

		} 

		/**
         * The fixed size of the game window.
         */
		@Override 
		public Dimension getPreferredSize(){ 
			return new Dimension(1920,1080); 
		}
	};
	    frame.add(c);
	    frame.pack();
	    frame.setVisible(true);
	    frame.setResizable(false);

	}

} 
