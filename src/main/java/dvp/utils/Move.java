package dvp.utils;

public class Move {
    Board resultState;
    String piece_name;
    Direction dir;
    int piece_id;
    String moveDescription;
    
    public Move(Board resultState, int piece_id, String piece_name, Direction dir) {
        this.resultState = resultState;
        this.piece_id = piece_id;
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

    public int getPieceId() {
        return piece_id;
    }
}
