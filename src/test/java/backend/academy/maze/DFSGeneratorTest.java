package backend.academy.maze;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Maze;
import backend.academy.maze.generatorMaze.algorithms.DFSGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DFSGeneratorTest {
    private final DFSGenerator generator = new DFSGenerator();

    @Test
    void testGenerate_ValidDimensions() {
        int height = 7;
        int width = 7;
        Maze maze = generator.generate(height, width);

        // Проверяем, что размеры лабиринта совпадают
        assertEquals(height, maze.height());
        assertEquals(width, maze.width());
    }

    @Test
    void testGenerate_AllPassagesVisited() {
        int height = 7;
        int width = 7;
        Maze maze = generator.generate(height, width);

        // Проверяем, что все посещенные ячейки имеют тип PASSAGE
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell.Type cellType = maze.getCell(row, col).type();
                if (cellType != Cell.Type.WALL) {
                    assertEquals(Cell.Type.PASSAGE, cellType);
                }
            }
        }
    }

    @Test
    void testGenerate_RemainsWalls() {
        int height = 7;
        int width = 7;
        Maze maze = generator.generate(height, width);

        // Проверяем, что стену можно найти в лабиринте
        int wallCount = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    wallCount++;
                }
            }
        }

        // Лабиринт должен содержать хотя бы некоторые стены
        assertTrue(wallCount > 0);
    }

    @Test
    void testGenerate_SmallMaze() {
        int height = 5;
        int width = 5;
        Maze maze = generator.generate(height, width);

        // Проверяем, что размер лабиринта правильный и содержимое верное
        assertEquals(height, maze.height());
        assertEquals(width, maze.width());

        // Проверяем, что не все клетки являются проходами
        int passageCount = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
                    passageCount++;
                }
            }
        }

        // В маленьком лабиринте будет больше стен, чем проходов
        assertTrue(passageCount < (height * width) / 2);
    }

    @Test
    void testGenerate_ClosedMaze() {
        int height = 9;
        int width = 9;
        Maze maze = generator.generate(height, width);

        // Проверяем, что все проходы находятся в пределах лабиринта
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell.Type cellType = maze.getCell(row, col).type();
                if (cellType == Cell.Type.PASSAGE) {
                    assertTrue(maze.isValidCell(row, col), "Клетка выходит за пределы лабиринта: " + row + ", " + col);
                }
            }
        }
    }

    @Test
    void testGenerate_MinimalSize() {
        int height = 3;
        int width = 3;
        Maze maze = generator.generate(height, width);

        // Проверяем, что лабиринт корректно сгенерирован
        assertEquals(height, maze.height());
        assertEquals(width, maze.width());
        assertEquals(Cell.Type.PASSAGE, maze.getCell(1, 1).type());
    }

    @Test
    void testGenerate_LargeMaze() {
        int height = 151;
        int width = 151;
        Maze maze = generator.generate(height, width);

        // Проверяем, что размеры лабиринта совпадают
        assertEquals(height, maze.height());
        assertEquals(width, maze.width());

        // Проверяем, что лабиринт не состоит только из проходов
        int passageCount = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
                    passageCount++;
                }
            }
        }
        assertTrue(passageCount < (height * width), "Лабиринт состоит только из проходов");
    }

}
