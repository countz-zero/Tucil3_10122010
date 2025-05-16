package dvp.utils;

public class Piece {
    private final char piece_name;
    private int height;
    private int width;
    private int size;
    private boolean isVertical;
    private final boolean isPCar;

    public Piece(char piece_name, int height, int width) {
        if(!isValidSize(height, width)) {
            throw new IllegalArgumentException("Ada blok yang ukurannya tidak valid");
        }

        final String validName_String = "ABCDEFGHIJLMNOPQRSTUVWXYZ";
        if(!validName_String.contains(String.valueOf(piece_name))) {
            throw new IllegalArgumentException("K tidak boleh digunakan untuk blok");
        }

        // Kalau ada dilarang
        // if(piece_name == 'P' && ((height == 3) || (width == 3))) {
        //     throw new IllegalArgumentException("Ukuran mobil P tidak valid")
        // }

        this.piece_name = piece_name;
        this.height = height;
        this.width = width;
        this.size = (width == 1) ? height : width;
        this.isVertical = (width == 1) ? true : false;
        this.isPCar = (piece_name == 'P') ? true : false;
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

    public char getPieceName() {
        return piece_name;
    }

    public boolean getisVertical() {
        return isVertical;
    }

    public boolean getisPCar() {
        return isPCar;
    }
}
