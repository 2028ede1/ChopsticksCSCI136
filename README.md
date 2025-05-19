# CSCI 136 - FINAL PROJECT - CHOPSTICKS w/ Minimax AI
By Emmanuel Ekpenyong and John Walden, in Prof. Katie Keith's CSCI 136 class, Spring 2025

# Overview

A Java implementation of the two-player hand game Chopsticks, where the goal is to eliminate all of your opponent's hands by causing them to reach a total of five fingers on each hand. The AI selects optimal moves using Minimax decision logic. With our Minimax implementation, the game is pretty much unbeatable. We implemented a custom Minimax tree data structure—combining elements of a k-ary tree—which we learned independently and built from scratch. The game is interactive, using drag-and-drop controls for player moves. All graphics were created using the Java Swing graphics library along with other standard Java AWT component libraries. 

# To Use

1. git clone (ssh link)
2. navigate into the ChopsticksCSCI136 directory and type
   * javac -d bin Chopsticks/*.java (to compile)
   * java -cp bin Chopsticks.Chopsticks (to run the game)
![Image](https://github.com/user-attachments/assets/79180656-2ed3-4d36-8f0a-085481f70067)

# To play

To play the game, click and drag one of your hands onto one of the AI opponent’s hands to perform a tap and add fingers. The number of fingers on your hand is added to the opponent’s hand. If a hand reaches five fingers, it is eliminated. You can also split fingers between your hands by dragging one of your hands onto your other hand. The AI will automatically make its move after yours. The game ends when one player has both hands eliminated.

# Video Demonstration
https://github.com/user-attachments/assets/7b0e43e6-ab76-40d9-a6b5-86941c843148

# Screenshots
![Image](https://github.com/user-attachments/assets/0e84f9d2-eeed-4cdc-8691-8adb5e3da1c5)
![Image](https://github.com/user-attachments/assets/91922ec5-1997-4ef3-b571-395258e50543)
# ADT - MinimaxTree

# Implementation details

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
