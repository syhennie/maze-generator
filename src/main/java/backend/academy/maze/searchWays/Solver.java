package backend.academy.maze.searchWays;

import backend.academy.maze.generatorMaze.Maze;
import backend.academy.maze.outputConsole.Coordinate;
import java.util.List;

/**
 * Интерфейс для алгоритмов решения лабиринта.
 * Определяет метод для поиска пути в лабиринте.
 */
public interface Solver {
    /**
     * Находит путь в лабиринте от стартовой до конечной координаты.
     *
     * @param maze  лабиринт, в котором нужно найти путь
     * @param start стартовая координата
     * @param end   конечная координата
     * @return список координат, представляющий путь от старта до конца,
     *         или пустой список, если путь не найден
     */
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
