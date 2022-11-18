
// Path: Connect4AI.java

public class Connect4AI {
    // getMove function returns the column to drop the piece
    public int getMove(Connect4Board board) {
        //create duplicate Connect4Board object
        Connect4Board boardCopy = new Connect4Board();
        //copy the board from the parameter to the duplicate board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                boardCopy.board[i][j] = board.board[i][j];
            }
        }
        int bestMove = 0;
        int bestScore = -1000;
        for (int i = 0; i < 7; i++) {
            if (board.dropPiece(i + 1, 2)) {
                int score = minimax(boardCopy, 0, 5, false);
                board.dropPiece(i + 1, 0);
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

    // evaluate function gives a score to the board
    private int evaluate(Connect4Board board) {
        int score = 0;
        // score for horizontal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                int count = 0;
                for (int k = 0; k < 4; k++) {
                    if (board.board[i][j + k] == 2) {
                        count++;
                    } else if (board.board[i][j + k] == 1) {
                        count--;
                    }
                }
                if (count == 4) {
                    score += 100;
                } else if (count == 3) {
                    score += 10;
                } else if (count == 2) {
                    score += 1;
                } else if (count == -4) {
                    score -= 100;
                } else if (count == -3) {
                    score -= 10;
                } else if (count == -2) {
                    score -= 1;
                }
            }
        }
        // score for vertical
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                int count = 0;
                for (int k = 0; k < 4; k++) {
                    if (board.board[i + k][j] == 2) {
                        count++;
                    } else if (board.board[i + k][j] == 1) {
                        count--;
                    }
                }
                if (count == 4) {
                    score += 100;
                } else if (count == 3) {
                    score += 10;
                } else if (count == 2) {
                    score += 1;
                } else if (count == -4) {
                    score -= 100;
                } else if (count == -3) {
                    score -= 10;
                } else if (count == -2) {
                    score -= 1;
                }
            }
        }
        // score for diagonal
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int count = 0;
                for (int k = 0; k < 4; k++) {
                    if (board.board[i + k][j + k] == 2) {
                        count++;
                    } else if (board.board[i + k][j + k] == 1) {
                        count--;
                    }
                }
                if (count == 4) {
                    score += 100;
                } else if (count == 3) {
                    score += 10;
                } else if (count == 2) {
                    score += 1;
                } else if (count == -4) {
                    score -= 100;
                } else if (count == -3) {
                    score -= 10;
                } else if (count == -2) {
                    score -= 1;
                }
            }
        }
        // score for anti-diagonal
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 7; j++) {
                int count = 0;
                for (int k = 0; k < 4; k++) {
                    if (board.board[i + k][j - k] == 2) {
                        count++;
                    } else if (board.board[i + k][j - k] == 1) {
                        count--;
                    }
                }
                if (count == 4) {
                    score += 100;
                } else if (count == 3) {
                    score += 10;
                } else if (count == 2) {
                    score += 1;
                } else if (count == -4) {
                    score -= 100;
                } else if (count == -3) {
                    score -= 10;
                } else if (count == -2) {
                    score -= 1;
                }
            }
        }
        return score;
    }

    // printBoard function prints the board
    private void printBoard(int[][] tempBoard) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(tempBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // drop piece function drops the piece in the column
    public void dropPiece(Connect4Board board, int column, int player) {
        int[][] tempBoard = board.board;
        for (int i = 5; i >= 0; i--) {
            if (tempBoard[i][column] == 0) {
                tempBoard[i][column] = player;
                break;
            }
        }
    }

    // remove piece function removes the piece from the column
    public void removePiece(Connect4Board board, int column) {
        int[][] tempBoard = board.board;
        for (int i = 0; i < 6; i++) {
            if (tempBoard[i][column] != 0) {
                tempBoard[i][column] = 0;
                break;
            }
        }
    }
}
