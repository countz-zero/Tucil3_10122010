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

    @Test
    public void pieceValid() {
        thrown.expect(IllegalArgumentException.class);
        Piece A = new Piece("A", 3, 3, 1, 2);
    }

    @Test
    public void pieceValid2() {
        thrown.expect(IllegalArgumentException.class);
        Piece A = new Piece("K", 1, 3, 1, 2);
    }

}
