package dvp.utils;

public class Piece {
    //TODO GIMANA KALO PIECE PAKE HASHMAP, ARRAYLISTNYA BIAR ENAK NYARINYA
    private final String piece_name;
    private int height = 1;
    private int width = 1;
    private int size = 1;
    private Boolean isVertical = null;
    private int row;
    private int col;

    public Piece(String piece_name, int row, int col) {
        final String validName_String = "ABCDEFGHIJLMNOPQRSTUVWXYZ";
        if(!validName_String.contains(String.valueOf(piece_name))) {
            throw new IllegalArgumentException("K tidak boleh digunakan untuk blok");
        }

        this.piece_name = piece_name;
        this.row = row;
        this.col = col;
    }

    public void addPosition(int i, int j) {
        if (i == row) {
            isVertical = false;
            incWidth();
            if (width > 3) {
                throw new IllegalArgumentException("Ada blok yang tingginya lebih dari 3");
            }
        } else if (j == col) {
            isVertical = true;
            incHeight();
            if (height > 3) {
                throw new IllegalArgumentException("Ada blok yang lebarnya lebih dari 3");
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getSize() {
        return size;
    }

    public String getPieceName() {
        return piece_name;
    }

    public boolean getisVertical() {
        return isVertical;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int newRow) {
        row  = newRow;
    }

    public void setCol(int newCol) {
        row  = newCol;
    }

    public void incHeight() {
        height += 1;
        size += 1;
    }

    public void incWidth() {
        width += 1;
        size += 1;
    }
}
