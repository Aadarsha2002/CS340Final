
// Path: Connect4AI.java

public class Connect4AI {
    public int getMove(Connect4Board board) {
        int[][] tempBoard = board.board;
        int column = 0;
        int bestScore = -1000;
        for (int i = 0; i < 7; i++) {
            if (tempBoard[0][i] == 0) {
                tempBoard[0][i] = 2;
                int score = minimax(tempBoard, 0, false);
                tempBoard[0][i] = 0;
                if (score > bestScore) {
                    bestScore = score;
                    column = i;
                }
            }
        }
        return column + 1;
    }

    private int minimax(int[][] tempBoard, int depth, boolean isMaximizing) {
        if (checkWin(tempBoard, 2)) {
            return 1;
        } else if (checkWin(tempBoard, 1)) {
            return -1;
        } else if (checkTie(tempBoard)) {
            return 0;
        }
        if (isMaximizing) {
            int bestScore = -1000;
            for (int i = 0; i < 7; i++) {
                if (tempBoard[0][i] == 0) {
                    tempBoard[0][i] = 2;
                    int score = minimax(tempBoard, depth + 1, false);
                    tempBoard[0][i] = 0;
                    if (score > bestScore) {
                        bestScore = score;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int i = 0; i < 7; i++) {
                if (tempBoard[0][i] == 0) {
                    tempBoard[0][i] = 1;
                    int score = minimax(tempBoard, depth + 1, true);
                    tempBoard[0][i] = 0;
                    if (score < bestScore) {
                        bestScore = score;
                    }
                }
            }
            return bestScore;
        }
    }

    private boolean checkWin(int[][] tempBoard, int player) {
        // check horizontal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (tempBoard[i][j] == player && tempBoard[i][j + 1] == player && tempBoard[i][j + 2] == player
                        && tempBoard[i][j + 3] == player) {
                    return true;
                }
            }
        }
        // check vertical
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (tempBoard[i][j] == player && tempBoard[i + 1][j] == player && tempBoard[i + 2][j] == player
                        && tempBoard[i + 3][j] == player) {
                    return true;
                }
            }
        }
        // check diagonal
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (tempBoard[i][j] == player && tempBoard[i + 1][j + 1] == player && tempBoard[i + 2][j + 2] == player
                        && tempBoard[i + 3][j + 3] == player) {
                    return true;
                }
            }
        }
        // check other diagonal
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 7; j++) {
                if (tempBoard[i][j] == player && tempBoard[i + 1][j - 1] == player && tempBoard[i + 2][j - 2] == player
                        && tempBoard[i + 3][j - 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkTie(int[][] tempBoard) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (tempBoard[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}