package backend.academy.maze.outputConsole;

import backend.academy.maze.generatorMaze.Maze;
import java.util.List;

/**
 * Интерфейс для рендеринга (отображения) лабиринта.
 * Предоставляет методы для отображения лабиринта как в текстовом формате,
 * так и с отображением пути.
 */
public interface Renderer {
    /**
     * Отображает лабиринт без указания пути.
     *
     * @param maze лабиринт для отображения
     * @return строковое представление лабиринта
     */
    String render(Maze maze);

    /**
     * Отображает лабиринт с указанным путем.
     *
     * @param maze лабиринт для отображения
     * @param path список координат, представляющий путь в лабиринте
     * @return строковое представление лабиринта с путем
     */
    String render(Maze maze, List<Coordinate> path);
}
