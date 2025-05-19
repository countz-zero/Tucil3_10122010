package dvp.utils;

public class Move {
    Board resultState;
    String piece_name;
    Direction dir;
    
    public Move(Board resultState, String piece_name, Direction dir) {
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
