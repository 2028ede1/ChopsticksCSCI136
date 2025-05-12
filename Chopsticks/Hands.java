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

	public Hands(int left, int right) {
		this.leftFingers = left;
		this.rightFingers = right;
	}

	public int getLeftFingers() {
		return this.leftFingers;
	}

	public int getRightFingers() {
		return this.rightFingers;
	}

	public void addLeftHand(int amount) {
		if (leftFingers < 5) {
			this.leftFingers += amount;
		}

		if (leftFingers >= 5) {
			this.leftFingers = 5;
		}
	}

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
    System.out.println("Test");
    }
}
