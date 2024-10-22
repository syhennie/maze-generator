package backend.academy.maze;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Maze;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    @Test
    void testMazeInitialization() {
        // Arrange & Act
        Maze maze = new Maze(5, 5);

        // Assert
        assertEquals(5, maze.height());
        assertEquals(5, maze.width());
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                assertEquals(Cell.Type.WALL, maze.getCell(row, col).type());
            }
        }
    }

    @Test
    void testInvalidMazeInitialization() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Maze(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Maze(5, 0));
    }

    @Test
    void testSetAndGetCell() {
        // Arrange
        Maze maze = new Maze(5, 5);

        // Act
        maze.setCell(2, 2, Cell.Type.PASSAGE);
        Cell cell = maze.getCell(2, 2);

        // Assert
        assertEquals(Cell.Type.PASSAGE, cell.type());
    }

    @Test
    void testSetInvalidCell() {
        // Arrange
        Maze maze = new Maze(5, 5);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> maze.setCell(5, 5, Cell.Type.PASSAGE));
        assertThrows(IllegalArgumentException.class, () -> maze.setCell(2, 2, null));
    }

    @Test
    void testAddObstacles() {
        // Arrange
        Maze maze = new Maze(5, 5);

        // Act
        maze.setCell(2, 2, Cell.Type.PASSAGE);
        maze.addObstacles();

        // Assert
        Cell.Type type = maze.getCell(2, 2).type();
        assertTrue(type == Cell.Type.PASSAGE || type == Cell.Type.SNOW || type == Cell.Type.RAIN ||
            type == Cell.Type.LOCKED || type == Cell.Type.MONEY);
    }

    @Test
    void testAddRandomWalls() {
        // Arrange
        Maze maze = new Maze(10, 10);

        // Act
        maze.setCell(2, 2, Cell.Type.PASSAGE);
        maze.addRandomWalls();

        // Assert
        assertTrue(maze.getCell(2, 2).type() == Cell.Type.WALL || maze.getCell(2, 2).type() == Cell.Type.PASSAGE);
    }

    @Test
    void testIsValidCell() {
        // Arrange
        Maze maze = new Maze(5, 5);

        // Act & Assert
        assertTrue(maze.isValidCell(0, 0));
        assertFalse(maze.isValidCell(-1, 0));
        assertFalse(maze.isValidCell(5, 0));
    }

    @Test
    void testGetCellWithInvalidCoordinates() {
        // Arrange
        Maze maze = new Maze(5, 5);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> maze.getCell(5, 5));
        assertThrows(IllegalArgumentException.class, () -> maze.getCell(-1, 0));
    }
}
