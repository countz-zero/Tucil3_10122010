package dvp.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Board {
    //TODO Gimana kalo piece pake hashmap
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";

    private final int row_size;
    private final int column_size;
    private Piece[][] grid = null;
    private int[] exit_location; // Koordinat 0 = posisinya, koordinat 1 == angka barisannya. 1 == Atas, mutar lawan jarum jam
    private Piece main_car;
    private int main_car_idx;
    private ArrayList<Piece> gamePieces;

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

        this.gamePieces = gamePieces;
    }

    public Board(Board other) {
        this.column_size = other.column_size;
        this.row_size = other.row_size;
        this.exit_location = other.exit_location;
        this.gamePieces = new ArrayList<>();
        for (Piece p : other.gamePieces) {
            this.gamePieces.add(new Piece(p));
            if(p.getPieceName().equals("P")) {
                this.main_car = new Piece(p);
            }
        }
        
        this.main_car_idx = other.main_car_idx;
        this.grid = other.grid;
    }

    public void movePiece(Piece piece, Direction dir) {
        placePieces();
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

                piece.setRow(anchor_row - 1);
                break;
            case Bawah:
                if(anchor_row + len == row_size) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena blok sudah di pinggir");
                } else if (grid[anchor_row + len][anchor_col] != null) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena ada blok lain");
                }

                piece.setRow(anchor_row + 1);
                break;
            case Kiri:
                if(anchor_col == 0) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena blok sudah di pinggir");
                } else if (grid[anchor_row][anchor_col - 1] != null) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena ada blok lain");
                }

                piece.setCol(anchor_col - 1);
                break;
            case Kanan:
                if(anchor_col + len == column_size) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena blok sudah di pinggir");
                } else if (grid[anchor_row][anchor_col + len] == null) {
                    throw new IllegalArgumentException("Gerakan tidak bisa dilakukan karena ada blok lain");
                }

                piece.setCol(anchor_col + 1);
                break;
        }

        clearBoard();
    }

    public String displayBoard() {
        StringBuilder sb = new StringBuilder();
        placePieces();
        if(exit_location[0] == 1) {
            String gate = " ".repeat(exit_location[1]) + GREEN + "K" + RESET + " ".repeat(column_size - exit_location[1] + 1);
            sb.append(gate + "\n");
        }

        for (int i = 0; i < row_size; i++) {
            for(int j = 0; j < column_size; j++) {
                if(j == 0 && exit_location[0] == 2 && i == exit_location[1]) {
                    sb.append(GREEN + "K" + RESET);
                } else if (j == 0 && exit_location[0] == 2) {
                    sb.append(" ");
                }

                if (grid[i][j] == null) {
                    sb.append(".");
                } else if(grid[i][j].getPieceName().equals("P")) {
                    sb.append(RED + "P" + RESET);
                } else {
                    sb.append(grid[i][j].getPieceName());   
                }

                if(j == column_size - 1 && exit_location[0] == 4 && i == exit_location[1]) {
                    sb.append(GREEN + "K" + RESET);
                } else if (j == column_size - 1 && exit_location[0] == 4) {
                   sb.append(" ");
                }
            }

            sb.append("\n");
        }

        if(exit_location[0] == 3) {
            String gate = " ".repeat(exit_location[1]) + GREEN + "K" + RESET + " ".repeat(column_size - exit_location[1] + 1);
            sb.append(gate);
        }

        clearBoard();
        return sb.toString();
    }

    public String displayBoard(Piece piece) {
        StringBuilder sb = new StringBuilder();
        placePieces();
        if(exit_location[0] == 1) {
            String gate = " ".repeat(exit_location[1]) + GREEN + "K" + RESET + " ".repeat(column_size - exit_location[1] + 1);
            sb.append(gate + "\n");
        }

        for (int i = 0; i < row_size; i++) {
            for(int j = 0; j < column_size; j++) {
                if(j == 0 && exit_location[0] == 2 && i == exit_location[1]) {
                    sb.append(GREEN + "K" + RESET);
                } else if (j == 0 && exit_location[0] == 2) {
                    sb.append(" ");
                }

                if (grid[i][j] == null) {
                    sb.append(".");
                } else if(grid[i][j].getPieceName().equals("P")) {
                    sb.append(RED + "P" + RESET);
                } else if (grid[i][j].getPieceName().equals(piece.getPieceName())){
                    sb.append(BLUE + grid[i][j].getPieceName() + RESET);
                } else{
                    sb.append(grid[i][j].getPieceName());   
                }

                if(j == column_size - 1 && exit_location[0] == 4 && i == exit_location[1]) {
                    sb.append(GREEN + "K" + RESET);
                } else if (j == column_size - 1 && exit_location[0] == 4) {
                   sb.append(" ");
                }
            }

            sb.append("\n");
        }

        if(exit_location[0] == 3) {
            String gate = " ".repeat(exit_location[1]) + GREEN + "K" + RESET + " ".repeat(column_size - exit_location[1] + 1);
            sb.append(gate);
        }

        clearBoard();
        return sb.toString();
    }

    public void placePieces() {
        for (Piece piece : gamePieces) {
            int anchor_row = piece.getRow();
            int anchor_col = piece.getCol();

            System.err.println(piece.getPieceName() + " " + Integer.toString(anchor_row) + " " + Integer.toString(anchor_col));
            if(anchor_row >= row_size || anchor_row < 0 || anchor_col >= column_size || anchor_col < 0) {
                throw new IllegalArgumentException("Ada piece di luar papan");
            }

            if(piece.getisVertical() && (anchor_row + piece.getSize() > row_size)) {
                throw new IllegalArgumentException("Vertical piece extends off the board");
            }
            if(!piece.getisVertical() && (anchor_col + piece.getSize() > column_size)) {
                throw new IllegalArgumentException("Horizontal piece extends off the board");
            }

            if(piece.getisVertical()) {
                for (int i = 0; i < piece.getSize(); i++) {
                    if(grid[anchor_row + i][anchor_col] != null) {
                        throw new IllegalStateException("Position is already occupied");
                    }
                    grid[anchor_row + i][anchor_col] = piece;
                }
            } else {
                for (int i = 0; i < piece.getSize(); i++) {
                    if(grid[anchor_row][anchor_col + i] != null) {
                        throw new IllegalStateException("Position is already occupied");
                    }
                    grid[anchor_row][anchor_col + i] = piece;
                }
            }
        }
    }

    public boolean isWinState() {
        boolean isWin = false;
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

    public ArrayList<Move> generateSuccessor() {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        placePieces();
        for(int i = 0; i < gamePieces.size(); i++) {
            Piece p = gamePieces.get(i);
            int anchor_row = p.getRow();
            int anchor_col = p.getCol();
            int size = p.getSize();
            if(p.getisVertical()) {
                if(anchor_row > 0 && grid[anchor_row - 1][anchor_col] == null) {
                    Board newState = new Board(this);
                    Piece newPiece = newState.gamePieces.get(i);
                    newPiece.setRow(newPiece.getRow() - 1);
                    possibleMoves.add(new Move(newState, newPiece.getPieceName(), Direction.Atas));
                } else if(anchor_row + size < row_size  && grid[anchor_row + size][anchor_col] == null) {
                    Board newState = new Board(this);
                    Piece newPiece = newState.gamePieces.get(i);
                    newPiece.setRow(newPiece.getRow() + 1);
                    possibleMoves.add(new Move(newState, newPiece.getPieceName(), Direction.Bawah));
                }
            } else if(!p.getisVertical()) {
                if(anchor_col > 0 && grid[anchor_row][anchor_col - 1] == null) {
                    Board newState = new Board(this);
                    Piece newPiece = newState.gamePieces.get(i);
                    newPiece.setCol(newPiece.getCol() - 1);
                    possibleMoves.add(new Move(newState, newPiece.getPieceName(), Direction.Kiri));
                } else if(anchor_col + size < column_size  && grid[anchor_row][anchor_col + size] == null) {
                    Board newState = new Board(this);
                    Piece newPiece = newState.gamePieces.get(i);
                    newPiece.setCol(newPiece.getCol() + 1);
                    possibleMoves.add(new Move(newState, newPiece.getPieceName(), Direction.Kanan));
                }
            }
        }

        return possibleMoves;
    } 

    public String getStateHash() {
        StringBuilder sb = new StringBuilder("");
        ArrayList<Piece> sortedPieces = new ArrayList<>(gamePieces);
        sortedPieces.sort(Comparator.comparing(Piece::getPieceName));

         for (Piece p : sortedPieces) {
            sb.append(p.getPieceName()).append(":");
            sb.append(p.getRow()).append(",");
            sb.append(p.getCol()).append(";");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board state = (Board) o;
        
        if (gamePieces.size() != state.gamePieces.size()) return false;
        
        for (int i = 0; i < gamePieces.size(); i++) {
            if (!gamePieces.get(i).equals(state.gamePieces.get(i))) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(gamePieces);
    }

    public void findAndGetPCar(ArrayList<Piece> gamePieces) {
        for(int i = 0; i < gamePieces.size(); i++) {
            if(gamePieces.get(i).getPieceName().equals("P")) {
                main_car = gamePieces.get(i);
                main_car_idx = i;
                return;
            }
        }

        throw new IllegalArgumentException("Tidak ada mobil berlabel P");
    }

    public void clearBoard() {
        for (int i = 0; i < row_size; i++) {
            for(int j = 0; j < column_size; j++) {
                grid[i][j] = null;
        }
    }
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

    public int getRowSize() {
        return row_size;
    }

    public int getColSize() {
        return column_size;
    }

    public int getExitLocationOrientation() {
        return exit_location[0];
    }

    public int getExitLocationPosition() {
        return exit_location[1];
    }

    public Piece getMainPiece() {
        return main_car;
    }

    public int getMainCarIdx() {
        return main_car_idx;
    }

    public ArrayList<Piece> getGamePieces() {
        return gamePieces;
    }

    public Piece[][] getGridConfig() {
        try{
        placePieces();
        return grid;
        }
        finally {
            clearBoard();
        }
    }

}
