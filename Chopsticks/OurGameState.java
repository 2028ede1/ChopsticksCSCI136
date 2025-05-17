package Chopsticks; 

/**
 * The OurGameState class represents the current state of a Chopsticks game.
 * It has instance variables tracking the Hands objects of both the human player and the AI player, as well as whose turn it is.
 * It has getters for both the playerHands and the aiHands.
 */
public class OurGameState {

	private Hands playerHands;
	private Hands aiHands;
	private boolean isAiPlayerTurn;
	
	/**
	 * Constructor for game state object. Accepts two players, human player hands and ai player hands, as well as
	 * boolean of whether or not it's the ai player's turn.
	 */
	public OurGameState(Hands playerOne, Hands playerTwo, boolean isAiPlayerTurn) {
		this.playerHands = playerOne;
		this.aiHands = playerTwo;
		this.isAiPlayerTurn = isAiPlayerTurn;
	}
	
	/**
	 * Returns a deep copy of this objects game state.
	 * @return OurGameState object that has values copied from another OurGameState object
	 */	
	public OurGameState copyGameState() {
		Hands playerCopy = new Hands(this.playerHands.getLeftFingers(), this.playerHands.getRightFingers());
    	Hands aiCopy = new Hands(this.aiHands.getLeftFingers(), this.aiHands.getRightFingers());
    	boolean isAiPlayerTurnCopy = this.isAiPlayerTurn;
		OurGameState copiedGameState = new OurGameState(playerCopy, aiCopy, isAiPlayerTurnCopy);
		return copiedGameState;
	}

	/**
	 * Sets the isAiPlayerTurn value.
	 * @param bool boolean value to set the instance variable to.
	 */
	public void setIsAiPlayerTurn(boolean bool) {
		this.isAiPlayerTurn = bool;
	}

	/**
	 * Returns whose turn it is.
	 * @return boolean value of whether or not it is the AI player's turn.
	 */
	public boolean getIsAiPlayerTurn() {
		return this.isAiPlayerTurn;
	}

	/**
	 * Returns Hands object for the player
	 * @return Hands object representing the human players hands. 
	 */
	public Hands getPlayerHands() {
		return this.playerHands;
	}

	/**
	 * Returns Hands object for the AI
	 * @return Hands object representing the AI's hands. 
	 */
	public Hands getAiHands() {
		return this.aiHands;
	}

	/**
	 * Display the winner of the game if someone has one.
	 */
	public void printWinner() {
		if (this.playerHands.bothHandsOut()) {
			System.out.println("AI HAS WON!!!!");
		}

		else if (this.aiHands.bothHandsOut()) {
			System.out.println("PLAYER HAS WON!!!!");
		}
	}

	/**
	 * Display the text representation of the current game state by
	 * displaying both finger counts of the left and right player hands and the 
	 * ai hands respectively.
	 */
	public void displayGameState() {

		System.out.println("-----------------------------------------------------------");

		System.out.println("Player left hand: " + this.playerHands.getLeftFingers()  + " fingers.");
		System.out.println("Player right hand: " + this.playerHands.getRightFingers() + " fingers.");
		System.out.println("Computer left hand: " + this.aiHands.getLeftFingers()  + " fingers.");
		System.out.println("Computer right hand: " + this.aiHands.getRightFingers() + " fingers.");

		System.out.println("-----------------------------------------------------------");
	}

	public static void main(String[] args) {

		Hands player1 = new Hands(5,6); 
		Hands player2 = new Hands(0, -1);
		OurGameState test = new OurGameState(player1, player2, false);
		test.displayGameState(); 
		test.printWinner(); // should print "AI HAS WON!!!!" since player1 (the human) has exceeded 5 fingers on each hand

	}

}
