package dvp;
import dvp.utils.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.BufferedReader;

/**
 * Hello world!
 *
 */
public class App 
{
    int A = 0, B = 0, N = 0;
    int[] exit_location = {0, 0};
    Board board;
    ArrayList<Piece> gamePiece = new ArrayList<Piece>();
    static final String filePath = "src\\main\\resources\\input.txt";
    

    public static ArrayList<String> readAllLines(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            System.out.println("File input terbaca");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return lines;
    }

    public static int[] getBoardSizeInput(String line) {
        int[] boardSizeInput = new int[2];
        try {
            // Split the line by whitespace or comma (adjust delimiter as needed)
            String[] parts = line.trim().split(" ");
            
            if (parts.length == 2) {
                boardSizeInput[0] = Integer.parseInt(parts[0]);
                boardSizeInput[1] = Integer.parseInt(parts[1]);
            } else {
                throw new IllegalArgumentException("Ada kesalahan di formatting ipnut");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing values in line " + line);
        }
        return boardSizeInput;
    }

    public static int getNumOfPieces(String line) {
        int N = 0;
        try {
            // Split the line by whitespace or comma (adjust delimiter as needed)
            N = Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing values in line " + line);
        }

        return N;
    }

    //TODO focus
    public void getPieces(int A, int B, int N, ArrayList<String> lines) {
        int lines_row = lines.size();
        int lines_col = lines.get(0).length();
        if(!((lines_row == A && lines_col == B + 1) || (lines_row == A + 1 && lines_col == B))) {
            throw new IllegalArgumentException("Input di txt tidak sesuai");
        } else if ((lines_row == A + 1 && lines_col == B)) {
            //AAP
            //BBP
            //...
            //  K

            String firstLine = lines.get(0);
            String lastLine = lines.get(lines.size() - 1);
            if (firstLine.trim().equals("K")) {
                exit_location[0] = 1;
                exit_location[1] = firstLine.indexOf("K") ;
                lines.remove(0);
            } else if (lastLine.trim().equals("K")) {
                exit_location[0] = 3;
                exit_location[1] = lastLine.indexOf("K");
                lines.remove(lines.size() - 1);
            } 

            //AAP
            //BBP
            //...

            Set<String> nameTags = new HashSet<String>();
            String letter = "";
            for(int i = 0; i < A; i++) {
                for(int j = 0; j < B; j++) {
                    System.out.println(Integer.toString(i) + " " + Integer.toString(j));
                    letter = Character.toString(lines.get(i).charAt(j));

                    if(letter.equals(".")) {
                        continue;
                    } else if(!nameTags.contains(letter)) {
                        nameTags.add(letter);
                        Piece piece = new Piece(letter, i, j);
                        gamePiece.add(piece);
                    } else if(nameTags.contains(letter)) {
                        for(Piece piece : gamePiece) {
                            if(piece.getPieceName().equals(letter)) {
                                piece.addPosition(i, j);
                            }
                        }
                    }
                }
            }

        } else if (lines_row == A && lines_col == B + 1) {
            //0123
            //
            //KPPB
            // AAB
            // ...

            Set<String> nameTags = new HashSet<String>();
            String letter = "";
            for(int i = 0; i < A; i++) {
                for(int j = -1; j < B; j++) {
                    letter = Character.toString(lines.get(i).charAt(j+1));
                    if (letter.equals(" ") || letter.equals(".")) {
                        continue;
                    } else if (letter.equals("K") && j == -1) {
                        exit_location[0] = 2;
                        exit_location[1] = i;
                    } else if (letter.equals("K") && j == B - 1) {
                        exit_location[0] = 4;
                        exit_location[1] = i;
                    } else if(!nameTags.contains(letter)) {
                        nameTags.add(letter);
                        Piece piece = new Piece(letter, i, j);
                        gamePiece.add(piece);
                    } else if(nameTags.contains(letter)) {
                        for(Piece piece : gamePiece) {
                            if(piece.getPieceName().equals(letter)) {
                                piece.addPosition(i, j);
                            }
                        }
                    }

                }
            }
        }
    }

    public void solve_board(Board board, String method) {
        if (method == "G") {

        } else if (method == "U") {

        } else if (method == "A") {

        }
    }

    public enum Direction {
        Atas,
        Bawah,
        Kiri,
        Kanan
    }

    public static void main( String[] args ) {
        String method;
        App game = new App();
        ArrayList<String> lines = readAllLines(filePath);
        int[] dimension = getBoardSizeInput(lines.get(0));
        game.A = dimension[0];
        game.B = dimension[1];

        game.N = getNumOfPieces(lines.get(1));
        game.getPieces(game.A, game.B, game.N, new ArrayList<>(lines.subList(3, lines.size())));
        game.board = new Board(game.A, game.B, game.gamePiece, game.exit_location);
        game.board.placePieces(game.gamePiece);
        //game.board.displayBoard();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Pilih algoritma yang ingit digunakan");
        System.out.println("Greedy Best First Search (G) | USC (U) | A-Star (A)");
        String input = scanner.nextLine();
        scanner.close();
    }

    
}
