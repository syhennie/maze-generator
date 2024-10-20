package backend.academy.maze.searchWays.algorithms;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Maze;
import backend.academy.maze.outputConsole.Coordinate;
import backend.academy.maze.searchWays.Solver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));

        Set<Coordinate> closedSet = new HashSet<>();

        Map<Coordinate, Integer> gScore = new HashMap<>();
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();

        gScore.put(start, 0);

        openSet.add(new Node(start, 0, heuristic(start, end)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.coordinate.equals(end)) {
                return reconstructPath(cameFrom, current.coordinate);
            }
            closedSet.add(current.coordinate);

            for (Coordinate neighbor : getNeighbors(maze, current.coordinate)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeGScore = gScore.get(current.coordinate) + 1; // 1 - стоимость движения

                if (!gScore.containsKey(neighbor) || tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current.coordinate);
                    gScore.put(neighbor, tentativeGScore);

                    int fScore = tentativeGScore + heuristic(neighbor, end);
                    openSet.add(new Node(neighbor, tentativeGScore, fScore));
                }
            }
        }

        return Collections.emptyList();
    }

    private int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    private List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> cameFrom, Coordinate current) {
        List<Coordinate> totalPath = new ArrayList<>();
        Coordinate temp = current;
        while (cameFrom.containsKey(temp)) {
            totalPath.add(temp);
            temp = cameFrom.get(temp);
        }
        totalPath.add(temp);
        Collections.reverse(totalPath);
        return totalPath;
    }

    private List<Coordinate> getNeighbors(Maze maze, Coordinate coordinate) {
        List<Coordinate> neighbors = new ArrayList<>();
        int row = coordinate.row();
        int col = coordinate.col();

        if (maze.isValidCell(row - 1, col) && maze.getCell(row - 1, col).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(row - 1, col));
        }
        if (maze.isValidCell(row + 1, col) && maze.getCell(row + 1, col).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(row + 1, col));
        }
        if (maze.isValidCell(row, col - 1) && maze.getCell(row, col - 1).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(row, col - 1));
        }
        if (maze.isValidCell(row, col + 1) && maze.getCell(row, col + 1).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(row, col + 1));
        }

        return neighbors;
    }

    private static class Node {
        Coordinate coordinate;
        int g;
        int f;

        Node(Coordinate coordinate, int g, int f) {
            this.coordinate = coordinate;
            this.g = g;
            this.f = f;
        }
    }

}
