package dvp.utils;

public class Move {
    Board resultState;
    String piece_name;
    Direction dir;
    String moveDescription;
    
    public Move(Board resultState, String piece_name, Direction dir) {
        this.resultState = resultState;
        this.piece_name = piece_name;
        this.dir = dir;
        this.moveDescription = piece_name + "-" + dir.toString();
    }

    public Board getResultState() {
        return resultState;
    }

    public String getPieceName() {
        return piece_name;
    }

    public Direction getDir() {
        return dir;
    }

    public String getMoveDesc() {
        return moveDescription;
    }
}
