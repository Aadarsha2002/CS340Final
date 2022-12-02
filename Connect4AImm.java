// Path: Connect4AImm.java

public class Connect4AImm {
    // getMove function returns the column to drop the piece
    public int getMove(Connect4Board board) {
        // create duplicate Connect4Board object
        Connect4Board boardCopy = new Connect4Board();
        // copy the board from the parameter to the duplicate board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                boardCopy.board[i][j] = board.board[i][j];
            }
        }
        int bestMove = 0;
        int bestScore = -1000;
        for (int i = 0; i < 7; i++) {
            if (board.dropPiece(i + 1, 0)) {
                int score = minimax(boardCopy, 0, 100, false);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove + 1;
    }

    // minimax function returns the score of the board
    public int minimax(Connect4Board board, int depth, int maxDepth, boolean isMaximizing) {
        if (depth == maxDepth) {
            return evaluate(board);
        }
        if (isMaximizing) {
            int bestScore = -1000;
            for (int i = 0; i < 7; i++) {
                if (board.dropPiece(i + 1, 2)) {
                    int score = minimax(board, depth + 1, maxDepth, false);
                    board.dropPiece(i + 1, 0);
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;

        } else {
            int bestScore = 1000;
            for (int i = 0; i < 7; i++) {
                if (board.dropPiece(i + 1, 1)) {
                    int score = minimax(board, depth + 1, maxDepth, true);
                    board.dropPiece(i + 1, 0);
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    // finds streaks of 4's, 3's and 2's for user and AI
    private int evaluate(Connect4Board tempBoard) {
        int my_fours = streak(tempBoard, 4, 1);
        int my_threes = streak(tempBoard, 3, 1);
        int my_twos = streak(tempBoard, 2, 1);
        int ai_fours = streak(tempBoard, 4, 2);
        int ai_threes = streak(tempBoard, 3, 2);
        int ai_twos = streak(tempBoard, 2, 2);
        // utility equation for score of a current board
        return (my_fours * 10 + my_threes * 5 + my_twos * 2) - (ai_fours * 10 + ai_threes * 5 + ai_twos * 2);
    }

    // checks a board for a specified streak of peices for a player
    private int streak(Connect4Board tempBoard, int streak, int player) {
        int count = 0;
        // look through the entire board and find all instances of
        // the current players moves
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (tempBoard.board[i][j] == player) {
                    // once you find current players move check for a vertical streak,...
                    count += verticalStreak(i, j, tempBoard, streak);
                    // ... a horizontal streak...
                    count += horizontalStreak(i, j, tempBoard, streak);
                    // ... and a diagonal streak
                    count += diagonalStreak(i, j, tempBoard, streak);
                }
            }
        }
        return count;
    }

    private int verticalStreak(int row, int col, Connect4Board tempBoard, int streak) {
        int count = 0;
        for (int i = row; i < 6; i++) {
            if (tempBoard.board[i][col] == tempBoard.board[row][col]) {
                count++;
            }
        }

        if (count >= streak) {
            return 1;
        } else {
            return 0;
        }
    }

    private int horizontalStreak(int row, int col, Connect4Board tempBoard, int streak) {
        int count = 0;
        for (int i = col; i < 7; i++) {
            if (tempBoard.board[row][i] == tempBoard.board[row][col]) {
                count++;
            }
        }

        if (count >= streak) {
            return 1;
        } else {
            return 0;
        }
    }

    private int diagonalStreak(int row, int col, Connect4Board tempBoard, int streak) {
        int total = 0;
        int count = 0;
        for (int i = row; i < 6; i++) {
            if (col > 6) {
                break;
            } else if (tempBoard.board[i][col] == tempBoard.board[row][col]) {
                count++;
            }
            col++;
        }

        if (count >= streak) {
            total += 1;
        }

        count = 0;

        for (int i = row; i > 0; i--) {
            if (col > 6) {
                break;
            } else if (tempBoard.board[i][col] == tempBoard.board[row][col]) {
                count++;
            }
            col++;
        }
        if (count >= streak) {
            total++;
        }

        return total;
    }

    // drop piece function drops the piece in the column
    public void AIdropPiece(Connect4Board board, int column, int player) {
        int[][] tempBoard = board.board;
        for (int i = 5; i >= 0; i--) {
            if (tempBoard[i][column] == 0) {
                tempBoard[i][column] = player;
                break;
            }
        }
    }

    // remove piece function removes the piece from the column
    public void AIremovePiece(Connect4Board board, int column) {
        int[][] tempBoard = board.board;
        for (int i = 0; i < 6; i++) {
            if (tempBoard[i][column] != 0) {
                tempBoard[i][column] = 0;
                break;
            }
        }
    }
}
