package dvp.utils;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int row_size;
    private final int column_size;
    private Piece[][] grid;
    private int[] exit_location = new int[2]; // Koordinat 0 = posisinya, koordinat 1 == angka barisannya. 1 == Atas, mutar lawan jarum jam
    private Piece main_car;

    public Board(int row_size, int column_size, ArrayList<Piece> gamePieces) {
        if (row_size <= 0 || column_size <= 0) {
            throw new IllegalArgumentException("Ukuran papan tidak mungkin negatif atau nol");
        }

        this.row_size = row_size;
        this.column_size = column_size;

        this.grid = new Piece[row_size][column_size];

        findAndGetPCar(gamePieces);

        if(!isExitAligned(gamePieces)) {
            throw new IllegalArgumentException("Puzzle tidak mungkin diselesaikan karena mobil P tidak bisa keluar");
        }

        if(!isCarInFrontP(gamePieces)) {
            throw new IllegalArgumentException("Ada mobil yang di depan mobil merah (P)");
        }

        placePieces(gamePieces);
    }

    //TODO Need changes for exit K
    public void displayBoard() {
        for (int i = 0; i < row_size; i++) {
            for(int j = 0; j < column_size; j++) {
                System.out.print(grid[i][j] == null ? "." : grid[i][j].getPieceName());
            }
        }
    }

    public void placePieces(ArrayList<Piece> gamePieces) {
        for (Piece piece : gamePieces) {
            int x = piece.getRow();
            int y = piece.getCol();

            if(piece.getisVertical()) {
                for (int i = 0; i < piece.getSize(); i++) {
                    grid[x + i][y] = piece;
                }
            } else {
                for (int i = 0; i < piece.getSize(); i++) {
                    grid[x][y + i] = piece;
                }
            }
        }
    }

    public void findAndGetPCar(ArrayList<Piece> gamePieces) throws IllegalStateException{
        for(Piece piece : gamePieces) {
            if(piece.getPieceName().equals("P")) {
                main_car = piece;
                break;
            }
        }

        throw new IllegalStateException("Tidak ada mobil berlabel P");
    }

    //! Ada perubahan nanti tergantung jawaban
    //TODO Logikanya perlu diperbaiki kalo blok di belakang mobil
    public boolean isCarInFrontP(ArrayList<Piece> gamePieces) {
        for (Piece piece : gamePieces) {
            if(piece.getPieceName().equals("P")) {
                continue;
            }

            if(main_car.getisVertical() && piece.getisVertical() && (piece.getCol() == main_car.getCol())) {
                return false;
            } else if(!main_car.getisVertical() && !piece.getisVertical() && (piece.getRow() == main_car.getRow())) {
                return false;
            }
        }

        return true;
    }

    public boolean isExitAligned(ArrayList<Piece> gamePieces) {
        if ((exit_location[0] == 1 || exit_location[0] == 3) && main_car.getCol() == exit_location[1]) {
            return true;
        } else if ((exit_location[0] == 2 || exit_location[0] == 4) && main_car.getRow() == exit_location[1]) {
            return true;
        }

        return false;
    }
}
