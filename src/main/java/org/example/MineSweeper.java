package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MineSweeper {
    public static final char MINE = '*';
    public static final char UNREVEALED = '_';

    // all the variables below can be set to private with getters and setters
    public char[][] visibleBoard;
    public char[][] actualBoard;
    public boolean[][] revealed;
    public int size;
    public int totalMines;
    public int revealedCount;

    public MineSweeper(int size) {
        this.size = size;
        visibleBoard = new char[size][size];
        actualBoard = new char[size][size];
        revealed = new boolean[size][size];
        revealedCount = 0;

        for (int i = 0; i < size; i++) {
            Arrays.fill(visibleBoard[i], UNREVEALED);
            Arrays.fill(actualBoard[i], '0');
        }
    }

    public void placeMines(int totalMines) {
        this.totalMines = totalMines;
        Random rand = new Random();
        int placed = 0;
        while (placed < totalMines) {
            int r = rand.nextInt(size);
            int c = rand.nextInt(size);
            if (actualBoard[r][c] != MINE) {
                actualBoard[r][c] = MINE;
                placed++;
            }
        }
    }

    /**
     * Calculate the number of mines surrounding each cell in all 8 possible directions
     */
    public void calculateAdjacentMineCounts() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (actualBoard[r][c] == MINE) continue;
                int count = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int nr = r + i;
                        int nc = c + j;
                        if (isValidPosition(nr, nc) && actualBoard[nr][nc] == MINE) {
                            count++;
                        }
                    }
                }
                actualBoard[r][c] = (char) ('0' + count);
            }
        }
    }

    /**
     * This function will reveal the number of mines surrounding the cell from all 8 possible directions
     * if the value is 0, use breadth-first search and expand from this cell
     * @param row
     * @param column
     */
    public void revealSquare(int row, int column) {
        revealed[row][column] = true;
        revealedCount++;
        char value = actualBoard[row][column];
        visibleBoard[row][column] = value;

        if (value == '0') {
            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[]{row, column});

            while (!queue.isEmpty()) {
                int currentSize = queue.size();
                for (int k = 0; k < currentSize; k++) {
                    int[] currentPosition = queue.poll();
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int surrounding_row = currentPosition[0] + i;
                            int surrounding_column = currentPosition[1] + j;
                            if (isValidPosition(surrounding_row, surrounding_column) && !revealed[surrounding_row][surrounding_column]) {
                                revealed[surrounding_row][surrounding_column] = true;
                                revealedCount++;
                                visibleBoard[surrounding_row][surrounding_column] = actualBoard[surrounding_row][surrounding_column];
                                if (actualBoard[surrounding_row][surrounding_column] == '0') {
                                    queue.add(new int[]{surrounding_row, surrounding_column});
                                }
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("This square contains " + (value - '0') + " adjacent mines.");
        }
    }

    public boolean isValidPosition(int row, int column) {
        return row >= 0 && row < size && column >= 0 && column < size;
    }

    public void printBoard() {
        System.out.print("  ");
        for (int c = 1; c <= size; c++) {
            System.out.print(c + " ");
        }
        System.out.println();
        for (int r = 0; r < size; r++) {
            char rowLabel = (char) ('A' + r);
            System.out.print(rowLabel + " ");
            for (int c = 0; c < size; c++) {
                System.out.print(visibleBoard[r][c] + " ");
            }
            System.out.println();
        }
    }
}
