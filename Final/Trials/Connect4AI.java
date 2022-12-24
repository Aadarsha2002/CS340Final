package Trials;
// Path: Connect4AI.java

public class Connect4AI {
    Integer aiType = 0;

    public Connect4AI(Integer aiType) {
        this.aiType = aiType;
    }

    // getMove function for Connect4AI returns the column to drop the piece in based
    // on the aiType
    public int getMove(Connect4Board board) {
        if (aiType.equals(1)) {
            Connect4AImm ai = new Connect4AImm();
            return ai.getMove(board);
        } else {
            Connect4AIab ai = new Connect4AIab();
            return ai.getMove(board);
        }
    }
}