package dvp.utils;

public class Move {
    PuzzleState resultState;
    String piece_name;
    Direction dir;
    
    public Move(PuzzleState resultState, String piece_name, Direction dir) {
        this.resultState = resultState;
        this.piece_name = piece_name;
        this.dir = dir;
    }

    public enum Direction {
        Atas,
        Bawah,
        Kiri,
        Kanan
    }

}
