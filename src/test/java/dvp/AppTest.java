package dvp;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
    int A = 0, B = 0, N = 0;
    int[] exit_location = {0, 0};
    Board board;
    ArrayList<Piece> gamePiece = new ArrayList<Piece>();
    static final String filePath = "src\\test\\resources\\inputtest.txt";
    ArrayList<String> lines;

    @Before
    public void setUp() {
        lines = App.readAllLines(filePath);
    }

    // @Test
    // public void fileRead()
    // {
    //     ArrayList<String> correctLine = new ArrayList<>();
    //     Collections.addAll(correctLine, "3 3", "2", " AAA", "KBPP", " B..");
    //     assertEquals(lines, correctLine);
    // }

    @Test
    public void testingGetBoardSizeInput() {
        int[] correctSize = {3, 3};
        assertArrayEquals(correctSize, App.getBoardSizeInput(lines.get(0)));
    }

    @Test
    public void testingDimension() {
        int correctN = 2;
        assertEquals(correctN, App.getNumOfPieces(lines.get(1)));
    }

    // @Ignore
    // @Test
    // public void testingPieces1() {
    //     App game = new App();

    //     game.getPieces(3, 3, 2, new ArrayList<>(lines.subList(2, lines.size())));
        
    //     assertArrayEquals(new int[] {2, 1}, game.exit_location);
    // }

    @Test
    public void testingPieces2() {
        App game = new App();

        game.getPieces(3, 3, 2, new ArrayList<>(lines.subList(2, lines.size())));
        
        assertArrayEquals(new int[] {3, 1}, game.exit_location);
    }

}
