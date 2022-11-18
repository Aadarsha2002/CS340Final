
// Path: Connect4Board.java

public class Connect4Board {
    int[][] board = new int[6][7];

    public Connect4Board() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean dropPiece(int column, int player) {
        if (column < 1 || column > 7) {
            return false;
        }
        column--;
        for (int i = 5; i >= 0; i--) {
            if (board[i][column] == 0) {
                board[i][column] = player;
                return true;
            }
        }
        return false;
    }

    public boolean checkWin(int player) {
        // check horizontal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player
                        && board[i][j + 3] == player) {
                    return true;
                }
            }
        }
        // check vertical
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == player
                        && board[i + 3][j] == player) {
                    return true;
                }
            }
        }
        // check diagonal
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == player && board[i + 1][j + 1] == player && board[i + 2][j + 2] == player
                        && board[i + 3][j + 3] == player) {
                    return true;
                }
            }
        }
        // check other diagonal
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 7; j++) {
                if (board[i][j] == player && board[i + 1][j - 1] == player && board[i + 2][j - 2] == player
                        && board[i + 3][j - 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkTie() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
