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
    void testGetRandomType() {
        int passageCount = 0;
        int nonWallCount = 0;
        int iterations = 10000;

        for (int i = 0; i < iterations; i++) {
            Cell.Type type = Cell.getRandomType();
            if (type == Cell.Type.PASSAGE) {
                passageCount++;
            } else if (type != Cell.Type.WALL) {
                nonWallCount++;
            }
        }

        // Проверка, что PASSAGE возвращается хотя бы в 75% случаев
        assertTrue(passageCount >= iterations * 0.75);
        // Проверка, что остальные (кроме стен, конечно) возвращаются меньше 25% от общего числа
        assertTrue(nonWallCount < iterations * 0.25);
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

