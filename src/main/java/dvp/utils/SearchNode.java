package dvp.utils;

import java.util.Objects;

public class SearchNode implements Comparable<SearchNode>{
    private Board state;
    private SearchNode parent;
    private String moveDescription;
    private int gScore;
    private int fScore;
    private String method;

    public SearchNode(Board state, String method) {
        this.state = state;
        this.parent = null;
        this.moveDescription = "Initial state";
        this.method = method;
        if(method.equals("G")) {
            this.gScore = 0;
            this.fScore = calculateHeuristic();
        } else if(method.equals("U")) {
            this.gScore = 0;
            this.fScore = 0;
        } else if(method.equals("A")) {
            this.gScore = 0;
            this.fScore = calculateHeuristic();
        } else {
            throw new IllegalArgumentException("Invalid method");
        }
    }

    public SearchNode(Board state, SearchNode parent, String moveDescription) {
        this.state = state;
        this.parent = parent;
        this.moveDescription = moveDescription;
        this.method = parent.method;

        if(method.equals("G")) {
            this.gScore = 0;
            this.fScore = calculateHeuristic();
        } else if(method.equals("U")) {
            this.gScore = parent.gScore + 1;
            this.fScore = parent.gScore + 1;
        } else if(method.equals("A")) {
            this.gScore = parent.gScore + 1;
            this.fScore = this.gScore + calculateHeuristic();
        } else {
            throw new IllegalArgumentException("Invalid method");
        }
    }

    public int calculateHeuristic() {
        Piece main_piece = state.getMainPiece();
        
        int distanceToExit = 0;
        int posAfterPiece = 0;
        int blockingVehicles = 0;
        Piece[][] grid = state.getGridConfig();

        switch (state.getExitLocationOrientation()) {
            //Asumsi pas sudah menang, tidak dicek lagi heuristiknya
            case 1:
            distanceToExit = main_piece.getRow();
            posAfterPiece = main_piece.getRow() - 1;
            
            for (int c = posAfterPiece; c >= 0; c--) {
                if (grid[c][main_piece.getCol()] != null) {
                    blockingVehicles++;
                }
            }
            break;
            case 2:
            distanceToExit = main_piece.getCol();
            posAfterPiece = main_piece.getCol() -1;
            
            // Check each position between the target vehicle and the exit
            for (int c = posAfterPiece; c >= 0; c--) {
                if (grid[main_piece.getRow()][c] != null) {
                    blockingVehicles++;
                }
            }
            break;
            case 3:
            distanceToExit = state.getRowSize() - (main_piece.getRow() + main_piece.getSize());
            posAfterPiece = main_piece.getRow() + main_piece.getSize();
            for (int c = posAfterPiece; c < state.getRowSize(); c++) {
                if (grid[c][main_piece.getCol()] != null) {
                    blockingVehicles++;
                }
            }
            break;
            case 4:
            distanceToExit = state.getColSize() - (main_piece.getCol() + main_piece.getSize());
            posAfterPiece = main_piece.getCol() + main_piece.getSize();
            for (int c = posAfterPiece; c < state.getColSize(); c++) {
                if (grid[main_piece.getRow()][c] != null) {
                    blockingVehicles++;
                }
            }            
            break;
        }
               
        
        return distanceToExit + blockingVehicles;
    }

    @Override
    public int compareTo(SearchNode other) {
        return Integer.compare(this.fScore, other.fScore);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchNode that = (SearchNode) o;
        return state.equals(that.state);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

    public Board getState() {
        return state;
    }

    public SearchNode getParent() {
        return parent;
    }

    public String getMoveDesc() {
        return moveDescription;
    }

    public int getGScore() {
        return gScore;
    }

    public int getFScore() {
        return fScore;
    }
}
