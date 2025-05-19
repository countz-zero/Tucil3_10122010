package dvp.utils;

import java.util.ArrayList;

public class Board {
    //TODO Gimana kalo piece pake hashmap
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";

    private final int row_size;
    private final int column_size;
    private Piece[][] grid;
    private int[] exit_location; // Koordinat 0 = posisinya, koordinat 1 == angka barisannya. 1 == Atas, mutar lawan jarum jam
    private Piece main_car;

    public Board(int row_size, int column_size, ArrayList<Piece> gamePieces, int[] exit_location) {
        if (row_size <= 0 || column_size <= 0) {
            throw new IllegalArgumentException("Ukuran papan tidak mungkin negatif atau nol");
        }

        this.row_size = row_size;
        this.column_size = column_size;

        this.grid = new Piece[row_size][column_size];

        findAndGetPCar(gamePieces);

        if(!isExitAligned(gamePieces, exit_location)) {
            throw new IllegalArgumentException("Puzzle tidak mungkin diselesaikan karena mobil P tidak bisa keluar");
        }

        this.exit_location = exit_location;

        if(!isCarInFrontP(gamePieces)) {
            throw new IllegalArgumentException("Ada mobil yang di depan mobil merah (P)");
        }

        placePieces(gamePieces);
    }

    public enum Direction {
        Atas,
        Bawah,
        Kiri,
        Kanan
    }

    public void movePiece(Piece piece, Direction dir) {
        int anchor_row = piece.getRow();
        int anchor_col = piece.getCol();
        int len = piece.getSize();

        if (piece.getisVertical()) {
            if (!(dir == Direction.Atas || dir == Direction.Bawah)) {
                throw new IllegalArgumentException("Tidak bisa dilakukan karena orientasi");
            }
        } else {
            if (!(dir == Direction.Kiri || dir == Direction.Kanan)) {
                throw new IllegalArgumentException("Tidak bisa dilakukan karena orientasi");
            }
        }

        switch (dir) {
            case Atas:
                if(anchor_row == 0) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena blok sudah di pinggir");
                } else if (grid[anchor_row - 1][anchor_col] != null) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena ada blok lain");
                }

                grid[anchor_row - 1][anchor_col] = piece;
                grid[anchor_row + len - 1][anchor_col] = null;
                piece.setRow(anchor_row - 1);
                break;
            case Bawah:
                if(anchor_row + len == row_size) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena blok sudah di pinggir");
                } else if (grid[anchor_row + len][anchor_col] != null) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena ada blok lain");
                }

                grid[anchor_row + len][anchor_col] = piece;
                grid[anchor_row][anchor_col] = null;
                piece.setRow(anchor_row + 1);
                break;
            case Kiri:
                if(anchor_col == 0) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena blok sudah di pinggir");
                } else if (grid[anchor_row][anchor_col - 1] != null) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena ada blok lain");
                }

                grid[anchor_row][anchor_col - 1] = piece;
                grid[anchor_row][anchor_col + len - 1] = null;
                piece.setCol(anchor_col - 1);
                break;
            case Kanan:
                if(anchor_col + len == column_size) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena blok sudah di pinggir");
                } else if (grid[anchor_row][anchor_col + len] == null) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena ada blok lain");
                }

                grid[anchor_row][anchor_col + len] = piece;
                grid[anchor_row][anchor_col] = null;
                piece.setCol(anchor_col + 1);
                break;
        }
    }

    public void displayBoard() {
        if(exit_location[0] == 1) {
            String gate = " ".repeat(exit_location[1]) + GREEN + "K" + RESET + " ".repeat(column_size - exit_location[1] + 1);
            System.out.println(gate);
        }

        for (int i = 0; i < row_size; i++) {
            for(int j = 0; j < column_size; j++) {
                if(j == 0 && exit_location[0] == 2 && i == exit_location[1]) {
                    System.out.print(GREEN + "K" + RESET);
                } else if (j == 0 && exit_location[0] == 2) {
                    System.out.print(" ");
                }

                if (grid[i][j] == null) {
                    System.out.print(".");
                } else if(grid[i][j].getPieceName().equals("P")) {
                    System.out.print(RED + "P" + RESET);
                } else {
                    System.out.print(grid[i][j].getPieceName());   
                }

                if(j == column_size - 1 && exit_location[0] == 4 && i == exit_location[1]) {
                    System.out.print(GREEN + "K" + RESET);
                } else if (j == column_size - 1 && exit_location[0] == 4) {
                   System.out.print(" ");
                }
            }

            System.err.print("\n");
        }

        if(exit_location[0] == 3) {
            String gate = " ".repeat(exit_location[1]) + GREEN + "K" + RESET + " ".repeat(column_size - exit_location[1] + 1);
            System.out.print(gate);
        }
    }

    public void displayBoard(Piece piece) {
        if(exit_location[0] == 1) {
            String gate = " ".repeat(exit_location[1]) + GREEN + "K" + RESET + " ".repeat(column_size - exit_location[1] + 1);
            System.out.println(gate);
        }

        for (int i = 0; i < row_size; i++) {
            for(int j = 0; j < column_size; j++) {
                if(j == 0 && exit_location[0] == 2 && i == exit_location[1]) {
                    System.out.print(GREEN + "K" + RESET);
                } else if (j == 0 && exit_location[0] == 2) {
                    System.out.print(" ");
                }

                if (grid[i][j] == null) {
                    System.out.print(".");
                } else if(grid[i][j].getPieceName().equals("P")) {
                    System.out.print(RED + "P" + RESET);
                } else if (grid[i][j].getPieceName().equals(piece.getPieceName())){
                    System.out.print(BLUE + grid[i][j].getPieceName() + RESET);
                } else{
                    System.out.print(grid[i][j].getPieceName());   
                }

                if(j == column_size - 1 && exit_location[0] == 4 && i == exit_location[1]) {
                    System.out.print(GREEN + "K" + RESET);
                } else if (j == column_size - 1 && exit_location[0] == 4) {
                   System.out.print(" ");
                }
            }

            System.err.print("\n");
        }

        if(exit_location[0] == 3) {
            String gate = " ".repeat(exit_location[1]) + GREEN + "K" + RESET + " ".repeat(column_size - exit_location[1] + 1);
            System.out.print(gate);
        }
    }

    public void placePieces(ArrayList<Piece> gamePieces) {
        for (Piece piece : gamePieces) {
            int x = piece.getRow();
            int y = piece.getCol();

            if(x >= row_size || x < 0 || y >= column_size || y < 0) {
                throw new IllegalArgumentException("Ada piece di luar papan");
            }

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

    public boolean isWinState() {
        boolean isWin = false;
        int position = exit_location[1];
        switch (exit_location[0]) {
            case 1:
            if (main_car.getRow() == 0) {
                isWin = true;
            }
            break;
            case 2:
            if(main_car.getCol() == 0) {
                isWin = true;
            }
            break;
            case 3:
            if(main_car.getRow() + main_car.getSize() - 1 == row_size - 1) {
                isWin = true;
            }
            break;
            case 4:
            if(main_car.getCol() + main_car.getSize() - 1 == column_size - 1) {
                isWin =  true;
            }
        }

        return isWin;
    }

    public void findAndGetPCar(ArrayList<Piece> gamePieces) {
        for(Piece piece : gamePieces) {
            if(piece.getPieceName().equals("P")) {
                main_car = piece;
                return;
            }
        }

        throw new IllegalArgumentException("Tidak ada mobil berlabel P");
    }

    public boolean isCarInFrontP(ArrayList<Piece> gamePieces) {
        for (Piece piece : gamePieces) {
            if(piece.getPieceName().equals("P")) {
                continue;
            }

            if(main_car.getisVertical() && piece.getisVertical() && (piece.getCol() == main_car.getCol())) {
                if(exit_location[0] == 1 && piece.getRow() < main_car.getRow()) {
                    return false;
                } else if (exit_location[0] == 3 && piece.getRow() > main_car.getRow()) {
                    return false;
                }
            } else if(!main_car.getisVertical() && !piece.getisVertical() && (piece.getRow() == main_car.getRow())) {
                if(exit_location[0] == 2 && piece.getCol() < main_car.getCol()) {
                    return false;
                } else if (exit_location[0] == 4 && piece.getCol() > main_car.getCol()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isExitAligned(ArrayList<Piece> gamePieces, int[] exit_location) {
        if ((exit_location[0] == 1 || exit_location[0] == 3) && main_car.getCol() == exit_location[1] && main_car.getisVertical()) {
            return true;
        } else if ((exit_location[0] == 2 || exit_location[0] == 4) && main_car.getRow() == exit_location[1] && !main_car.getisVertical()) {
            return true;
        }

        return false;
    }
}
