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


	// Hands objects should have at minimum 1 finger per hand, and at most 5 fingers per hand
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

	public int getLeftFingers() {
		return this.leftFingers;
	}

	public int getRightFingers() {
		return this.rightFingers;
	}

	// In a real game, maximum amount of fingers should never be greater than 5 for either player
	public void addLeftHand(int amount) {
		if (leftFingers < 5) {
			this.leftFingers += amount;
		}

		if (leftFingers >= 5) {
			this.leftFingers = 5;
		}
	}

	// In a real game, maximum amount of fingers should never be greater than 5 for either player
	public void addRightHand(int amount) {
		if (rightFingers < 5) {
			this.rightFingers += amount;
		}

		if (rightFingers >= 5) {
			this.rightFingers = 5;
		}
	}

	public boolean isLeftHandOut() {
		return this.leftFingers == 5;
	}

	public boolean isRightHandOut() {
		return this.rightFingers == 5;
	}

	public boolean bothHandsOut() {
		return this.isLeftHandOut() && this.isRightHandOut();
	}

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

	// According to IRL game rules, player can bump only if one of it's hands is 
	// out and the other hand still active has an even number of fingers.

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
 		Hands playerTest = new Hands(3, 4);

 		System.out.println(playerTest.canBump()); // should be false
 		System.out.println("Player has " + playerTest.getLeftFingers() + " left fingers."); // Should be 5
 		System.out.println("Player has " + playerTest.getRightFingers() + " right fingers."); // Should be 5

 		playerTest.addLeftHand(1);
 		System.out.println("Player has " + playerTest.getLeftFingers() + " left fingers."); // Should be 4

 		playerTest.addLeftHand(1);
 		System.out.println("Player has " + playerTest.getLeftFingers() + " left fingers."); // Should be 5

 		System.out.println(playerTest.canBump()); // should be true

 		playerTest.bump();
 		System.out.println("Player has " + playerTest.getLeftFingers() + " left fingers."); // Should be 2
 		System.out.println("Player has " + playerTest.getRightFingers() + " right fingers."); // Should be 2

    }
}