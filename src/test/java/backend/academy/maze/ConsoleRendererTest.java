package backend.academy.maze;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Maze;
import backend.academy.maze.outputConsole.ConsoleRenderer;
import backend.academy.maze.outputConsole.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleRendererTest {
    private Maze maze;
    private ConsoleRenderer renderer;

    @BeforeEach
    void setUp() {
        // Arrange
        maze = new Maze(3, 3);
        renderer = new ConsoleRenderer();
    }

    @Test
    void testRenderWithoutPath() {
        // Arrange
        maze.setCell(0, 0, Cell.Type.WALL);
        maze.setCell(0, 1, Cell.Type.PASSAGE);
        maze.setCell(0, 2, Cell.Type.RAIN);
        maze.setCell(1, 0, Cell.Type.SNOW);
        maze.setCell(1, 1, Cell.Type.LOCKED);
        maze.setCell(1, 2, Cell.Type.MONEY);

        // Act
        String result = renderer.render(maze);

        // Assert
        String expected =
                "███    ⚡ \n" +
                " *  ☦  ₽ \n" +
                "█████████\n";
        assertEquals(expected, result);
    }

    @Test
    void testRenderWithPath() {
        // Arrange
        maze.setCell(0, 0, Cell.Type.WALL);
        maze.setCell(0, 1, Cell.Type.PASSAGE);
        maze.setCell(0, 2, Cell.Type.RAIN);
        maze.setCell(1, 0, Cell.Type.SNOW);
        maze.setCell(1, 1, Cell.Type.LOCKED);
        maze.setCell(1, 2, Cell.Type.MONEY);

        List<Coordinate> path = List.of(
            new Coordinate(0, 1),
            new Coordinate(1, 1)
        );

        // Act
        String result = renderer.render(maze, path);

        // Assert
        String expected =
                "███ o  ⚡ \n" +
                " *  o  ₽ \n" +
                "█████████\n";
        assertEquals(expected, result);
    }

    @Test
    void testRenderWithNullMaze() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> renderer.render(null));
    }

    @Test
    void testRenderWithNullPath() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> renderer.render(maze, null));
    }

    @Test
    void testRenderWithEmptyPath() {
        // Arrange
        maze.setCell(0, 0, Cell.Type.PASSAGE);

        // Act
        String result = renderer.render(maze, List.of());

        // Assert
        String expected = "   ██████\n█████████\n█████████\n";
        assertEquals(expected, result);
    }
}
