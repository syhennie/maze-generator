package backend.academy.maze.searchWays.algorithms;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Maze;
import backend.academy.maze.outputConsole.Coordinate;
import backend.academy.maze.searchWays.Solver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Реализация алгоритма поиска в ширину (BFS) для решения задачи о лабиринте.
 * Реализует интерфейс {@link Solver}.
 */
public class BFSSolver implements Solver {
    private static final int[] DIRECTION_ROW = {-1, 1, 0, 0};
    private static final int[] DIRECTION_COL = {0, 0, -1, 1};
    private static final int STEP = 4;

    /**
     * Находит путь в лабиринте от стартовой до конечной координаты с использованием алгоритма BFS.
     *
     * @param maze  лабиринт, в котором нужно найти путь
     * @param start стартовая координата
     * @param end   конечная координата
     * @return список координат, представляющий путь от старта до конца,
     *         или пустой список, если путь не найден
     */
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        int height = maze.height();
        int width = maze.width();

        Queue<Coordinate> queue = new LinkedList<>();
        boolean[][] visited = new boolean[height][width];

        Map<Coordinate, Coordinate> predecessors = new HashMap<>();

        queue.add(start);
        visited[start.row()][start.col()] = true;
        predecessors.put(start, null);

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (current.equals(end)) {
                return reconstructPath(predecessors, end);
            }

            for (int i = 0; i < STEP; i++) {
                int newRow = current.row() + DIRECTION_ROW[i];
                int newCol = current.col() + DIRECTION_COL[i];

                if (maze.isValidCell(newRow, newCol)
                    && maze.getCell(newRow, newCol).type() == Cell.Type.PASSAGE
                    && !visited[newRow][newCol]) {

                    Coordinate neighbor = new Coordinate(newRow, newCol);
                    queue.add(neighbor);
                    visited[newRow][newCol] = true;

                    predecessors.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList();
    }

    /**
     * Восстанавливает путь от конечной координаты до стартовой, используя информацию о предшественниках.
     *
     * @param predecessors карта предшественников, где ключом является координата,
     *                     а значением - предшествующая координата
     * @param goal конечная координата
     * @return список координат, представляющий путь от начала до конца
     */
    private List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> predecessors, Coordinate goal) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate step = goal;

        while (step != null) {
            path.add(step);
            step = predecessors.get(step);
        }

        Collections.reverse(path);
        return path;
    }
}
