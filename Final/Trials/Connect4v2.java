package Trials;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Connect4v2 {

  static Scanner scanner = new Scanner(System.in);

  // The game board is represented as a 2-dimensional array
  // with 6 rows and 7 columns
  private static int dth = 3;

  private static final int ROWS = 6;
  private static final int COLS = 7;
  private static char[][] board = new char[ROWS][COLS];

  public static void main(String[] args) {
    // Initialize the game board to be empty
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        board[i][j] = ' ';
      }
    }

    // Start the game by printing the initial board state
    printBoard();

    System.out.println("Do you want to play against another human (Y/N)?");
    String response = scanner.nextLine();
    boolean againstHuman = response.equalsIgnoreCase("y");

    // If the user doesn't want to play against another human, ask whether they want
    // to go first
    if (!againstHuman) {
      System.out.println("Do you want to go first (Y/N)?");
      response = scanner.nextLine();
      boolean humanGoesFirst = response.equalsIgnoreCase("y");

      // If the human doesn't want to go first, let the AI go first
      if (!humanGoesFirst) {

        aiTurn();
      }
    }

    // The game is played by alternating turns between the human
    // player and the AI opponent
    char currentPlayer = againstHuman ? '1' : 'H';
    while (true) {
      if (currentPlayer == '1') {
        // Human player 1's turn
        humanTurn(1);

        // Check if the game is over
        if (isGameOver()) {
          break;
        }

        // Switch to the other player's turn
        currentPlayer = '2';
      } else if (currentPlayer == '2') {
        // Human player 2's turn
        humanTurn(2);

        // Check if the game is over
        if (isGameOver()) {
          break;
        }

        // Switch to the other player's turn
        currentPlayer = '1';
      } else if (currentPlayer == 'H') {
        // Human player's turn
        humanTurn();

        // Check if the game is over
        if (isGameOver()) {
          break;
        }

        // If the user is playing against another human, skip the AI opponent's turn
        if (againstHuman) {
          continue;
        }

        // AI opponent's turn
        aiTurn();

        // Check if the game is over
        if (isGameOver()) {
          break;
        }
      }
    }
  }

  // This method is called during the human player's turn
  private static void humanTurn() {
    // Prompt the player to enter a column where they want to place their piece
    System.out.println("Enter a column (1-7): ");
    int col = scanner.nextInt();

    // Place the piece in the selected column
    placePiece(col, 'H');

    // Print the updated board
    printBoard();
  }

  // This method is called during the human player's turn when playing against
  // another human
  private static void humanTurn(int player) {
    // Prompt the player to enter a column where they want to place their piece
    System.out.println("Player " + player + ", enter a column (1-7): ");
    int col = scanner.nextInt();

    // Place the piece in the selected column
    placePiece(col, (char) (player + '0'));

    // Print the updated board
    printBoard();
  }

  // This method is called during the AI opponent's turn
  private static void aiTurn() {
    // Use the minimax algorithm to find the best move for the AI opponent
    int col = minimax(dth, 'A', Integer.MIN_VALUE, Integer.MAX_VALUE)[0];

    // Place the piece in the selected column
    placePiece(col, 'A');

    // Print the updated board
    printBoard();
  }

  // This method implements the minimax algorithm to find the best move for the AI
  // opponent
  private static int[] minimax(int depth, char player, int alpha, int beta) {

    // Generate a list of all possible moves
    List<Integer> moves = getMoves();

    if (depth == 0 || hasWon(player == 'A' ? 'H' : 'A') || hasWon(player == 'A' ? 'A' : 'H') || moves.isEmpty()) {
      return new int[] { -1, evaluate() };
    }

    // The AI player is maximizing, so initialize the best score to a low value
    int bestScore = Integer.MAX_VALUE; // change this
    int bestCol = -1;

    // The human player is minimizing, so initialize the best score to a high value
    if (player == 'H') {
      bestScore = Integer.MAX_VALUE;
    }

    int[] scores = new int[7];

    // Loop through all possible moves
    for (int col : moves) {
      // Place the piece in the selected column
      placePiece(col, player);

      // Calculate the score for this move
      int score = minimax(depth - 1, player == 'A' ? 'H' : 'A', alpha, beta)[1];

      // print the score and the Move
      // System.out.println("Score: " + score + " Move: " + col);

      scores[col - 1] = score;
      // Remove the piece from the column
      removePiece(col);

      // Update the best score and column if necessary
      if (score < bestScore) { // change this for when maximizing or minimizing
        bestScore = score;
        bestCol = col;
      }

      // Alpha-beta pruning
      if (bestScore <= alpha) {
        return new int[] { bestCol, bestScore };
      }

      if (bestScore < beta) {
        beta = bestScore;
      }
    }

    return new int[] { bestCol, bestScore };
  }

  private static void printBoard() {
    // Print the column numbers
    System.out.print(" _");
    for (int i = 0; i < COLS; i++) {
      System.out.print("|" + (i + 1));
    }
    System.out.println("|");

    // Print the game board
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        if (j == 0) {
          // Print the row number
          System.out.print("|" + (i + 1));
        }
        System.out.print("|" + board[i][j]);
      }
      System.out.println("|");
    }
    System.out.println("---------------");
  }

  // This method returns true if the game is over, false otherwise
  private static boolean isGameOver() {
    // Check if player 1 has won
    if (hasWon('1')) {
      System.out.println("Player 1 has won!");
      return true;
    }

    // Check if player 2 has won
    if (hasWon('2')) {
      System.out.println("Player 2 has won!");
      return true;
    }

    // Check if the human player has won
    if (hasWon('H')) {
      System.out.println("Human player has won!");
      return true;
    }

    // Check if the AI opponent has won
    if (hasWon('A')) {
      System.out.println("AI opponent has won!");
      return true;
    }

    // Check if the board is full (i.e. a tie)
    if (isFull()) {
      System.out.println("The game is a tie!");
      return true;
    }

    // If none of the above conditions are true, the game is not over
    return false;
  }

  // This method places a piece on the game board
  private static void placePiece(int col, char player) {
    // Check if the selected column is valid
    if (col < 1 || col > COLS) {
      System.out.println("Invalid column!");
      return;
    }
    // Check if the selected column is full
    if (board[0][col - 1] != ' ') {
      System.out.println("Column is full!");
      return;
    }

    // Loop through the rows from bottom to top
    for (int i = ROWS - 1; i >= 0; i--) {
      // If the current cell is empty, place the piece in this cell
      if (board[i][col - 1] == ' ') {
        board[i][col - 1] = player;
        return;
      }
    }
  }

  // This method generates a list of all possible moves that the AI player can
  // make
  private static List<Integer> getMoves() {
    List<Integer> moves = new ArrayList<>();

    // Loop through each column
    for (int i = 0; i < COLS; i++) {
      // Check if the selected column is not full
      if (board[0][i] == ' ') {
        moves.add(i + 1);
      }
    }

    return moves;
  }

  // This method removes a piece from the game board
  private static void removePiece(int col) {
    // Loop through the rows from top to bottom
    for (int i = 0; i < ROWS; i++) {
      // If the current cell is not empty, remove the piece from this cell
      if (board[i][col - 1] != ' ') {
        board[i][col - 1] = ' ';
        return;
      }
    }
  }

  // This method calculates the score for the current state of the game board
  private static int evaluate() {
    int score = 0;

    if (hasWon('A')) {
      return -1000;
    } else if (hasWon('H')) {
      return 1000;
    }

    // Loop through each cell on the game board
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        // If the cell is empty, skip it
        if (board[i][j] == ' ') {
          continue;
        }

        // Calculate the score for the current cell based on the number
        // of consecutive pieces in the horizontal, vertical, and diagonal
        // directions
        int h = horizontal(i, j);
        int v = vertical(i, j);
        int d1 = diagonal1(i, j);
        int d2 = diagonal2(i, j);

        // Add the maximum of these scores to the total score
        score += Math.max(h, Math.max(v, Math.max(d1, d2)));
      }
    }

    return score;
  }

  // This method returns true if the specified player has won the game, false
  // otherwise
  private static boolean hasWon(char player) {
    // Loop through each cell on the game board
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        // If the current cell does not belong to the specified player, skip it
        if (board[i][j] != player) {
          continue;
        }

        // Check if the specified player has four consecutive pieces in the
        // horizontal, vertical, or diagonal direction starting from the current cell
        if (horizontal(i, j) >= 4 || vertical(i, j) >= 4 || diagonal1(i, j) >= 4 || diagonal2(i, j) >= 4) {
          return true;
        }
      }
    }

    return false;
  }

  // This method returns true if the game board is full, false otherwise
  private static boolean isFull() {
    // Loop through each cell on the game board
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        // If the current cell is empty, the board is not full
        if (board[i][j] == ' ') {
          return false;
        }
      }
    }

    // If all cells have been checked and none of them is empty, the board is full
    return true;
  }

  // This method returns the number of consecutive pieces in the horizontal
  // direction
  // starting from the specified cell
  private static int horizontal(int row, int col) {
    // Initialize the counter to 1 (for the current cell)
    int count = 1;

    // Check the cells to the left of the current cell
    for (int i = col - 1; i >= 0; i--) {
      // If the cell is empty or belongs to the other player, stop counting
      if (board[row][i] == ' ' || board[row][i] != board[row][col]) {
        break;
      }

      // Otherwise, increment the counter
      count++;
    }

    // Check the cells to the right of the current cell
    for (int i = col + 1; i < COLS; i++) {
      // If the cell is empty or belongs to the other player, stop counting
      if (board[row][i] == ' ' || board[row][i] != board[row][col]) {
        break;
      }

      // Otherwise, increment the counter
      count++;
    }

    return count;
  }

  // This method returns the number of consecutive pieces in the vertical
  // direction
  // starting from the specified cell
  private static int vertical(int row, int col) {
    // Initialize the counter to 1 (for the current cell)
    int count = 1;

    // Check the cells above the current cell
    for (int i = row - 1; i >= 0; i--) {
      // If the cell is empty or belongs to the other player, stop counting
      if (board[i][col] == ' ' || board[i][col] != board[row][col]) {
        break;
      }

      // Otherwise, increment the counter
      count++;
    }

    // Check the cells below the current cell
    for (int i = row + 1; i < ROWS; i++) {
      // If the cell is empty or belongs to the other player, stop counting
      if (board[i][col] == ' ' || board[i][col] != board[row][col]) {
        break;
      }

      // Otherwise, increment the counter
      count++;
    }

    return count;
  }

  // This method returns the number of consecutive pieces in the diagonal
  // direction
  // (top-left to bottom-right) starting from the specified cell
  private static int diagonal1(int row, int col) {
    // Initialize the counter to 1 (for the current cell)
    int count = 1;

    // Check the cells in the top-left direction
    for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
      // If the cell is empty or belongs to the other player, stop counting
      if (board[i][j] == ' ' || board[i][j] != board[row][col]) {
        break;
      }

      // Otherwise, increment the counter
      count++;
    }

    // Check the cells in the bottom-right direction
    for (int i = row + 1, j = col + 1; i < ROWS && j < COLS; i++, j++) {
      // If the cell is empty or belongs to the other player, stop counting
      if (board[i][j] == ' ' || board[i][j] != board[row][col]) {
        break;
      }

      // Otherwise, increment the counter
      count++;
    }

    return count;
  }

  // This method returns the number of consecutive pieces in the diagonal
  // direction
  // (top-right to bottom-left) starting from the specified cell
  private static int diagonal2(int row, int col) {
    // Initialize the counter to 1 (for the current cell)
    int count = 1;

    // Check the cells in the top-right direction
    for (int i = row - 1, j = col + 1; i >= 0 && j < COLS; i--, j++) {
      // If the cell is empty or belongs to the other player, stop counting
      if (board[i][j] == ' ' || board[i][j] != board[row][col]) {
        break;
      }

      // Otherwise, increment the counter
      count++;
    }

    // Check the cells in the bottom-left direction
    for (int i = row + 1, j = col - 1; i < ROWS && j >= 0; i++, j--) {
      // If the cell is empty or belongs to the other player, stop counting
      if (board[i][j] == ' ' || board[i][j] != board[row][col]) {
        break;
      }

      // Otherwise, increment the counter
      count++;
    }

    return count;
  }
}
