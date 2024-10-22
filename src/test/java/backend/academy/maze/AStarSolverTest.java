package backend.academy.maze;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Maze;
import backend.academy.maze.generatorMaze.algorithms.DFSGenerator;
import backend.academy.maze.outputConsole.Coordinate;
import backend.academy.maze.searchWays.algorithms.AStarSolver;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AStarSolverTest {

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

        AStarSolver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertFalse(path.isEmpty());
        assertEquals(end, path.getLast());
    }

    @Test
    void testSolve_NoPath() {
        Maze maze = new Maze(5, 5);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        AStarSolver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertTrue(path.isEmpty());
    }

    @Test
    void testSolve_StartEqualsEnd() {
        Maze maze = new Maze(5, 5);

        Coordinate startAndEnd = new Coordinate(2, 2);

        AStarSolver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, startAndEnd, startAndEnd);

        assertEquals(1, path.size());
        assertEquals(startAndEnd, path.getFirst());
    }

    @Test
    void testSolve_ObstacleNavigation() {
        DFSGenerator generator = new DFSGenerator();
        Maze maze = generator.generate(5, 5);

        maze.setCell(0, 1, Cell.Type.WALL);
        maze.setCell(1, 1, Cell.Type.WALL);
        maze.setCell(2, 1, Cell.Type.WALL);

        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 3);

        AStarSolver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertFalse(path.isEmpty());
    }

    @Test
    void testSolve_SingleObstacle() {
        DFSGenerator generator = new DFSGenerator();
        Maze maze = generator.generate(5, 5);

        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 3);

        AStarSolver solver = new AStarSolver();
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
    void testSolve_PathWithTwoPathsAndWeight() {
        Maze maze = new Maze(5, 5);
        maze.setCell(1, 0, Cell.getRandomNonWallType());
        maze.setCell(2, 0, Cell.getRandomNonWallType());
        maze.setCell(3, 0, Cell.getRandomNonWallType());
        maze.setCell(4, 0, Cell.getRandomNonWallType());
        maze.setCell(2, 1, Cell.getRandomNonWallType());
        maze.setCell(2, 2, Cell.getRandomNonWallType());
        maze.setCell(2, 3, Cell.getRandomNonWallType());
        maze.setCell(2, 4, Cell.getRandomNonWallType());
        maze.setCell(3, 4, Cell.getRandomNonWallType());
        maze.setCell(4, 2, Cell.getRandomNonWallType());
        maze.setCell(4, 3, Cell.getRandomNonWallType());
        maze.setCell(4, 1, Cell.getRandomNonWallType());

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
        Maze maze = new Maze(5, 5);
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        AStarSolver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertTrue(path.isEmpty());
    }

    @Test
    void testSolve_LargeMaze() {
        DFSGenerator generator = new DFSGenerator();
        Maze maze = generator.generate(55, 59);

        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(53, 53);

        AStarSolver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        if (!path.isEmpty()) {
            assertEquals(end, path.getLast());
        }
    }
}
