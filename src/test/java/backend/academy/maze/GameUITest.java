package backend.academy.maze;

import backend.academy.maze.outputConsole.Coordinate;
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
    void testRequestMazeSize_ValidInput() {
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
    void testRequestMazeSize_InvalidInput() {
        String input = "2\n4\n5\n7\n"; // Неверные размеры
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        int[] size = gameUI.requestMazeSize();

        assertTrue(getOutput().contains("Размер должен быть нечётным числом в диапазоне от 3 до 151."));
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
    void testChooseMazeGenerator_InvalidInput() {
        String input = "3\n"; // Некорректный выбор
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        Generator generator = gameUI.chooseMazeGenerator();

        assertTrue(generator instanceof DFSGenerator);
        assertTrue(getOutput().contains("Некорректный ввод. По умолчанию выбран DFS."));
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
    void testChooseSolver_InvalidInput() {
        String input = "3\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        Solver solver = gameUI.chooseSolver();

        assertTrue(solver instanceof BFSSolver);
        assertTrue(getOutput().contains("Некорректный ввод. По умолчанию выбран BFS."));
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

    @Test
    void testRequestCoordinates_ValidInput() {
        String input = "2\n3\n3\n4\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        Coordinate[] coordinates = gameUI.requestCoordinates(9, 9);

        assertEquals(2, coordinates[0].row());
        assertEquals(3, coordinates[0].col());
        assertEquals(3, coordinates[1].row());
        assertEquals(4, coordinates[1].col());
    }

    @Test
    void testAddObstacles_YesInput() {
        String input = "1\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        boolean result = gameUI.addObstacles();

        assertTrue(result);
    }

    @Test
    void testAddObstacles_NoInput() {
        String input = "2\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        boolean result = gameUI.addObstacles();

        assertFalse(result);
    }

    @Test
    void testAddObstacles_InvalidInput() {
        String input = "3\n1\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        boolean result = gameUI.addObstacles();

        assertFalse(result);
    }

    @Test
    void testRequestMazeType_ValidInput_Ideal() {
        String input = "1\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        int mazeType = gameUI.requestMazeType();

        assertEquals(1, mazeType);
    }

    @Test
    void testRequestMazeType_ValidInput_NonIdeal() {
        String input = "2\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        int mazeType = gameUI.requestMazeType();

        assertEquals(2, mazeType);
    }

    @Test
    void testRequestMazeType_InvalidInput() {
        String input = "3\n1\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        int mazeType = gameUI.requestMazeType();

        assertEquals(1, mazeType);
    }

    @Test
    void testContinueGame_YesInput() {
        String input = "1\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        boolean continueGame = gameUI.continueGame();

        assertTrue(continueGame);
    }

    @Test
    void testContinueGame_NoInput() {
        String input = "2\n";
        provideInput(input);
        GameUI gameUI = new GameUI(System.out);

        boolean continueGame = gameUI.continueGame();

        assertFalse(continueGame);
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

