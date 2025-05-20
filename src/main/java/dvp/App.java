package dvp;
import dvp.utils.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
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
    int[] dimension = {0, 0};
    Board board;
    ArrayList<Piece> gamePiece = new ArrayList<Piece>();
    static final String filePath = "test\\input.txt";
    String method;
    int nodeCount;
    

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
                throw new IllegalArgumentException("Ada kesalahan di formatting input");
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
                    // System.out.println(Integer.toString(i) + " " + Integer.toString(j));
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

    public List<SearchNode> solve(Board initialState, String method) {
        PriorityQueue<SearchNode> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();
        
        SearchNode startNode = new SearchNode(initialState, method);

        openSet.add(startNode);
        while(!openSet.isEmpty()) {
            SearchNode current = openSet.poll();
            nodeCount++;

            String boardStr = current.getState().displayBoard();

            if(closedSet.contains(boardStr)) {
                continue;
            }

            if(current.getState().isWinState()) {
                System.out.println("Win!");
                return reconstructPath(current);
            }

            closedSet.add(boardStr);

            for(Move move : current.getState().generateSuccessor()) {
                String nextBoardStr = move.getResultState().displayBoard();
                if (!closedSet.contains(nextBoardStr)) {
                    SearchNode nextNode = new SearchNode(move.getResultState(), current, move.getMoveDesc());
                    openSet.add(nextNode);
                }
            }
        }

        return new ArrayList<>();
    }

    public static List<SearchNode> reconstructPath(SearchNode goalNode) {
        List<SearchNode> path = new ArrayList<>();
        SearchNode current = goalNode;
        
        while (current != null) {
            path.add(current);
            current = current.getParent();
        }
        
        Collections.reverse(path);
        return path;
    }

    public static String printSolution(List<SearchNode> solution) {
        StringBuilder sb = new StringBuilder("");
        if (solution.isEmpty()) {
            sb.append("Tidak ada solusi yang ditemukan!");
        } else {
            System.out.println("Solusi ditemukan pada " + (solution.size() - 1) + " gerakan:");
            
            for (int i = 0; i < solution.size(); i++) {
                SearchNode node = solution.get(i);
                if (i > 0) {
                    sb.append("Gerakan " + i + ": " + node.getMoveDesc() + "\n");
                }

                String piece_name = Character.toString(node.getMoveDesc().charAt(0));
                sb.append(node.getState().displayBoard(piece_name));
            }
        }

        return sb.toString();
    }

    public static void main( String[] args ) {
        App game = new App();
        ArrayList<String> lines = readAllLines(filePath);
        int[] dimension = getBoardSizeInput(lines.get(0));
        game.dimension = dimension;
        game.A = dimension[0];
        game.B = dimension[1];

        game.N = getNumOfPieces(lines.get(1));
        game.getPieces(game.A, game.B, game.N, new ArrayList<>(lines.subList(2, lines.size())));
        game.board = new Board(game.A, game.B, game.gamePiece, game.exit_location);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Pilih algoritma yang ingit digunakan");
        System.out.println("Greedy Best First Search (G) | USC (U) | A-Star (A)");
        game.method = scanner.nextLine();
        scanner.close();

        System.out.println("Initial state:");
        System.out.println(game.board.displayBoard());
        
        // Solve the puzzle
        long startTime = System.currentTimeMillis();
        List<SearchNode> solution = game.solve(game.board, game.method);
        long endTime = System.currentTimeMillis();

        long timeElapsed = endTime - startTime;
        System.out.println(printSolution(solution));
        System.out.println("Waktu yang dibutuhkan : " + timeElapsed + " ms\n");
        System.out.println("Banyak simpul yang dikunjungi : " + game.nodeCount);
        
        // if (solution.isEmpty()) {
        //     System.out.println("No solution found!");
        // } else {
        //     System.out.println("Solution found in " + (solution.size() - 1) + " moves:");
            
        //     for (int i = 0; i < solution.size(); i++) {
        //         SearchNode node = solution.get(i);
        //         if (i > 0) {
        //             System.out.println("Step " + i + ": " + node.getMoveDesc());
        //         }
        //         node.getState().displayBoard();
        //     }
        // }

        try {
            FileWriter writer = new FileWriter("test\\output.txt");
            writer.write(printSolution(solution) + "\n");
            writer.write("Waktu yang dibutuhkan : " + timeElapsed + " ms\n");
            writer.write("Banyak simpul yang dikunjungi : " + game.nodeCount);
            writer.close(); // Always close the writer
            System.out.println("Solusi ditulis ke solution.txt di folder test");
        } catch (IOException e) {
            System.out.println("Ada error menulis solusi ke file text.");
            e.printStackTrace();
        }
    }
}
