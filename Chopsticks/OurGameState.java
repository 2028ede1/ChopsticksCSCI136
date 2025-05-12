package Chopsticks; 

/**
 * The OurGameState class represents the current state of a Chopsticks game.
 * It has instance variables tracking the Hands objects of both the human player and the AI player, as well as whose turn it is.
 * 
 * 
 * 
 */
public class OurGameState {
	private Hands playerHands;
	private Hands aiHands;

	private boolean isAiPlayerTurn;
	
	public OurGameState(Hands playerOne, Hands playerTwo, boolean isAiPlayerTurn) {
		this.playerHands = playerOne;
		this.aiHands = playerTwo;
		this.isAiPlayerTurn = isAiPlayerTurn;
	}
	
	public OurGameState copyGameState2() {
		Hands playerCopy = new Hands(this.playerHands.getLeftFingers(), this.playerHands.getRightFingers());
    	Hands aiCopy = new Hands(this.aiHands.getLeftFingers(), this.aiHands.getRightFingers());
    	boolean isAiPlayerTurnCopy = this.isAiPlayerTurn;
		OurGameState copiedGameState = new OurGameState(playerCopy, aiCopy, isAiPlayerTurnCopy);
		return copiedGameState;
	}

	public void setIsAiPlayerTurn(boolean bool) {
		this.isAiPlayerTurn = bool;
	}


	public boolean getIsAiPlayerTurn() {
		return this.isAiPlayerTurn;
	}


	public Hands getPlayerHands() {
		return this.playerHands;
	}

	public Hands getAiHands() {
		return this.aiHands;
	}

	public void printWinner() {
		if (this.playerHands.bothHandsOut()) {
			System.out.println("AI HAS WON!!!!");
		}

		else if (this.aiHands.bothHandsOut()) {
			System.out.println("PLAYER HAS WON!!!!");
		}
	}

	public void displayGameState() {

		System.out.println("-----------------------------------------------------------");

		System.out.println("Player left hand: " + this.playerHands.getLeftFingers()  + " fingers.");
		System.out.println("Player right hand: " + this.playerHands.getRightFingers() + " fingers.");
		System.out.println("Computer left hand: " + this.aiHands.getLeftFingers()  + " fingers.");
		System.out.println("Computer right hand: " + this.aiHands.getRightFingers() + " fingers.");

		System.out.println("-----------------------------------------------------------");
	}

	public static void main(String[] args) {
		System.out.println("Test");
	}

}
