package org.example;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        System.out.println("Welcome to Minesweeper!\n");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            MineSweeper mineSweeper = setupGame(scanner);
            playGame(scanner, mineSweeper);
            System.out.println("Press any key to play again...");
            scanner.nextLine();
        }
    }

    private static void playGame(Scanner scanner, MineSweeper mineSweeper) {
        while (true) {
            System.out.print("\nSelect a square to reveal (e.g. A1):");
            String input = scanner.nextLine().toUpperCase().trim();

            if (!input.matches("^[A-Z]\\d+$")) {
                System.out.println("Invalid input. Format must be like A1.");
                continue;
            }

            int row = input.charAt(0) - 'A';
            int column = Integer.parseInt(input.substring(1)) - 1;

            if (!mineSweeper.isValidPosition(row, column)) {
                System.out.println("invalid position");
                continue;
            }

            if (mineSweeper.actualBoard[row][column] == MineSweeper.MINE) {
                System.out.println("Oh no, you detonated a mine! Game over.");
                return;
            }

            mineSweeper.revealSquare(row, column);
            System.out.println("\nHere is your updated minefield:");
            mineSweeper.printBoard();

            if (mineSweeper.revealedCount == mineSweeper.size * mineSweeper.size - mineSweeper.totalMines) {
                System.out.println("Congratulations, you have won the game!");
                return;
            }
        }
    }

    private static MineSweeper setupGame(Scanner scanner) {
        System.out.println("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
        int size = Integer.parseInt(scanner.nextLine());
        MineSweeper mineSweeper = new MineSweeper(size);
        System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
        int totalMines = Integer.parseInt(scanner.nextLine());
        mineSweeper.placeMines(totalMines);
        mineSweeper.calculateAdjacentMineCounts();
        System.out.println("\nHere is your minefield:");
        mineSweeper.printBoard();

        return mineSweeper;
    }


}
