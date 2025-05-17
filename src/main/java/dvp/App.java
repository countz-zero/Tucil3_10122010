package dvp;
import dvp.utils.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.BufferedReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        int A = 0, B = 0, N = 0;
        int[] exit_location = new int[2];
        Board board;
        ArrayList<Piece> gamePiece = new ArrayList<Piece>();

        String filePath = "src/main/resources/input.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line1 = reader.readLine();
            if(line1 != null) {
                String[] parts = line1.split(" ");
                if(parts.length == 2) {
                    A = Integer.parseInt(parts[0]);
                    B = Integer.parseInt(parts[1]);
                } else {
                    throw new InvalidParameterException("Input dimensi papan tidak valid");
                }
            }

            String line2 = reader.readLine();
            if(line2 != null) {
                N = Integer.parseInt(line2);
            }

            String line;
            for (int i = 0; i <= A; i++) {
                line = reader.readLine();
                if(line.length() != B) {
                    throw new InvalidParameterException("Ada baris yang tidak sesuai panjang papan");
                }
                //TODO Strategy to read string?
            } 

    } catch (FileNotFoundException e) {
        e.getMessage();
    } catch (IOException e) {
            e.getMessage();
    } 
}
}
