package backend.academy.maze;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Maze;
import backend.academy.maze.generatorMaze.algorithms.PrimGenerator;
import backend.academy.maze.outputConsole.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PrimGeneratorTest {
    private final PrimGenerator generator = new PrimGenerator();
    private static final int height = 9;
    private static final int width = 17;

    @Test void testGenerate_CorrectSize() {
        Maze maze = generator.generate(height, width);

        assertEquals(height, maze.height());
        assertEquals(width, maze.width());
    }

    @Test void testGenerate_AllCellsInBounds() {
        Maze maze = generator.generate(height, width);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                assertTrue(maze.isValidCell(row, col), "Клетка выходит за пределы лабиринта: " + row + ", " + col);
            }
        }
    }

    @Test void testGenerate_AtLeastOnePassage() {
        Maze maze = generator.generate(height, width);

        int passageCount = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
                    passageCount++;
                }
            }
        }
        assertTrue(passageCount > 0, "Лабиринт не содержит проходов");
    }

    @Test void testGenerate_WallsAndPassages() {
        Maze maze = generator.generate(height, width);

        Set<Coordinate> walls = new HashSet<>();
        Set<Coordinate> passages = new HashSet<>();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    walls.add(new Coordinate(row, col));
                } else {
                    passages.add(new Coordinate(row, col));
                }
            }
        }

        assertTrue(!walls.isEmpty(), "Лабиринт не содержит стен");
        assertTrue(!passages.isEmpty(), "Лабиринт не содержит проходов");
    }

    @Test void testRandomStartCoordinate_IsOdd() {
        PrimGenerator generator = new PrimGenerator();
        int height = 9;
        int width = 9;

        Coordinate start = generator.getRandomStartCoordinate(height, width);

        assertTrue(start.row() % 2 != 0, "Стартовая клетка нечётная");
        assertTrue(start.col() % 2 != 0, "Стартовая клетка нечётная");
    }

    @Test void testGenerate_PassagesLessThanWalls() {
        PrimGenerator generator = new PrimGenerator();
        int height = 15;
        int width = 15;

        Maze maze = generator.generate(height, width);

        int passageCount = 0;
        int wallCount = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
                    passageCount++;
                } else {
                    wallCount++;
                }
            }
        }

        assertTrue(passageCount < wallCount);
    }

}

