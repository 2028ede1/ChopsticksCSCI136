package Chopsticks; 

import java.util.ArrayList;

public interface MinimaxTree<Gamestate>{

   /**
    * A generic interface for implementing the Minimax algorithm
    * for two player turn based games.
    * 
    * @param Gamestate represents what gamestate of the game to be implemented.
    * should encapsulate all the info needed to determine:
    * 
    * - Whose turn it is?
    * - What possible moves are available?
    * - When is the game over?
    * - How to apply a move?
    * - Based on the game rules, who is at an advantage/disadvantage?
    */

   /**
    * Determines whether a given game state is a terminal state.
    * 
    * @param currentState current game state to check.
    * @return true is the game is over (win, lose, draw), false if not
    */

   public boolean isTerminalState(Gamestate currentState);

   /**
    * Assigns a heuristic score to a terminal/intermediate game state, 
    * symbolizing who has the advantage.
    * 
    * Positive values mean the maximizer (AI) is at an advantage.
    * Negative values mean the maximizer (AI) is at a disadvantage/the minimzer (simulated Human) is at an advantage
    * 
    * @param currentState current game state to evaluate
    * @return an integer score representing how favorable the state is for either player
    * 
    */
   public int evaluateState(Gamestate currentState);

   /**
    * Generates all legal next moves from the current game State.
    * The list generated will consist of Gamestate objects that have the reflected 
    * game state change after the move is preformed.
    * 
    * @param currentState the current game state.
    * @return ArrayList of Gamestate objects.
    */

   public ArrayList<Gamestate> getAvailableMoves(Gamestate currentState);

   /**
      * Uses the Minimax algorithm to determine the best achievable advantage state score 
      * from the current game state, assuming optimal play from both players.
      *
      * At each level/depth:
      * - The maximizing player selects the highest score from future states.
      * - The minimizing player selects the lowest score.
      * 
      * @param currentState the game state to evaluate
      * @param depth current depth in the search tree
      * @param maxDepth maximum depth to search in the tree
      * @param isMaximizingPlayer True if it's the maximizer's turn; false for the minimizer. Should swap
      * boolean values for each depth in the tree.
      * @return the gaurenteed highest advantage state score that can be assigned 
      * to the current state assuming both players play optimally.
   */
   
   public int minimax(Gamestate currentState, int depth, int maxDepth, boolean isMaximizingPlayer);

   /**
    * Finds the best move for the maximizer (AI) by calling minimax method on each of the possible moves.
    * 
    * @param currentState the current game state
    * @return a Gamestate object reflecting the optimal move that was preformed.
    */

   public Gamestate findBestMove(Gamestate currentState);

}


