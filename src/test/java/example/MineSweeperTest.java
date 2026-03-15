package example;

import org.example.MineSweeper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MineSweeperTest {
    @Test
    public void constructorTest() {
        int size = 5;
        MineSweeper mineSweeper = new MineSweeper(size);
        char[][] visibleBoard = mineSweeper.visibleBoard;
        Assertions.assertEquals(size, visibleBoard.length);
        Assertions.assertEquals(size, visibleBoard[0].length);
        for (int i = 0; i < visibleBoard.length; i++) {
            for (int j = 0; j < visibleBoard[0].length; j++) {
                Assertions.assertEquals('_', visibleBoard[i][j]);
            }
        }
    }

    @Test
    public void placeMinesTest() {
        MineSweeper mineSweeper = new MineSweeper(4);
        int totalMines = 3;
        mineSweeper.placeMines(totalMines);

        int count = 0;
        char[][] actualBoard = mineSweeper.actualBoard;
        for (int i = 0; i < actualBoard.length; i++) {
            for (int j = 0; j < actualBoard[0].length; j++) {
                if (actualBoard[i][j] == MineSweeper.MINE) {
                    count++;
                }
            }
        }
        Assertions.assertEquals(totalMines, count);
    }

    @Test
    public void calculateAdjacentMineCountsTest() {
        char[][] expected = {
                {'2', '*', '2', '0'},
                {'3', '*', '2', '0'},
                {'*', '2', '1', '0'},
                {'1', '1', '0', '0'}
        };

        char[][] actual = {
                {'0', '*', '0', '0'},
                {'0', '*', '0', '0'},
                {'*', '0', '0', '0'},
                {'0', '0', '0', '0'},
        };

        MineSweeper mineSweeper = new MineSweeper(4);
        mineSweeper.actualBoard = actual;
        mineSweeper.calculateAdjacentMineCounts();

        Assertions.assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test
    public void revealSquareTestWithExpansion() {
        MineSweeper mineSweeper = new MineSweeper(4);
        char[][] actualBoard = {
                {'2', '*', '2', '0'},
                {'3', '*', '2', '0'},
                {'*', '2', '1', '0'},
                {'1', '1', '0', '0'}
        };
        mineSweeper.actualBoard = actualBoard;

        char[][] expected = {
                {'_', '_', '2', '0'},
                {'_', '_', '2', '0'},
                {'_', '2', '1', '0'},
                {'_', '1', '0', '0'}
        };
        mineSweeper.revealSquare(3, 3);

        Assertions.assertTrue(Arrays.deepEquals(expected, mineSweeper.visibleBoard));
    }

    @Test
    public void revealSquareTestWithoutExpansion() {
        MineSweeper mineSweeper = new MineSweeper(4);
        char[][] actualBoard = {
                {'2', '*', '2', '0'},
                {'3', '*', '2', '0'},
                {'*', '2', '1', '0'},
                {'1', '1', '0', '0'}
        };
        mineSweeper.actualBoard = actualBoard;

        char[][] expected = {
                {'_', '_', '2', '0'},
                {'3', '_', '2', '0'},
                {'_', '2', '1', '0'},
                {'_', '1', '0', '0'}
        };
        mineSweeper.revealSquare(3, 3);
        mineSweeper.revealSquare(1, 0);

        Assertions.assertTrue(Arrays.deepEquals(expected, mineSweeper.visibleBoard));
    }
}
