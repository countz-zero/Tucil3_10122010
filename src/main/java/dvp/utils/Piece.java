package dvp.utils;

import java.util.Objects;

public class Piece {
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

    public Piece(Piece other) {
        this.piece_name = other.piece_name;
        this.row = other.row;
        this.col = other.col;
        this.height = other.height;
        this.width = other.width;
        this.size = other.size;
        this.isVertical = other.isVertical;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return row == piece.row && 
               col == piece.col && 
               size == piece.size && 
               isVertical == piece.isVertical;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(row, col, size, isVertical);
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
        col  = newCol;
    }

    public void incHeight() {
        height += 1;
        size += 1;
    }

    public void incWidth() {
        width += 1;
        size += 1;
    }

    public void decHeight() {
        height -= 1;
        size -= 1;
    }

    public void decWidth() {
        width -= 1;
        size -= 1;
    }
}
