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
        Connect4AI ai = new Connect4AI();
        System.out.println("Welcome to Connect 4!");
        System.out.println("Do you want to play against a human or AI?");
        System.out.println("Enter 1 for human or 2 for AI");
        int opponent = input.nextInt();
        while (opponent != 1 && opponent != 2) {
            System.out.println("Invalid input. Enter 1 for human or 2 for AI");
            opponent = input.nextInt();
        }
        System.out.println("Enter 1 to go first or 2 to go second");
        int player = input.nextInt();
        while (player != 1 && player != 2) {
            System.out.println("Invalid input. Enter 1 to go first or 2 to go second");
            player = input.nextInt();
        }
        if (player == 2) {
            board.dropPiece(ai.getMove(board), 2);
            board.printBoard();
        }
        while (true) {
            System.out.println("Enter a column to drop your piece");
            int column = input.nextInt();
            while (!board.dropPiece(column, 1)) {
                System.out.println("Invalid input. Enter a column to drop your piece");
                column = input.nextInt();
            }
            board.printBoard();
            if (board.checkWin(1)) {
                System.out.println("You win!");
                break;
            } else if (board.checkTie()) {
                System.out.println("Tie!");
                break;
            }
            if (opponent == 1) {
                System.out.println("Enter a column to drop your piece");
                column = input.nextInt();
                while (!board.dropPiece(column, 2)) {
                    System.out.println("Invalid input. Enter a column to drop your piece");
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
            } else {
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
