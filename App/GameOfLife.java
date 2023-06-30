package App;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Random;

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

public class GameOfLife {
    private static int playPause = 0;
    private static int editCells = 1;

    private static void playPauseModel(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("play") && playPause == 0) {
            playPause = 1;
        }
        else if (cmd.equals("pause") && playPause == 1) {
            playPause = 0;
        }
    }

    private static void toggleCellState(ActionEvent e, int[][][] grid, JButton cellOverlay[][]) {
        if (editCells == 1) {
            String index = e.getActionCommand();
            int x = Integer.parseInt(index.substring(0,index.indexOf(".")));
            int y = Integer.parseInt(index.substring(index.indexOf(".") + 1));
            grid[x][y][0] = (grid[x][y][0] + 1) % 2;
            if (grid[x][y][0] == 1) {
                cellOverlay[x][y].setBackground(new Color(50, 168, 82));
            }
            else {
                cellOverlay[x][y].setBackground(new Color(250, 250, 250));
            }
        }
    }

    public static void main(String[] args) {
        JFrame appWindow = new JFrame();
        JPanel controlPanel = new JPanel();
        JPanel gridPanel = new JPanel();
        Random rndm = new Random();
        
        final int CONTROL_PANEL_HEIGHT = 60;
        final int GRID_COLUMNS = 64;
        final int GRID_ROWS = 64;
        final int GRID_WIDTH = GRID_COLUMNS * 10;
        final int GRID_HEIGHT = GRID_ROWS * 10;
        final int WINDOW_WIDTH = GRID_WIDTH + (GRID_COLUMNS / 4);
        final int WINDOW_HEIGHT = GRID_HEIGHT + (int) ((GRID_ROWS / 4) * 2.5) + 60;

        //initial configuration for the app window
        appWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        appWindow.setTitle("Conway's Game of Life");
        appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appWindow.setResizable(false);
        appWindow.setLayout(null);

        controlPanel.setSize(WINDOW_WIDTH, CONTROL_PANEL_HEIGHT);
        controlPanel.setLayout(null);
        controlPanel.setLocation(0, 0);
        
        ImageIcon playIcon = new ImageIcon("Resources/Images/play.png", "play button icon");
        Image playIconImage = playIcon.getImage();
        playIcon.setImage(playIconImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        JButton playButton = new JButton(playIcon);
        playButton.setActionCommand("play");
        playButton.addActionListener(e -> playPauseModel(e));
        playButton.setBounds(10, 5, 50, 50);
        playButton.setFocusPainted(false);
        playButton.setOpaque(true);
        playButton.setBackground(new Color(250, 250, 250));
        controlPanel.add(playButton);

        ImageIcon pauseIcon = new ImageIcon("Resources/Images/pause.png", "pause button icon");
        Image pauseIconImage = pauseIcon.getImage();
        pauseIcon.setImage(pauseIconImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        JButton pauseButton = new JButton(pauseIcon);
        pauseButton.setActionCommand("pause");
        pauseButton.addActionListener(e -> playPauseModel(e));
        pauseButton.setBounds(70, 5, 50, 50);
        pauseButton.setFocusPainted(false);
        pauseButton.setOpaque(true);
        pauseButton.setBackground(new Color(250, 250, 250));
        controlPanel.add(pauseButton);

        JLabel runInstructions = new JLabel("Press play button to play the simulation and pause button to pause the simulation");
        runInstructions.setSize(runInstructions.getPreferredSize());
        runInstructions.setLocation(130, 5);
        controlPanel.add(runInstructions);

        JLabel cellInstructions = new JLabel("Click on a cell when simulation is paused to toggle state");
        cellInstructions.setSize(cellInstructions.getPreferredSize());
        cellInstructions.setLocation(130, runInstructions.getY() + runInstructions.getHeight() + 5);
        controlPanel.add(cellInstructions);

        gridPanel.setSize(GRID_WIDTH, GRID_HEIGHT);
        gridPanel.setLayout(new GridLayout(GRID_ROWS, GRID_COLUMNS, 1, 1));
        gridPanel.setLocation(0, CONTROL_PANEL_HEIGHT);

        int[][][] grid = new int[GRID_COLUMNS][GRID_ROWS][3];
        JButton[][] cellOverlay = new JButton[GRID_COLUMNS][GRID_ROWS];

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y][0] = rndm.nextInt(2);
                grid[x][y][1] = 0;
                JButton cell = new JButton(null, null);
                cell.setOpaque(true);
                cell.setBorderPainted(false);
                cell.setSize(GRID_HEIGHT / GRID_COLUMNS, GRID_WIDTH / GRID_ROWS);
                if (grid[x][y][0] == 1) {
                    cell.setBackground(new Color(50, 168, 82));
                }
                else {
                    cell.setBackground(new Color(250, 250, 250));
                }
                cell.setActionCommand(x + "." + y);
                cell.addActionListener(e -> toggleCellState(e, grid, cellOverlay));
                cellOverlay[x][y] = cell;
                gridPanel.add(cellOverlay[x][y]);
            }
        }
        appWindow.add(gridPanel);
        appWindow.add(controlPanel);
        appWindow.setVisible(true);
        long start = System.nanoTime();
        long end;
        boolean refresh = true;

        while (true) {
            System.out.println();
            if ((playPause == 1) && refresh) {
                playButton.setBackground(new Color(50, 168, 82));
                pauseButton.setBackground(new Color(250, 250, 250));
                editCells = 0;
                refresh = false;
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
                            cellOverlay[x][y].setBackground(new Color(50, 168, 82));
                        }
                        else {
                            cellOverlay[x][y].setBackground(new Color(250, 250, 250));
                        }
                    }
                }

                for (int x = 0; x < grid.length; x++) {
                    for (int y = 0; y < grid[x].length; y++) {
                        grid[x][y][0] = grid[x][y][2];
                        grid[x][y][1] = 0;   
                    }
                }
            }
            else if (playPause == 1) {
                playButton.setBackground(new Color(50, 168, 82));
                pauseButton.setBackground(new Color(250, 250, 250));
            }
            else if (playPause == 0) {
                playButton.setBackground(new Color(250, 250, 250));
                pauseButton.setBackground(new Color(50, 168, 82));
                editCells = 1;
            }

            end = System.nanoTime();
            if ((end - start)/1000000 > 100) {
                start = end;
                refresh = true;
            }
        }
    } 
}