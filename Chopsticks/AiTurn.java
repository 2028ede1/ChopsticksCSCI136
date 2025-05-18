package Chopsticks; 

import java.util.ArrayList;
import java.util.Scanner;

public class AiTurn implements MinimaxTree<OurGameState> {


	/**
     * Stores the game state representing the 
     * result of the best move the AI can perform.
     */

	public OurGameState optimalMove;


	/**
     * Constructs a new AiTurn object with 
     * null optimal move to start.
     */
	public AiTurn() {
		this.optimalMove = null;
	}


	/**
     * Determines whether a game has ended 
     * (either the player or AI has both hands out).
     * 
     * @param currentState the current game state to check
     * @return true if the game is over, false otherwise
     */

	public boolean isTerminalState(OurGameState currentState) {
		return currentState.getPlayerHands().bothHandsOut() || currentState.getAiHands().bothHandsOut();
	}

	/**
     * Heuristic evaluation function to "score" a game state from the AI's perspective.
     * Value returned represents who has the advantage (positive indicates an advantage for 
     * the AI, negative values indicate a disadvantage for the computer)
     * 
     * @param currentState the state to evaluate
     * @return a numerical value indicating favorability for the AI
     */

	public int evaluateState(OurGameState currentState) {

		// High positive and negative scores to AI Win and AI Loss
		if (currentState.getPlayerHands().bothHandsOut()) {
			return 1000;
		}

		if (currentState.getAiHands().bothHandsOut()) {
			return -1000;
		}

		// Start scoring intermediate game states
		int score = 0;

		// "Reward" AI with points if the player has only 1 hand available

		if (currentState.getPlayerHands().isRightHandOut() || currentState.getPlayerHands().isLeftHandOut()) {
			score += 200;
		}

		// "Punish" AI if the Ai has only 1 hand avaiable
		if (currentState.getAiHands().isRightHandOut() || currentState.getAiHands().isLeftHandOut()) {
			score -= 200;
		}

		// "Reward" Ai with points if it can bump, penalize if the human simulated
		// player can bump
		if (currentState.getAiHands().canBump()) {
			score += 50;
		}

		if (currentState.getPlayerHands().canBump()) {
			score -= 50;
		}

		// "Penalize" AI for having 4 fingers on either hand 

		if (currentState.getAiHands().getLeftFingers() == 4) {
			score -= 20;
		}

		if (currentState.getAiHands().getRightFingers() == 4) {
			score -= 20;
		}

		// "Reward" AI for the opponent having 4 fingers on either hand

		if (currentState.getPlayerHands().getLeftFingers() == 4) {
			score += 20;
		}

		if (currentState.getPlayerHands().getRightFingers() == 4) {
			score += 20;
		}


		// "Reward" AI for having fewer than 3 fingers on either hand

		if (currentState.getAiHands().getLeftFingers() < 3){
			score += 30;
		}

		if (currentState.getAiHands().getRightFingers() < 3){
			score += 30;
		}

		// "Penalize" AI for the opponent having fewer than 3 fingers on either hand
		if (currentState.getPlayerHands().getLeftFingers() < 3) {
			score -= 30;
		}

		if (currentState.getPlayerHands().getRightFingers() < 3) {
			score -= 30;
		}

		return score;
	}

	/**
     * Generates all possible future game states from the 
     * current state based on legal moves.
     * 
     * @param currentState the current game state
     * @return a list of all valid next game states
     */
	public ArrayList<OurGameState> getAvailableMoves(OurGameState currentState) {

		// meant to store the copies of game states that represent the new game states after prefroming a possible move
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
	 * Recursively evaluates the long-term consequences of being in the given game state
	 * using the Minimax algorithm.
	 * 
	 * It simulates alternating turns between the AI (score maximizer) and the
	 * human player (score minimizer), assuming both play perfectly from this point onward.
	 * Minimax explores all legal future moves from the current state up to a fixed depth.
	 * 
	 * If it's the AI's turn, it chooses the move with the maximum evaluation score.
	 * If it's the player's turn, it chooses the move with the minimum evaluation score.
	 * 
	 * The value returned represents the best guaranteed outcome the AI can achieve from
	 * this state, assuming the opponent also plays optimally.
	 *
	 * @param currentState the game state to evaluate
	 * @param depth current depth 
	 * @param maxDepth maximum search depth allowed
	 * @param isMaxizingPlayer true if the current turn is the AI's (maximize score), false if it's the human player's (minimize score)
	 * @return the evaluation score of the best achievable outcome from this state
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
     * Finds and returns the best move for the AI to 
     * perform from the given state using the minimax
     * algorithm.
     * 
     * @param currentState the current game state
     * @return the game state after performing the optimal AI move
     */

	public OurGameState findBestMove(OurGameState currentState) {
		int bestVal = -1000;
		
		OurGameState optimalMove = null;

		for (OurGameState move : this.getAvailableMoves(currentState)) {
			int moveVal = minimax(move, 0, 15, false);
			if (moveVal > bestVal) {
				optimalMove = move;
			}
		}

		return optimalMove;

	}

	/**
	 * Prints string representation of the resulting game state after
	 * the optimal move has been preformed.
	 */
	public void printBestMove() {

		System.out.println("-----------------------------------------------------------");

		System.out.println("The optimal move for the ai would result in: ");
		System.out.println(this.optimalMove.getPlayerHands().getLeftFingers() + " fingers for the PLAYERS left hand.");
		System.out.println(this.optimalMove.getPlayerHands().getRightFingers() + " fingers for the PLAYERS right hand.");

		System.out.println("The computer now currently has: ");
		System.out.println(this.optimalMove.getAiHands().getLeftFingers() + " fingers for the AI PLAYERS left hand.");
		System.out.println(this.optimalMove.getAiHands().getRightFingers() + " fingers for the AI PLAYERS right hand.");

		System.out.println("-----------------------------------------------------------");
	}

	public void terminalGameTest() {
		Hands human = new Hands(1, 1);
		Hands ai = new Hands(1, 1);

		OurGameState startState = new OurGameState(human, ai, false);
		AiTurn decisionMaker = new AiTurn();

		boolean gameShouldEnd = false;
		boolean successfulMoveMade = false;

		while (!gameShouldEnd) {

			startState.displayGameState();

			// Early check for if game ends, if Ai has last made a winning move
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

				//  perform move to change game state depending on player input
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

				// keep prompting until the use has typed a valid input
				else {
					System.out.println("Invalid move made. Please try Again.");
				}

			}

			// it is now the Ai's turn, but before hand check if the player made a winning move

			if (startState.getPlayerHands().bothHandsOut() || startState.getAiHands().bothHandsOut()) {
				startState.displayGameState();
				startState.printWinner();
				gameShouldEnd = true;
				break;
			}

			else {
				decisionMaker.optimalMove = decisionMaker.findBestMove(startState);

				// computer differences between the current and present hand values from the optimal move state.
				// the values are used to preform the move (adding fingers to the left or right hand, or simply splitting)

				int different1 = decisionMaker.optimalMove.getPlayerHands().getLeftFingers() - startState.getPlayerHands().getLeftFingers();
				int different2 = decisionMaker.optimalMove.getPlayerHands().getRightFingers() - startState.getPlayerHands().getRightFingers();

				startState.getPlayerHands().addLeftHand(different1);
				startState.getPlayerHands().addRightHand(different2);

				// resets flag so player i sprompted for input again
				successfulMoveMade = false;
			}


		}
	}

	public static void main(String[] args) {
		AiTurn testAi = new AiTurn();
		// isTerminalState() TESTING

        // Neither player is out yet (fingers on both hands are less than 5)
        Hands playerHands1 = new Hands(2, 1);
        Hands aiHands1 = new Hands(2, 1);
        OurGameState gameState1 = new OurGameState(playerHands1, aiHands1, false);
        System.out.println("isTerminalState (no hands out) should be false: " + testAi.isTerminalState(gameState1));
        System.out.println("--------------------------------------------");

        // Human player is out (fingers on left and right hand are at 5)
        Hands playerHands2 = new Hands(5, 5);
        Hands aiHands2 = new Hands(2, 1);
        OurGameState gameState2 = new OurGameState(playerHands2, aiHands2, false);
        System.out.println("isTerminalState (human player out) should be true: " + testAi.isTerminalState(gameState2));
        System.out.println("--------------------------------------------");

        // AI player is out (fingers on left and right hand are at 5)
        Hands playerHands3 = new Hands(2, 1);
        Hands aiHands3 = new Hands(5, 5);
        OurGameState gameState3 = new OurGameState(playerHands3, aiHands3, false);
        System.out.println("isTerminalState (AI out) should be true: " + testAi.isTerminalState(gameState3));
        System.out.println("--------------------------------------------");

        // evaluateState() TESTING

        // both player hands out, so AI should win (maximum score of 1000 for the game state)
        Hands playerHands4 = new Hands(5, 5);
        Hands aiHands4 = new Hands(2, 1);
        OurGameState gameState4 = new OurGameState(playerHands4, aiHands4, false);
        System.out.println("evaluateState (player out) should be 1000: " + testAi.evaluateState(gameState4));
        System.out.println("--------------------------------------------");

        // both ai hands out, so AI should lose (minimum score of -1000 for the game state)
        Hands playerHands5 = new Hands(2, 5);
        Hands aiHands5 = new Hands(5, 5);
        OurGameState gameState5 = new OurGameState(playerHands5, aiHands5, false);
        System.out.println("evaluateState (AI out) should be -1000: " + testAi.evaluateState(gameState5));
        System.out.println("--------------------------------------------");

        // Evaluation score should be: 
	    // +20 for the AI for the player having 4 fingers on one hand
	    // +60 for the AI having less than 3 fingers on either hand
	    // -30 for the human player having less than 3 fingers on the right hand
        // should return +50 (overall, AI is winning more)

        Hands playerHands6 = new Hands(4, 1);
        Hands aiHands6 = new Hands(2, 2);
        OurGameState gameState6 = new OurGameState(playerHands6, aiHands6, false);
        System.out.println("evaluateState (player has 4-finger hand) should be +50: " + testAi.evaluateState(gameState6));
        System.out.println("--------------------------------------------");

        // Evaluation score should be: 
	    // -20 for the AI for the Ai having 4 fingers on one of its hands
	    // -60 for the AI for the player having less than 3 fingers on either hand
	    // +30 for the Ai player having less than 3 fingers on the left hand
        // should return +50 (overall, AI is winning more)

        Hands playerHands7 = new Hands(2, 2);
        Hands aiHands7 = new Hands(1, 4);
        OurGameState gameState7 = new OurGameState(playerHands7, aiHands7, false);
        System.out.println("evaluateState (AI has 4-finger hand) should be -50: " + testAi.evaluateState(gameState7));
        System.out.println("--------------------------------------------");

        // generatePossibleMoves() TESTING 

        // player's turn is being simulated. At the start of the game there are
        // 4 possible moves (left to left, left to right, right to right, right to left)

        Hands playerHands8 = new Hands(1, 1);
        Hands aiHands8 = new Hands(1, 1);
		OurGameState gameState8 = new OurGameState(playerHands8, aiHands8, false);
		ArrayList<OurGameState> movesPlayer = testAi.getAvailableMoves(gameState8);
		System.out.println("getAvailableMoves should return a list of 4 moves: " + movesPlayer.size());
		System.out.println("--------------------------------------------");

		// ai's turn when it has the ability to split should have 3 possible moves:
		// (right to left, right to right, split)
		Hands playerHands9 = new Hands(1, 1);
        Hands aiHands9 = new Hands(5, 2);
		OurGameState gameState9 = new OurGameState(playerHands9, aiHands9, true);
		ArrayList<OurGameState> movesPlayer2 = testAi.getAvailableMoves(gameState9);
		System.out.println("getAvailableMoves should return a list of 3 moves: " + movesPlayer2.size());
		System.out.println("--------------------------------------------");

        // minimax() TESTING

		// note that both this hand objects represent resulting states after player made a move

		// testing depth reached
		// +200 for player left hand being out
		// -50 for the player being able to bump
		// +20 for the player having 4 fingers on the right hand
		// +60 for the Ai having less than 3 fingers on either hand
		// should return 230

        Hands playerHands10 = new Hands(5, 4);
        Hands aiHands10 = new Hands(1, 1);
        OurGameState gameState10 = new OurGameState(playerHands10, aiHands10, true);
        int score10 = testAi.minimax(gameState10, 1, 1, true);
        System.out.println("minimax depth reach so current state evaluation should be +230 score: " + score10);
        System.out.println("--------------------------------------------");

        // testing 1 move win for the ai
        Hands playerHands11 = new Hands(5, 4);
        Hands aiHands11 = new Hands(1, 1);
        OurGameState gameState11 = new OurGameState(playerHands11, aiHands11, true);
        int score11 = testAi.minimax(gameState11, 0, 1, true);
        System.out.println("minimax (AI wins in 1 move) score should be 1000: " + score11);
        System.out.println("--------------------------------------------");

        // terminal game version testing
        testAi.terminalGameTest();

	}

} 




