package Chopsticks; 

/**
 * Hands class models a each players set of two hands in the game Chopsticks.
 * Each Hand is represented by an integer indicating the current number of fingers up from (1-5);
 * 
 * Instances of Hands are used in OurGameState to represent the current state of fingers up for 
 * both the human and AI players in the Chopsticks game.
 * 
 * Methods are provided to:
 * 
 * Track finger counts for the left and right hands
 * Increment fingers on either hand 
 * Check if a hand is "out" if it has 5 or more fingers
 * Split (bump) fingers between hands when allowed
 */

public class Hands {

	private int leftFingers;
	private int rightFingers;


	/**
	 * Constructor. Accepts two integer parameters to initialize the finger counts of both
	 * instance variables. Caps both instance variables at a minimum of 1 
	 * and a maximum of 5 to match game rules.
	 */
	public Hands(int left, int right) {
		this.leftFingers = left;
		this.rightFingers = right;

		if (this.rightFingers > 5) {
			this.rightFingers = 5;
		}

		else if (this.rightFingers < 1) {
			this.rightFingers = 1;
		}

		if (this.leftFingers > 5) {
			this.leftFingers = 5;
		}

		else if (this.leftFingers < 1) {
			this.leftFingers = 1;
		}

	}

	/**
	 * Returns the number of fingers on the left hand.
	 * @return integer finger count on the left hand.
	 */
	public int getLeftFingers() {
		return this.leftFingers;
	}

	/**
	 * Returns the number of fingers on the right hand.
	 * @return integer finger count on the right hand.
	 */
	public int getRightFingers() {
		return this.rightFingers;
	}


	/**
	 * Adds amount of fingers to the left hand, capping the finger count at 5 total fingers if needed
	 * to match game rules.
	 * @param amount integer number of fingers to add to the left hand.
	 */	
	public void addLeftHand(int amount) {
		this.leftFingers += amount;

		if (leftFingers >= 5) {
			this.leftFingers = 5;
		}
	}

	/**
	 * Adds amount of fingers to the left hand, capping the finger count at 5 total fingers if needed
	 * to match game rules.
	 * @param amount integer number of fingers to add to the left hand.
	 */	
	public void addRightHand(int amount) {
		this.rightFingers += amount;

		if (rightFingers >= 5) {
			this.rightFingers = 5;
		}
	}

	/**
	 * Returns true if the left hand has exceeded 5 fingers.
	 * @return boolean of whether or not the left hand has exceeded 5 fingers.
	 */	
	public boolean isLeftHandOut() {
		return this.leftFingers >= 5;
	}

	/**
	 * Returns true if the right hand has exceeded 5 fingers.
	 * @return boolean of whether or not the right hand has exceeded 5 fingers.
	 */	
	public boolean isRightHandOut() {
		return this.rightFingers >= 5;
	}

	/**
	 * Returns true if both hands are out of the game, or if both hands have exceeded 5 fingers.
	 * @return boolean of whether or not both hands have 5 or more fingers.
	 */	
	public boolean bothHandsOut() {
		return this.isLeftHandOut() && this.isRightHandOut();
	}

	/**
	 * Splits fingers evenly between a hand with an even count of fingers and a hand that is out.
	 */	
	public void bump() {
		if (this.bothHandsOut() != true) {
			if (this.isLeftHandOut() && this.getRightFingers() % 2 == 0) {
				this.leftFingers = this.rightFingers / 2;
				this.rightFingers = this.rightFingers / 2;
			}
			else if (this.isRightHandOut() && this.getLeftFingers() % 2 == 0) {
				this.rightFingers = this.leftFingers / 2;
				this.leftFingers = this.leftFingers / 2;
			}
		}

	}

	/**
	 * Checks if player can bump (split) fingers evenly. According to IRL game rules, player
	 * can only bump if one of its hands is out and the other hand is still active with an 
	 * even number of fingers.
	 * @return boolean of whether or not player can bump
	 */
	public boolean canBump() {
		if (this.bothHandsOut() != true) {
			if (this.isLeftHandOut() && this.getRightFingers() % 2 == 0) {
				return true;
			}
			else if (this.isRightHandOut() && this.getLeftFingers() % 2 == 0) {
				return true;
			}
		}
		return false;
 	}


 	public static void main(String[] args) { 
 		Hands playerTest1 = new Hands(3, 4);

 		System.out.println("playerTest1.canBump() should be false: " + playerTest1.canBump()); 
 		System.out.println("Player should have 3 left fingers: " + playerTest1.getLeftFingers() + " left fingers."); 
 		System.out.println("Player should have 4 right fingers: " + playerTest1.getRightFingers() + " right fingers."); 

 		playerTest1.addLeftHand(1);
 		System.out.println("Player should have 4 left fingers: " + playerTest1.getLeftFingers() + " left fingers."); 

 		playerTest1.addLeftHand(1);
 		System.out.println("Player should have 5 left fingers: " + playerTest1.getLeftFingers() + " left fingers."); 
 		System.out.println("playerTest1.canBump() should now be true: " + playerTest1.canBump()); 

 		playerTest1.bump();
 		System.out.println("Player should now have 2 left fingers: " + playerTest1.getLeftFingers() + " left fingers."); 
 		System.out.println("Player should now have 2 right fingers: " + playerTest1.getRightFingers() + " right fingers.");

 		System.out.println("-----------------------------------------------------------------------------------------------");
 		
 		Hands playerTest2 = new Hands(-4, 6);

 		System.out.println("playerTest2.canBump() should be false: " + playerTest2.canBump()); 
 		System.out.println("Player should have 1 left fingers: " + playerTest2.getLeftFingers() + " left fingers."); 
 		System.out.println("Player should have 5 right fingers: " + playerTest2.getRightFingers() + " right fingers."); 

 		playerTest2.addRightHand(1);
 		System.out.println("Player should have 5 right fingers: " + playerTest2.getRightFingers() + " right fingers."); 

 		playerTest2.addLeftHand(1);
 		System.out.println("Player should have 2 left fingers: " + playerTest2.getLeftFingers() + " left fingers."); 
 		System.out.println("playerTest2.canBump() should now be true: " + playerTest2.canBump()); 

 		playerTest2.bump();
 		System.out.println("Player should now have 1 left fingers: " + playerTest2.getLeftFingers() + " left fingers."); 
 		System.out.println("Player should now have 1 right fingers: " + playerTest2.getRightFingers() + " right fingers.");


    }
}