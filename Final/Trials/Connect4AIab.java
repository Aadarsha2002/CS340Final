package Trials;
// Path: Connect4AIab.java

public class Connect4AIab {
    // the depth of the search tree
    int depth = 5;

    // the board that the AI will be playing on
    Connect4Board board;

    // the constructor
    public Connect4AIab(Connect4Board board) {
        this.board = board;
    }

    public Connect4AIab() {
        this.board = new Connect4Board();
    }

    // method that returns the best move
    public int getMove(Connect4Board board) {
        // the best move
        int bestMove = 0;
        // the best score
        int bestScore = Integer.MIN_VALUE;
        // the score of the current move
        int score;
        // the current board
        Connect4Board currentBoard = board;
        // loop through all the columns
        for (int i = 0; i < 7; i++) {
            // if the column is not full
            if (currentBoard.dropPiece(i + 1, 2)) {
                // get the score of the move
                score = minimax(currentBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                // if the score is better than the best score
                if (score > bestScore) {
                    // set the best score to the score
                    bestScore = score;
                    // set the best move to the current move
                    bestMove = i;
                }
                // reset the board
                currentBoard = board;
            }
        }
        // return the best move
        return bestMove + 1;
    }

    // minimax algorithm with alpha-beta pruning
    public int minimax(Connect4Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        // if the depth is 0 or the game is over
        if (depth == 0 || board.checkWin(1) || board.checkWin(2) || board.checkTie()) {
            // return the score of the board
            return score(board);
        }
        // if it's the AI's turn
        if (maximizingPlayer) {
            // the best score
            int bestScore = Integer.MIN_VALUE;
            // the current board
            Connect4Board currentBoard = board;
            // loop through all the columns
            for (int i = 0; i < 7; i++) {
                // if the column is not full
                if (currentBoard.dropPiece(i + 1, 2)) {
                    // get the score of the move
                    int score = minimax(currentBoard, depth - 1, alpha, beta, false);
                    // set the best score to the max of the best score and the score
                    bestScore = Math.max(bestScore, score);
                    // set alpha to the max of alpha and the score
                    alpha = Math.max(alpha, score);
                    // reset the board
                    currentBoard = board;
                    // if beta is less than or equal to alpha
                    if (beta <= alpha) {
                        // break out of the loop
                        break;
                    }
                }
            }
            // return the best score
            return bestScore;
            // if it's the player's turn
        } else {
            // the best score
            int bestScore = Integer.MAX_VALUE;
            // the current board
            Connect4Board currentBoard = board;
            // loop through all the columns
            for (int i = 0; i < 7; i++) {
                // if the column is not full
                if (currentBoard.dropPiece(i + 1, 1)) {
                    // get the score of the move
                    int score = minimax(currentBoard, depth - 1, alpha, beta, true);
                    // set the best score to the min of the best score and the score
                    bestScore = Math.min(bestScore, score);
                    // set beta to the min of beta and the score
                    beta = Math.min(beta, score);
                    // reset the board
                    currentBoard = board;
                    // if beta is less than or equal to alpha
                    if (beta <= alpha) {
                        // break out of the loop
                        break;
                    }
                }
            }
            // return the best score
            return bestScore;
        }
    }

    // method that returns the score of the board
    public int score(Connect4Board board) {
        // if the AI wins
        if (board.checkWin(2)) {
            // return 100
            return 100;
        }
        // if the player wins
        if (board.checkWin(1)) {
            // return -100
            return -100;
        }
        // if it's a tie
        if (board.checkTie()) {
            // return 0
            return 0;
        }
        // the score
        int score = 0;
        // loop through all the rows
        for (int i = 0; i < 6; i++) {
            // loop through all the columns
            for (int j = 0; j < 7; j++) {
                // if the piece is the AI's
                if (board.board[i][j] == 2) {
                    // if the piece is in the middle
                    if (j == 3) {
                        // add 3 to the score
                        score += 3;
                    }
                    // if the piece is in the middle column
                    if (j == 2 || j == 4) {
                        // add 2 to the score
                        score += 2;
                    }
                    // if the piece is in the middle column
                    if (j == 1 || j == 5) {
                        // add 1 to the score
                        score += 1;
                    }
                }
                // if the piece is the player's
                if (board.board[i][j] == 1) {
                    // if the piece is in the middle
                    if (j == 3) {
                        // subtract 3 from the score
                        score -= 3;
                    }
                    // if the piece is in the middle column
                    if (j == 2 || j == 4) {
                        // subtract 2 from the score
                        score -= 2;
                    }
                    // if the piece is in the middle column
                    if (j == 1 || j == 5) {
                        // subtract 1 from the score
                        score -= 1;
                    }
                }
            }
        }
        // return the score
        return score;
    }
}