package backend.academy.maze;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import backend.academy.maze.generatorMaze.Cell;

class CellTest {

    @Test
    void testGetWeight_Passage() {
        // Arrange
        Cell cell = new Cell(0, 0, Cell.Type.PASSAGE);

        // Act
        int weight = cell.getWeight();

        // Assert
        assertEquals(1, weight);
    }

    @Test
    void testGetWeight_Snow() {
        Cell cell = new Cell(0, 0, Cell.Type.SNOW);
        assertEquals(4, cell.getWeight());
    }

    @Test
    void testGetWeight_Rain() {
        Cell cell = new Cell(0, 0, Cell.Type.RAIN);
        assertEquals(2, cell.getWeight());
    }

    @Test
    void testGetWeight_Locked() {
        Cell cell = new Cell(0, 0, Cell.Type.LOCKED);
        assertEquals(1_000_000, cell.getWeight());
    }

    @Test
    void testGetWeight_Money() {
        Cell cell = new Cell(0, 0, Cell.Type.MONEY);
        assertEquals(0, cell.getWeight());
    }

    @Test
    void testGetWeight_Wall() {
        Cell cell = new Cell(0, 0, Cell.Type.WALL);
        assertEquals(Integer.MAX_VALUE, cell.getWeight());
    }

    @Test
    void testGetRandomType_PassageDominates() {
        // Act
        int passageCount = 0;
        int totalRuns = 100;

        for (int i = 0; i < totalRuns; i++) {
            if (Cell.getRandomType() == Cell.Type.PASSAGE) {
                passageCount++;
            }
        }

        // Assert
        assertTrue(passageCount > totalRuns * 0.7 && passageCount < totalRuns * 0.8);
    }

    @Test
    void testGetRandomType_NonWall() {
        // Act
        boolean nonWallTypeExists = false;
        for (int i = 0; i < 1000; i++) {
            Cell.Type randomType = Cell.getRandomType();
            if (randomType != Cell.Type.PASSAGE) {
                nonWallTypeExists = true;
                break;
            }
        }

        // Assert
        assertTrue(nonWallTypeExists);
    }
}

