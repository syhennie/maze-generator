package backend.academy.maze.generatorMaze;

/**
 * Интерфейс для генерации лабиринтов.
 * Реализующие классы должны предоставлять конкретные алгоритмы генерации лабиринта
 * на основе заданной высоты и ширины.
 */
public interface Generator {
    /**
     * Генерирует лабиринт заданной высоты и ширины.
     *
     * @param height высота лабиринта
     * @param width  ширина лабиринта
     * @return сгенерированный лабиринт
     */
    Maze generate(int height, int width);
}
