package Chopsticks; 

import java.util.ArrayList;
import java.util.Scanner;

public class AiTurn implements MinimaxTree<OurGameState> {


	// Stores the game state representing the result of the best move to preform
	// from the current game state

	public OurGameState optimalMove;

	public AiTurn() {
		this.optimalMove = null;
	}


	/**
	 * Determines whether a game has ended (win, lose, or draw)
	 * For our game, the game ends when either the player or the computer reached
	 * 5 fingers for both hands.
	 */
	public boolean isTerminalState(OurGameState currentState) {
		return currentState.getPlayerHands().bothHandsOut() || currentState.getAiHands().bothHandsOut();
	}


	/**
	 * Evaluation function.
	 * Assigns a numeric value to a game state representing how favorable the state is for
	 * the computer. 
	 * 
	 * Positive values indicate an advantage for the computer, 
	 * with the maximum value indicating a complete win.
	 * 
	 * Negative values indicate a disadvantage for the computer 
	 * with the minimum value indicating a complete loss for.
	 * 
	 * 0 indicates a draw.
	 * 
	 * Intermediate values between the maximum/minimum and 0 represented intermediate
	 * advantage and disadvantage states.
	 */

	public int evaluateState(OurGameState currentState) {

		if (currentState.getPlayerHands().bothHandsOut()) {
			return 10;
		}

		if (currentState.getAiHands().bothHandsOut()) {
			return -10;
		}

		if (currentState.getAiHands().canBump()) {
			return 8;
		}

		if (currentState.getPlayerHands().canBump()) {
			return -8;
		}

		return 0;
	}

	/**
	  * Builds the child nodes (future game states) representing the resulting states
 	  * after all possible moves are made.
      *
      * Copies of the current game state are created and modified to 
      * reflect each legal move (based on game rules), then added to an ArrayList.
	 */
	public ArrayList<OurGameState> getAvailableMoves(OurGameState currentState) {
	    ArrayList<OurGameState> possibleFutureStates = new ArrayList<>();


	    if (currentState.getIsAiPlayerTurn()) {
	        // Check if AI can bump
	        if (currentState.getAiHands().canBump()) {

	        	OurGameState futureState = currentState.copyGameState();

	        	// must flip the IsAiPlayerTurn value
	        	futureState.setIsAiPlayerTurn(!futureState.getIsAiPlayerTurn());                    
	            futureState.getAiHands().bump();
	            possibleFutureStates.add(futureState);
	        }
	        
	        // AI left hand to player left hand
	        if (!currentState.getPlayerHands().isLeftHandOut() && !currentState.getAiHands().isLeftHandOut()) {
	        	OurGameState futureState = currentState.copyGameState();
	        	// must flip the IsAiPlayerTurn value

	        	futureState.setIsAiPlayerTurn(!futureState.getIsAiPlayerTurn());
	            futureState.getPlayerHands().addLeftHand(currentState.getAiHands().getLeftFingers());
	            possibleFutureStates.add(futureState);
	        }
	        
	        // AI left hand to player right hand
	        if (!currentState.getPlayerHands().isRightHandOut() && !currentState.getAiHands().isLeftHandOut()) {
	        	OurGameState futureState = currentState.copyGameState();
	        	futureState.setIsAiPlayerTurn(!futureState.getIsAiPlayerTurn());                        
	            futureState.getPlayerHands().addRightHand(currentState.getAiHands().getLeftFingers());
	            possibleFutureStates.add(futureState);
	        }
	        
	        // AI right hand to player left hand
	        if (!currentState.getPlayerHands().isLeftHandOut() && !currentState.getAiHands().isRightHandOut()) {
	        	OurGameState futureState = currentState.copyGameState();
	        	futureState.setIsAiPlayerTurn(!futureState.getIsAiPlayerTurn());                        
	            futureState.getPlayerHands().addLeftHand(currentState.getAiHands().getRightFingers());
	            possibleFutureStates.add(futureState);
	        }
	        
	        // AI right hand to player right hand
	        if (!currentState.getPlayerHands().isRightHandOut() && !currentState.getAiHands().isRightHandOut()) {
	        	OurGameState futureState = currentState.copyGameState();
	        	futureState.setIsAiPlayerTurn(!futureState.getIsAiPlayerTurn());                        
	            futureState.getPlayerHands().addRightHand(currentState.getAiHands().getRightFingers());
	            possibleFutureStates.add(futureState);
	        }
	    } 

	    else { 
	    	// Player's turn
	        // Check if player can bump
	       
	        if (currentState.getPlayerHands().canBump()) {
	        	OurGameState futureState = currentState.copyGameState();
	        	futureState.setIsAiPlayerTurn(!futureState.getIsAiPlayerTurn());                                      
	            futureState.getPlayerHands().bump();
	            possibleFutureStates.add(futureState);
	        }
	        
	        // Player left hand to AI left hand
	        if (!currentState.getAiHands().isLeftHandOut() && !currentState.getPlayerHands().isLeftHandOut()) {
	        	OurGameState futureState = currentState.copyGameState();  
	        	futureState.setIsAiPlayerTurn(!futureState.getIsAiPlayerTurn());                                           
	            futureState.getAiHands().addLeftHand(currentState.getPlayerHands().getLeftFingers());
	            possibleFutureStates.add(futureState);
	        }
	        
	        // Player left hand to AI right hand
	        if (!currentState.getAiHands().isRightHandOut() && !currentState.getPlayerHands().isLeftHandOut()) {
	        	OurGameState futureState = currentState.copyGameState();
	        	futureState.setIsAiPlayerTurn(!futureState.getIsAiPlayerTurn());                                                
	            futureState.getAiHands().addRightHand(currentState.getPlayerHands().getLeftFingers());
	            possibleFutureStates.add(futureState);
	        }
	        
	        // Player right hand to AI left hand
	        if (!currentState.getAiHands().isLeftHandOut() && !currentState.getPlayerHands().isRightHandOut()) {
	        	OurGameState futureState = currentState.copyGameState(); 
	        	futureState.setIsAiPlayerTurn(!futureState.getIsAiPlayerTurn());                                             
	            futureState.getAiHands().addLeftHand(currentState.getPlayerHands().getRightFingers());
	            possibleFutureStates.add(futureState);
	        }
	        
	        // Player right hand to AI right hand
	        if (!currentState.getAiHands().isRightHandOut() && !currentState.getPlayerHands().isRightHandOut()) {
	        	OurGameState futureState = currentState.copyGameState();
	        	futureState.setIsAiPlayerTurn(!futureState.getIsAiPlayerTurn());                                                
	            futureState.getAiHands().addRightHand(currentState.getPlayerHands().getRightFingers());
	            possibleFutureStates.add(futureState);
	        }
	    }
	    
	    return possibleFutureStates;
	}


	/**
	 * Evaluates the long-term consequences of being in the given game state, 
	 * assuming both the AI (score maximizer) and the simulated human player (score minimizer)
	 * play perfectly from this point forward.
	 * The value returned represents the guarenteed advantage/disadvantage/draw state outcome for the AI 
	 */


	public int minimax(OurGameState currentState, int depth, int maxDepth, boolean isMaxizingPlayer) {
		if (this.isTerminalState(currentState)) {
			return this.evaluateState(currentState);
		}


		if (depth == maxDepth) {
			return this.evaluateState(currentState);
		}
		
		if (isMaxizingPlayer) {

			int best = -1000;

			for (OurGameState possibleMove : this.getAvailableMoves(currentState)) {
				best = Math.max(best, minimax(possibleMove, depth + 1, maxDepth, !isMaxizingPlayer));
			}
			return best;
		}

		else {
			int best = 1000;

			for (OurGameState possibleMove : this.getAvailableMoves(currentState)) {
				best = Math.min(best, minimax(possibleMove, depth + 1, maxDepth, !isMaxizingPlayer));
			}
			return best;

		}
	}

	/**
	 * Finds the game state that results from the best possible move the AI can make.
 	 *
     * Iterates through all legal next moves from the current state, and applies the
     * minimax algorithm to evaluate the long-term outcome of each move, assuming
     * both the AI and the player play optimally.
     *
     * The move (i.e., resulting game state) with the highest advantage score
     * is returned as the optimal choice for the AI.
     */

	public OurGameState findBestMove(OurGameState currentState) {
		int bestVal = -1000;
		
		OurGameState optimalMove = null;

		for (OurGameState move : this.getAvailableMoves(currentState)) {
			int moveVal = minimax(currentState, 0, 15, false);
			if (moveVal > bestVal) {
				optimalMove = move;
			}
		}

		return optimalMove;
	}


	public void printBestMove() {

		System.out.println("-----------------------------------------------------------");

		System.out.println("The optimal move for the ai would result in: ");
		System.out.println(this.optimalMove.getPlayerHands().getLeftFingers() + " fingers for the PLAYERS left hand.");
		System.out.println(this.optimalMove.getPlayerHands().getRightFingers() + " fingers for the PLAYERS right hand.");

		System.out.println("The computer currently has: ");
		System.out.println(this.optimalMove.getAiHands().getLeftFingers() + " fingers for the AI PLAYERS left hand.");
		System.out.println(this.optimalMove.getAiHands().getRightFingers() + " fingers for the AI PLAYERS right hand.");

		System.out.println("-----------------------------------------------------------");
	}

	public static void main(String[] args) {

		Hands human = new Hands(1, 1);
		Hands ai = new Hands(1, 1);

		OurGameState startState = new OurGameState(human, ai, false);
		AiTurn decisionMaker = new AiTurn();


		boolean gameShouldEnd = false;
		boolean successfulMoveMade = false;

		while (!gameShouldEnd) {

			startState.displayGameState();

			// Early check for if game ends
			if (startState.getPlayerHands().bothHandsOut() || startState.getAiHands().bothHandsOut()) {
				startState.displayGameState();
				startState.printWinner();
				gameShouldEnd = true;
				break;
			}

			while (!successfulMoveMade) {
				Scanner scanner = new Scanner(System.in);
				System.out.print("Left to Right? Right to left? Left to Left? Right to Right? Or Split? ");

				String playerMove = scanner.nextLine();

				if (playerMove.equals("Left to Right") && !startState.getPlayerHands().isLeftHandOut() && !startState.getAiHands().isRightHandOut()) {

					int amountToAdd = startState.getPlayerHands().getLeftFingers();
					startState.getAiHands().addRightHand(amountToAdd);
					startState.setIsAiPlayerTurn(true);
					successfulMoveMade = true;
				}

				else if (playerMove.equals("Right to Left") && !startState.getPlayerHands().isRightHandOut() && !startState.getAiHands().isLeftHandOut()) {
					int amountToAdd = startState.getPlayerHands().getRightFingers();
					startState.getAiHands().addLeftHand(amountToAdd);
					startState.setIsAiPlayerTurn(true);
					successfulMoveMade = true;
				}

				else if (playerMove.equals("Left to Left") && !startState.getPlayerHands().isLeftHandOut() && !startState.getAiHands().isLeftHandOut()) {
					int amountToAdd = startState.getPlayerHands().getLeftFingers();
					startState.getAiHands().addLeftHand(amountToAdd);
					startState.setIsAiPlayerTurn(true);
					successfulMoveMade = true;
				}

				else if (playerMove.equals("Right to Right") && !startState.getPlayerHands().isRightHandOut() && !startState.getAiHands().isRightHandOut()) {
					int amountToAdd = startState.getPlayerHands().getRightFingers();
					startState.getAiHands().addRightHand(amountToAdd);
					startState.setIsAiPlayerTurn(true);
					successfulMoveMade = true;
				}

				else if (playerMove.equals("Split") && startState.getPlayerHands().canBump()) {
					startState.getPlayerHands().bump();
					startState.setIsAiPlayerTurn(true);
					successfulMoveMade = true;
				}

				else {
					System.out.println("Invalid move made. Please try Again.");
				}

			}

			if (startState.getPlayerHands().bothHandsOut() || startState.getAiHands().bothHandsOut()) {
				startState.displayGameState();
				startState.printWinner();
				gameShouldEnd = true;
				break;
			}

			else {
				decisionMaker.optimalMove = decisionMaker.findBestMove(startState);

				// figure out difference and add

				int different1 = decisionMaker.optimalMove.getPlayerHands().getLeftFingers() - startState.getPlayerHands().getLeftFingers();
				int different2 = decisionMaker.optimalMove.getPlayerHands().getRightFingers() - startState.getPlayerHands().getRightFingers();

				startState.getPlayerHands().addLeftHand(different1);
				startState.getPlayerHands().addRightHand(different2);
				successfulMoveMade = false;
			}


		}

	}

} 




