import javax.swing.JFrame;
import java.awt.GridLayout;
import java.util.Random;
import java.util.Scanner;

/**
 * Main application class
 * 
 * The purpose of this project is to replicate John Conway's Game of Life
 * 
 * The Game of Life is an example of a cellular Automaton and was
 * first devised by British mathematician John Horton Conway in 1970.
 * 
 * It follows four basic rules which govern its future state;
 *      1. Any live cells with fewer than two live neighbours dies, as if by underpopulation
 *      2. Any live cell with two or three live neightbours lives on to the next generation
 *      3. Any live cell with more than three live neighbours dies, as if by overpopulation
 *      4. Any dead cell with exactly 3 live neighbours becomes a live cell, as if by reproduction
 * 
 * @author Senuda Lokuruge, 2023. email: lokuruges@gmail.com
 */

public class Main {
    public static void main(String[] args) {
        //JFrame appWindow = new JFrame();
        Random rndm = new Random(27);
        Scanner scnr = new Scanner(System.in);
        //final int WINDOW_WIDTH = 500;
        //final int WINDOW_HEIGHT = 500;
        
        final int GRID_COLUMNS = 32;
        final int GRID_ROWS = 32;

        //initial configuration for the app window
        //appWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        //appWindow.setTitle("Conway's Game of Life");
        //appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //appWindow.setLayout(new GridLayout(GRID_ROWS, GRID_COLUMNS));

        int[][][] grid = new int[GRID_COLUMNS][GRID_ROWS][3];
        
        //int cellWidth = WINDOW_WIDTH / GRID_COLUMNS;
        //int cellHeight = WINDOW_HEIGHT / GRID_ROWS;

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y][0] = rndm.nextInt(2);
                grid[x][y][1] = 0;
                if (grid[x][y][0] == 1) {
                        System.out.print(" 1 ");
                    }
                    else {
                        System.out.print(" - ");
                    }
            }
            System.out.println();
        }

        char key;
        System.out.print("Proceed to next generation? (Y/N): ");
        key = scnr.next().charAt(0);

        while (key == 'Y' || key == 'y') {
            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[x].length; y++) {
                    if (y > 0 && x > 0 && grid[x-1][y-1][0] == 1) {
                        grid[x][y][1] += 1;
                    }
                    if (y > 0 && grid[x][y-1][0] == 1) {
                        grid[x][y][1] += 1;
                    }
                    if (y > 0 && x < grid.length - 1 && grid[x+1][y-1][0] == 1) {
                        grid[x][y][1] += 1;
                    }
                    if (x > 0 && grid[x-1][y][0] == 1) {
                        grid[x][y][1] += 1;
                    }
                    if (x < grid.length - 1 && grid[x+1][y][0] == 1) {
                        grid[x][y][1] += 1;
                    }
                    if (x > 0 && y < grid[x].length - 1 && grid[x-1][y+1][0] == 1) {
                        grid[x][y][1] += 1;
                    }
                    if (y < grid[x].length - 1 && grid[x][y+1][0] == 1) {
                        grid[x][y][1] += 1;
                    }
                    if (x < grid.length - 1 && y < grid[x].length - 1 && grid[x+1][y+1][0] == 1) {
                        grid[x][y][1] += 1;
                    }

                    if (grid[x][y][0] == 1 && grid[x][y][1] < 2) {
                        grid[x][y][2] = 0;
                    }
                    if (grid[x][y][0] == 1 && grid[x][y][1] > 1 && grid[x][y][1] < 4) {
                        grid[x][y][2] = 1;
                    }
                    if (grid[x][y][0] == 1 && grid[x][y][1] > 3) {
                        grid[x][y][2] = 0;
                    }
                    if (grid[x][y][0] == 0 && grid[x][y][1] == 3) {
                        grid[x][y][2] = 1;
                    }

                    if (grid[x][y][2] == 1) {
                        System.out.print(" 1 ");
                    }
                    else {
                        System.out.print(" - ");
                    }
                }
                System.out.println();
            }

            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[x].length; y++) {
                    grid[x][y][0] = grid[x][y][2];
                    grid[x][y][1] = 0;   
                }
            }

            System.out.print("Proceed to next generation? (Y/N): ");
            key = scnr.next().charAt(0);    
        }
        scnr.close();
    } 
}