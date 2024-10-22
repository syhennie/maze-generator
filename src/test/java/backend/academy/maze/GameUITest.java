package backend.academy.maze;

import backend.academy.maze.outputConsole.GameUI;
import backend.academy.maze.generatorMaze.Generator;
import backend.academy.maze.generatorMaze.algorithms.DFSGenerator;
import backend.academy.maze.generatorMaze.algorithms.PrimGenerator;
import backend.academy.maze.searchWays.Solver;
import backend.academy.maze.searchWays.algorithms.AStarSolver;
import backend.academy.maze.searchWays.algorithms.BFSSolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GameUITest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    void testRequestMazeSize() {
        // Arrange
        String input = "5\n5\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        // Act
        int[] mazeSize = gameUI.requestMazeSize();

        // Assert
        assertArrayEquals(new int[]{5, 5}, mazeSize);
        assertTrue(getOutput().contains("Введите высоту лабиринта"));
        assertTrue(getOutput().contains("Введите ширину лабиринта"));
    }

    @Test
    void testChooseMazeGenerator_Prim() {
        // Arrange
        String input = "1\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        // Act
        Generator generator = gameUI.chooseMazeGenerator();

        // Assert
        assertTrue(generator instanceof PrimGenerator);
    }

    @Test
    void testChooseMazeGenerator_DFS() {
        // Arrange
        String input = "2\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        // Act
        Generator generator = gameUI.chooseMazeGenerator();

        // Assert
        assertTrue(generator instanceof DFSGenerator);
    }

    @Test
    void testChooseSolver_BFS() {
        // Arrange
        String input = "1\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        // Act
        Solver solver = gameUI.chooseSolver();

        // Assert
        assertTrue(solver instanceof BFSSolver);
    }

    @Test
    void testChooseSolver_AStar() {
        // Arrange
        String input = "2\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        // Act
        Solver solver = gameUI.chooseSolver();

        // Assert
        assertTrue(solver instanceof AStarSolver);
    }

    @Test
    void testAddObstacles_Yes() {
        // Arrange
        String input = "1\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        // Act
        boolean addObstacles = gameUI.addObstacles();

        // Assert
        assertTrue(addObstacles);
    }

    @Test
    void testAddObstacles_No() {
        // Arrange
        String input = "2\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        // Act
        boolean addObstacles = gameUI.addObstacles();

        // Assert
        assertFalse(addObstacles);
    }

    // Метод для эмуляции ввода
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    // Метод для получения вывода
    private String getOutput() {
        return testOut.toString();
    }
}

