package dvp.utils;

import java.util.ArrayList;

public class Board {
    private final int row_size;
    private final int column_size;
    private Piece[][] grid;

    public Board(int row_size, int column_size, ArrayList<Piece> gamePieces) {
        if (row_size <= 0 || column_size <= 0) {
            throw new IllegalArgumentException("Ukuran papan tidak mungkin negatif atau nol");
        }

        this.row_size = row_size;
        this.column_size = column_size;

        grid = new Piece[column_size][row_size];
        placePieces(gamePieces);
    }

    public void displayBoard() {
        for (int i = 0; i < column_size; i++) {
            for(int j = 0; j < row_size; j++) {
                System.out.print(grid[i][j] == null ? "." : grid[i][j].getPieceName());
            }
        }
    }

    public void placePieces(ArrayList<Piece> gamePieces) {
        for (Piece piece : gamePieces) {
            int x = piece.getHeight();
            int y = piece.getWidth();

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
}
