package backend.academy.maze;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Maze;
import backend.academy.maze.generatorMaze.algorithms.DFSGenerator;
import backend.academy.maze.generatorMaze.algorithms.PrimGenerator;
import backend.academy.maze.outputConsole.Coordinate;
import backend.academy.maze.searchWays.algorithms.AStarSolver;
import backend.academy.maze.searchWays.algorithms.BFSSolver;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BFSSolverTest {

    @Test
    void testSolve_FindsPath() {
        Maze maze = new Maze(5, 5);
        maze.setCell(0, 1, Cell.Type.PASSAGE);
        maze.setCell(1, 1, Cell.Type.PASSAGE);
        maze.setCell(2, 1, Cell.Type.PASSAGE);
        maze.setCell(3, 1, Cell.Type.PASSAGE);
        maze.setCell(4, 1, Cell.Type.PASSAGE);

        Coordinate start = new Coordinate(0, 1);
        Coordinate end = new Coordinate(4, 1);

        BFSSolver solver = new BFSSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertFalse(path.isEmpty());
        assertEquals(end, path.getLast());
    }

    @Test
    void testSolve_NoPath() {
        Maze maze = new Maze(5, 5);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        BFSSolver solver = new BFSSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertTrue(path.isEmpty());
    }

    @Test
    void testSolve_StartEqualsEnd() {
        Maze maze = new Maze(5, 5);

        Coordinate startAndEnd = new Coordinate(2, 2);

        BFSSolver solver = new BFSSolver();
        List<Coordinate> path = solver.solve(maze, startAndEnd, startAndEnd);

        assertEquals(1, path.size());
        assertEquals(startAndEnd, path.getFirst());
    }

    @Test
    void testSolve_ObstacleNavigation() {
        Maze maze = new Maze(5, 5);
        maze.setCell(0, 1, Cell.Type.PASSAGE);
        maze.setCell(1, 1, Cell.Type.PASSAGE);
        maze.setCell(2, 1, Cell.Type.PASSAGE);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 1);

        BFSSolver solver = new BFSSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertFalse(path.isEmpty());
    }

    @Test
    void testSolve_SingleObstacle() {
        PrimGenerator generator = new PrimGenerator();
        Maze maze = generator.generate(5, 5);

        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 3);

        BFSSolver solver = new BFSSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertFalse(path.isEmpty());
    }

    @Test
    void testSolve_PathWithTwoPaths() {
        Maze maze = new Maze(5, 5);
        maze.setCell(1, 0, Cell.Type.PASSAGE);
        maze.setCell(2, 0, Cell.Type.PASSAGE);
        maze.setCell(3, 0, Cell.Type.PASSAGE);
        maze.setCell(4, 0, Cell.Type.PASSAGE);
        maze.setCell(2, 1, Cell.Type.PASSAGE);
        maze.setCell(2, 2, Cell.Type.PASSAGE);
        maze.setCell(2, 3, Cell.Type.PASSAGE);
        maze.setCell(2, 4, Cell.Type.PASSAGE);
        maze.setCell(3, 4, Cell.Type.PASSAGE);
        maze.setCell(4, 2, Cell.Type.PASSAGE);
        maze.setCell(4, 3, Cell.Type.PASSAGE);
        maze.setCell(4, 1, Cell.Type.PASSAGE);

        maze.setCell(0, 0, Cell.Type.PASSAGE);
        maze.setCell(4, 4, Cell.Type.PASSAGE);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        AStarSolver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertFalse(path.isEmpty());
        assertEquals(end, path.getLast());
    }

    @Test
    void testSolve_EmptyMaze() {
        Maze maze = new Maze(3, 3);
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 2);

        BFSSolver solver = new BFSSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertTrue(path.isEmpty());
    }

    @Test
    void testSolve_LargeMaze() {
        PrimGenerator generator = new PrimGenerator();
        Maze maze = generator.generate(151, 151);

        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(149, 149);

        BFSSolver solver = new BFSSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        if (!path.isEmpty()) {
            assertEquals(end, path.getLast());
        }
    }
}

