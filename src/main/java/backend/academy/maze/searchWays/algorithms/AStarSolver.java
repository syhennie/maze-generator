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

/**
 * Реализация алгоритма A* для решения задачи о лабиринте.
 * Реализует интерфейс {@link Solver}.
 */
public class AStarSolver implements Solver {
    /**
     * Находит путь в лабиринте от стартовой до конечной координаты с использованием алгоритма A*.
     *
     * @param maze  лабиринт, в котором нужно найти путь
     * @param start стартовая координата
     * @param end   конечная координата
     * @return список координат, представляющий путь от старта до конца,
     *         или пустой список, если путь не найден
     */
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

                int cellWeight = maze.getCell(neighbor.row(), neighbor.col()).getWeight();

                int tentativeGScore = gScore.get(current.coordinate) + cellWeight;

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

    /**
     * Вычисляет эвристическую оценку расстояния между двумя координатами.
     * Использует манхэттенское расстояние.
     *
     * @param a первая координата
     * @param b вторая координата
     * @return эвристическая оценка расстояния между двумя координатами
     */
    private int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    /**
     * Восстанавливает путь от конечной координаты до стартовой, используя информацию о предшественниках.
     *
     * @param cameFrom карта предшественников, где ключом является координата,
     *                 а значением - предшествующая координата
     * @param current конечная координата
     * @return список координат, представляющий путь от начала до конца
     */
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

    /**
     * Получает соседние координаты для заданной координаты в лабиринте.
     *
     * @param maze      лабиринт, в котором нужно найти соседей
     * @param coordinate координата, для которой нужно найти соседей
     * @return список соседних координат, которые не являются стенами
     */
    private List<Coordinate> getNeighbors(Maze maze, Coordinate coordinate) {
        List<Coordinate> neighbors = new ArrayList<>();
        int row = coordinate.row();
        int col = coordinate.col();

        if (maze.isValidCell(row - 1, col) && maze.getCell(row - 1, col).type() != Cell.Type.WALL) {
            neighbors.add(new Coordinate(row - 1, col));
        }
        if (maze.isValidCell(row + 1, col) && maze.getCell(row + 1, col).type() != Cell.Type.WALL) {
            neighbors.add(new Coordinate(row + 1, col));
        }
        if (maze.isValidCell(row, col - 1) && maze.getCell(row, col - 1).type() != Cell.Type.WALL) {
            neighbors.add(new Coordinate(row, col - 1));
        }
        if (maze.isValidCell(row, col + 1) && maze.getCell(row, col + 1).type() != Cell.Type.WALL) {
            neighbors.add(new Coordinate(row, col + 1));
        }

        return neighbors;
    }

    /**
     * Класс, представляющий узел в алгоритме A*.
     */
    private static class Node {
        Coordinate coordinate;
        int g;
        int f;

        /**
         * Конструктор узла.
         *
         * @param coordinate координата узла
         * @param g          стоимость пути от старта до узла
         * @param f          общая стоимость пути от старта до цели через узел
         */
        Node(Coordinate coordinate, int g, int f) {
            this.coordinate = coordinate;
            this.g = g;
            this.f = f;
        }
    }
}
