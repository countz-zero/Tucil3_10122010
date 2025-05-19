package dvp.utils;

import java.lang.classfile.instruction.ArrayLoadInstruction;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import javax.sound.midi.SysexMessage;

public class PuzzleState {
    private Board board;
    private ArrayList<Piece> gamePieces;

    public PuzzleState(Board board, ArrayList<Piece> gamePieces) {
        this.board = board;
        this.gamePieces = gamePieces;
    }

    public RushHourState(PuzzleState other) {
        this.gamePieces = new ArrayList<>();
        for (Piece p : other.gamePieces) {
            this.gamePieces.add(new Piece(p));
        }
        this.board = other.board;
    }

    public String getStateHash() {
        StringBuilder sb = new StringBuilder("");
        ArrayList<Piece> sortedPieces = new ArrayList<>();
        sortedPieces.sort(Comparator.comparing(Piece::getPieceName));

         for (Piece p : sortedPieces) {
            sb.append(p.getPieceName()).append(":");
            sb.append(p.getRow()).append(",");
            sb.append(p.getCol()).append(";");
        }

        return sb.toString();
    }

    public boolean isGoal() {
        return board.isWinState();
    }

    public List<Move> generateSuccessor() {
        List<Move> possibleMoves = new ArrayList<>();
        for(Piece p : gamePieces) {
            int anchor_row = p.getRow();
            int anchor_col = p.getCol();
            if(p.getisVertical()) {
                if(anchor_row > 0 && board[anchor_row - 1][anchor_col] == null) {
                    PuzzleState newState = new PuzzleState(this);

                }
            }
        }
    } 


}
