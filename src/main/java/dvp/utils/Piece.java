package dvp.utils;

public class Piece {
    private final String piece_name;
    private final int height;
    private final int width;
    private final int size;
    private final boolean isVertical;
    private int row;
    private int col;

    public Piece(String piece_name, int height, int width, int row, int col) {
        if(!isValidSize(height, width)) {
            String errMessage = String.format("Blok %s yang ukurannya tidak valid", piece_name);
            throw new IllegalArgumentException(errMessage);
        }

        final String validName_String = "ABCDEFGHIJLMNOPQRSTUVWXYZ";
        if(!validName_String.contains(String.valueOf(piece_name))) {
            throw new IllegalArgumentException("K tidak boleh digunakan untuk blok");
        }

        this.piece_name = piece_name;
        this.height = height;
        this.width = width;
        this.size = (width == 1) ? height : width;
        this.isVertical = (width == 1) ? true : false;
        this.row = row;
        this.col = col;
    }

    private boolean isValidSize(int height, int width) {
        return (width == 1 && height == 2) ||
               (width == 2 && height == 1) ||
               (width == 3 && height == 1) ||
               (width == 1 && height == 3);            
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
}
