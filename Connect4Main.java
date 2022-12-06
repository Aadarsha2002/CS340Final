//a working connect-4 game using a minimal main class and a separate class for the game board and game logic and a separate class for the game AI
//the game board and game logic class is called Connect4Board
//the game AI class is called Connect4AI
//the main class is called Connect4Main
//The user enters whether they want to play against a human or AI

import java.util.Scanner;

public class Connect4Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Connect4Board board = new Connect4Board();
        Connect4AI ai = new Connect4AI(0);
        // intro
        System.out.println("Welcome to Connect 4!");
        // ask if user wants to play against human or ai
        System.out.println("Do you want to play against a human or AI?");
        System.out.print("Enter 1 for human or 2 for AI: ");
        // intake input
        int opponent = input.nextInt();
        // make sure input was valid
        while (opponent != 1 && opponent != 2) {
            System.out.print("Invalid input. Enter 1 for human or 2 for AI: ");
            opponent = input.nextInt();
        }

        // if user wants to play against AI, ask what type of AI
        if (opponent == 2) {
            System.out.print("Enter 1 for minimax AI or 2 for alpha-beta pruning AI: ");
            int aiType = input.nextInt();
            // make sure valid input
            while (aiType != 1 && aiType != 2) {
                System.out.print("Invalid input. Enter 1 for minimax AI or 2 for alpha-beta pruning AI: ");
                aiType = input.nextInt();
            }
            ai = new Connect4AI(aiType);
        }

        // ask user if they want to go first or second
        System.out.print("Enter 1 to go first or 2 to go second: ");
        int player = input.nextInt();
        // make sure valid input
        while (player != 1 && player != 2) {
            System.out.print("Invalid input. Enter 1 to go first or 2 to go second: ");
            player = input.nextInt();
        }

        // if user chose to go second make a move with the AI
        if (player == 2) {
            // make move
            int aiMove = ai.getMove(board);
            // drop piece
            board.dropPiece(aiMove, 1);
            // print board
            board.printBoard();
        }

        while (true) {
            // ask user for their move
            System.out.print("Enter a column to drop your piece (1-7): ");
            int column = input.nextInt();
            // valid input
            while (!board.dropPiece(column, 1)) {
                System.out.print("Invalid input. Enter a column to drop your piece (1-7): ");
                column = input.nextInt();
            }
            // print the users move
            board.printBoard();
            // check to see if that move made a win or tie
            if (board.checkWin(1)) {
                System.out.println("You win!");
                break;
            } else if (board.checkTie()) {
                System.out.println("Tie!");
                break;
            }
            // this occurs if their is a second player and no AI
            if (opponent == 1) {
                System.out.print("Enter a column to drop your piece (1-7): ");
                column = input.nextInt();
                while (!board.dropPiece(column, 2)) {
                    System.out.print("Invalid input. Enter a column to drop your piece (1-7): ");
                    column = input.nextInt();
                }
                board.printBoard();
                if (board.checkWin(2)) {
                    System.out.println("You lose!");
                    break;
                } else if (board.checkTie()) {
                    System.out.println("Tie!");
                    break;
                }
            }
            // this is what happens when it's the AI's turn
            else {
                board.dropPiece(ai.getMove(board), 2);
                board.printBoard();
                if (board.checkWin(2)) {
                    System.out.println("You lose!");
                    break;
                } else if (board.checkTie()) {
                    System.out.println("Tie!");
                    break;
                }
            }
        }
        input.close();
    }
}