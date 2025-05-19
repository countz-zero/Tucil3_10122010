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
        ArrayList<String> lines = App.readAllLines(App.filePath);
        int[] game.dimension = App.getBoardSizeInput(lines.get(0));
        game.A = game.dimension[0];
        game.B = game.dimension[1];

        assertArrayEquals(new int [] {4, 3}, game.dimension);
    }

}
