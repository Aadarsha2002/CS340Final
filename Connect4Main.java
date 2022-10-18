//a working connect-4 game using a minimal main class and a separate class for the game board and game logic and a separate class for the game AI
//the game board and game logic class is called Connect4Board
//the game AI class is called Connect4AI
//the main class is called Connect4Main

import java.util.Scanner;

public class Connect4Main {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Connect4Board board = new Connect4Board();
            Connect4AI ai = new Connect4AI();
            int player = 1;
            int column;
            boolean validMove;
            boolean gameOver = false;
            while (!gameOver) {
                board.printBoard();
                if (player == 1) {
                    System.out.println("Player 1, enter a column to drop your piece into (1-7): ");
                    column = input.nextInt();
                    validMove = board.dropPiece(column, player);
                    while (!validMove) {
                        System.out.println("Invalid move, try again: ");
                        column = input.nextInt();
                        validMove = board.dropPiece(column, player);
                    }
                    if (board.checkWin(player)) {
                        board.printBoard();
                        System.out.println("Player 1 wins!");
                        gameOver = true;
                    }
                    player = 2;
                } else {
                    column = ai.getMove(board);
                    validMove = board.dropPiece(column, player);
                    while (!validMove) {
                        column = ai.getMove(board);
                        validMove = board.dropPiece(column, player);
                    }
                    if (board.checkWin(player)) {
                        board.printBoard();
                        System.out.println("Player 2 wins!");
                        gameOver = true;
                    }
                    player = 1;
                }
                if (board.checkTie()) {
                    board.printBoard();
                    System.out.println("Tie game!");
                    gameOver = true;
                }
            }
        }
    }
}
