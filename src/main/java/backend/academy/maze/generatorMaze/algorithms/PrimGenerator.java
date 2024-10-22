package backend.academy.maze.generatorMaze.algorithms;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Generator;
import backend.academy.maze.generatorMaze.Maze;
import backend.academy.maze.outputConsole.Coordinate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimGenerator implements Generator {
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);

        Coordinate start = getRandomStartCoordinate(height, width);
        maze.setCell(start.row(), start.col(), Cell.Type.PASSAGE);

        List<Coordinate> frontiers = new ArrayList<>();
        addFrontierCells(start, maze, frontiers);

        while (!frontiers.isEmpty()) {
            Coordinate frontier = frontiers.remove(random.nextInt(frontiers.size()));

            if (maze.getCell(frontier.row(), frontier.col()).type() == Cell.Type.WALL) {
                Coordinate neighbor = getRandomNeighbor(frontier, maze);

                if (neighbor != null) {
                    createPassageBetween(frontier, neighbor, maze);
                    addFrontierCells(frontier, maze, frontiers);
                }
            }
        }

        return maze;
    }

    public Coordinate getRandomStartCoordinate(int height, int width) {
        int startRow = random.nextInt(height / 2) * 2 + 1;
        int startCol = random.nextInt(width / 2) * 2 + 1;
        return new Coordinate(startRow, startCol);
    }

    private Coordinate getRandomNeighbor(Coordinate frontier, Maze maze) {
        List<Coordinate> neighbors = new ArrayList<>();

        addNeighborIfValid(frontier.row() - 2, frontier.col(), maze, neighbors);
        addNeighborIfValid(frontier.row() + 2, frontier.col(), maze, neighbors);
        addNeighborIfValid(frontier.row(), frontier.col() - 2, maze, neighbors);
        addNeighborIfValid(frontier.row(), frontier.col() + 2, maze, neighbors);

        return neighbors.isEmpty() ? null : neighbors.get(random.nextInt(neighbors.size()));
    }

    private void addNeighborIfValid(int row, int col, Maze maze, List<Coordinate> neighbors) {
        if (maze.isValidCell(row, col) && maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(row, col));
        }
    }

    private void createPassageBetween(Coordinate frontier, Coordinate neighbor, Maze maze) {
        int wallRow = (neighbor.row() + frontier.row()) / 2;
        int wallCol = (neighbor.col() + frontier.col()) / 2;

        maze.setCell(wallRow, wallCol, Cell.Type.PASSAGE);
        maze.setCell(frontier.row(), frontier.col(), Cell.Type.PASSAGE);
    }

    private void addFrontierCells(Coordinate coordinate, Maze maze, List<Coordinate> frontiers) {
        addFrontierIfValid(coordinate.row() - 2, coordinate.col(), maze, frontiers);
        addFrontierIfValid(coordinate.row() + 2, coordinate.col(), maze, frontiers);
        addFrontierIfValid(coordinate.row(), coordinate.col() - 2, maze, frontiers);
        addFrontierIfValid(coordinate.row(), coordinate.col() + 2, maze, frontiers);
    }

    private void addFrontierIfValid(int row, int col, Maze maze, List<Coordinate> frontiers) {
        if (maze.isValidCell(row, col)) {
            frontiers.add(new Coordinate(row, col));
        }
    }
}
