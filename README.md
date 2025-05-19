# CSCI 136 - FINAL PROJECT - CHOPSTICKS w/ Minimax AI
By Emmanuel Ekpenyong and John Walden, in Prof. Katie Keith's CSCI 136 class, Spring 2025

# Overview

A Java implementation of the two-player hand game Chopsticks, where the goal is to eliminate all of your opponent's hands by causing them to reach a total of five fingers on each hand. The AI selects optimal moves using Minimax decision logic. With our Minimax implementation, the game is pretty much unbeatable. We implemented a custom Minimax tree data structure—combining elements of a k-ary tree—which we learned independently and built from scratch. The game is interactive, using drag-and-drop controls for player moves. All graphics were created using the Java Swing graphics library along with other standard Java AWT component libraries. 

# To Use

1. git clone
2. navigate into the ChopsticksCSCI136 directory and type
   * javac -d bin Chopsticks/*.java (to compile)
   * java -cp bin Chopsticks.Chopsticks (to run the game)
![Image](https://github.com/user-attachments/assets/79180656-2ed3-4d36-8f0a-085481f70067)

# To play

To play the game, click and drag one of your hands onto one of the AI opponent’s hands to perform a tap and add fingers. The number of fingers on your hand is added to the opponent’s hand. If a hand reaches five fingers, it is eliminated. You can also split fingers between your hands by dragging one of your hands onto your other hand. The AI will automatically make its move after yours. The game ends when one player has both hands eliminated.

# Video Demonstration
https://github.com/user-attachments/assets/228ad58f-8895-46b2-8a44-b3b1a0d62f95

# Screenshots
![Image](https://github.com/user-attachments/assets/0e84f9d2-eeed-4cdc-8691-8adb5e3da1c5)
![Image](https://github.com/user-attachments/assets/91922ec5-1997-4ef3-b571-395258e50543)
# ADT - MinimaxTree
This ADT is applicable to any turn-based adversarial game, where the player and the opponent have polar opposite goals (you want to win, the opponent wants you to lose). The Minimax algorithm, at a high level, is meant to simulate rational human thinking by examining all possible moves, predicting the consequences of performing each possible move if both the human and the AI play as optimally as possible several turns ahead, and choosing the option that leads to the most favorable outcome for the AI in the worst-case scenario, where the simulated human player plays optimally to make the AI lose.

The methods included in the ADT are: isTerminalState(Gamestate currentState), evaluateState(Gamestate currentState), getAvailableMoves(Gamestate currentState), minimax(Gamestate currentState, int depth, int maxDepth, boolean isMaximizingPlayer), and findBestMove(Gamestate currentState). The Gamestate object represents a game state that you define based on the specific game being implemented. It should encapsulate all the information necessary to determine whose turn it is, what moves are available, whether the game is over, how to apply a move, and, according to the game’s rules, which player has an advantage or disadvantage in the current state.

Essentially, after the human makes a move, the AI generates all legal moves (with getAvailableMoves) and simulates several alternating turns ahead (with minimax)—its own, the human’s, and so on—recursively building a game tree of possible outcomes. Each path of turn choices is explored until the game ends (using isTerminalState) or a depth limit is reached. At the leaf nodes, the AI assigns a heuristic score based on how favorable each state is (with evaluateState), with positive values favoring the AI and negative ones favoring the opponent. These scores are then tracked recursively back up the tree to the root node, allowing the AI to determine the guaranteed consequence of each move if both players play optimally (with minimax). The AI then selects the move that leads to the highest score, ensuring the guaranteed best possible outcome (with findBestMove).

# Implementation details

## Hands.java

Hands objects are designed to represent a pair of hands, with instance variables to track the number of fingers on both the left and right hands. It includes methods to add fingers to either hand, as well as to bump or split fingers from one hand to the other when allowed.

## OurGameState.java

OurGameState represents the Gamestate object used by the Minimax algorithm. It includes methods to return a copy of the current game state when needed, track and update whose turn it is, retrieve the Hands objects for both the player and the AI, and print out the winner when the game ends.

## AiDecisionMaker.java

Contains the implementation of the MinimaxTree interface methods. The main instance variable is meant to hold an OurGameState object represent the optimalMove, or more specifically, the resulting game state after the most optimal move is performed. 

## ChopsticksGame.java

Used to run the game. Contains the implementation of UI elements using Swing and other Java graphics libraries, as well as the drag-and-drop interaction logic for performing moves.

# Resources

geeksforgeeks minimax explanations:

[geeksforgeeks - Minimax Algorithm in Game Theory](https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-1-introduction/)

[geeksforgeeks - Introduction to evaluation function of Minimax Algorithm](https://www.geeksforgeeks.org/introduction-to-evaluation-function-of-minimax-algorithm-in-game-theory/)

[geeksforgeeks - Finding optimal move in TicTakToe using Minimax](https://www.geeksforgeeks.org/finding-optimal-move-in-tic-tac-toe-using-minimax-algorithm-in-game-theory/)

our presentation:

[CSCI 136 - Final Project - Game AI](https://docs.google.com/presentation/d/1mDI7ggNKuLaTZpff-jB7mtZdmNo3DhDdX8ASW0Ksv8I/edit?usp=sharing)

videos:

[How computers play games](https://www.youtube.com/watch?v=SLgZhpDsrfc)

# Credits

our friend Moira helped draw us the hand images for the project 🫶🫶
