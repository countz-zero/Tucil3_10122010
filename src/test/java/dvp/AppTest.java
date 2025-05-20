package dvp;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    // @Test
    // public void fileRead()
    // {
    //     ArrayList<String> correctLine = new ArrayList<>();
    //     Collections.addAll(correctLine, "3 3", "2", " AAA", "KBPP", " B..");
    //     assertEquals(lines, correctLine);
    // }

    @Test
    public void testingA_B() {
        App game = new App();
        ArrayList<String> lines = App.readAllLines("src\\test\\resources\\inputtest.txt");
        game.getPieces(4, 3, 2, new ArrayList<>(lines.subList(2, lines.size())));
        game.board = new Board(4, 3, game.gamePiece, game.exit_location);
        ArrayList<Move> testSuccMoves = game.board.generateSuccessor();
        for (Move m : testSuccMoves) {
            System.out.println(m.getResultState().displayBoard());
        }
    }

}
