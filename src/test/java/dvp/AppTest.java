package dvp;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import dvp.App;
import dvp.utils.*;
/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    /**
     * Rigorous Test :-)
     */

    public static ArrayList<Piece> pieces;

    @Before
    public void setUp() {
        Piece A = new Piece("A", 1, 3, 0, 0);
        Piece B = new Piece("B", 2, 1, 1, 0);
        Piece P = new Piece("P", 2, 1, 1, 1);
        pieces = new ArrayList<Piece>();
        pieces.add(P);
        pieces.add(A);
        pieces.add(B);
    }

    @Test
   public void displayTest() {
        String correctOutput = String.join("\n", "AAA..", "BPP..", "B....", ".....", ".....");
        thrown.expect(IllegalArgumentException.class);
        Board board = new Board(5, 5, pieces, new int[] {2, 1});
        board.displayBoard();
    }

}
