package backend.academy.maze.outputConsole;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Maze;
import java.util.List;

/**
 * Реализация интерфейса {@link Renderer} для отображения лабиринта в консоли.
 * Объект этого класса отвечает за визуализацию клеток лабиринта, включая
 * стены, проходы и специальные клетки (такие, как дождь, снег, замок и деньги).
 */
public class ConsoleRenderer implements Renderer {
    private static final String WALL = "███";
    private static final String PASSAGE = "   ";
    private static final String RAIN = " ⚡ ";
    private static final String SNOW = " * ";
    private static final String LOCKED = " ☦ ";
    private static final String MONEY = " ₽ ";
    private static final String PATH = " o ";

    /**
     * Генерирует строку, представляющую лабиринт без пути.
     *
     * @param maze лабиринт для отображения
     * @return строка, представляющая лабиринт
     * @throws IllegalArgumentException если лабиринт пуст
     */
    @Override
    public String render(Maze maze) {
        validateMaze(maze);
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Cell cell = maze.getCell(row, col);
                chooseType(builder, cell);
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    /**
     * Генерирует строку, представляющую лабиринт с отмеченным путем.
     *
     * @param maze лабиринт для отображения
     * @param path путь для отображения в лабиринте
     * @return строка, представляющая лабиринт с путем
     * @throws IllegalArgumentException если лабиринт или путь пусты
     */
    @Override
    public String render(Maze maze, List<Coordinate> path) {
        validateMaze(maze);
        validatePath(path);
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Cell cell = maze.getCell(row, col);
                if (isPartOfPath(path, row, col)) {
                    builder.append(PATH);
                } else {
                    chooseType(builder, cell);
                }
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    /**
     * Выбирает символ для отображения типа клетки.
     *
     * @param builder строка, в которую добавляется символ
     * @param cell клетка, тип которой нужно отобразить
     */
    private void chooseType(StringBuilder builder, Cell cell) {
        switch (cell.type()) {
            case WALL -> builder.append(WALL);
            case PASSAGE -> builder.append(PASSAGE);
            case RAIN -> builder.append(RAIN);
            case SNOW -> builder.append(SNOW);
            case LOCKED -> builder.append(LOCKED);
            case MONEY -> builder.append(MONEY);
            default -> throw new IllegalArgumentException("Неизвестный тип клетки: " + cell.type());
        }
    }

    /**
     * Проверяет, является ли заданная клетка частью пути.
     *
     * @param path список координат, представляющий путь
     * @param row  строка клетки
     * @param col  столбец клетки
     * @return true, если клетка является частью пути, иначе false
     */
    private boolean isPartOfPath(List<Coordinate> path, int row, int col) {
        return path.stream().anyMatch(coordinate -> coordinate.row() == row && coordinate.col() == col);
    }

    /**
     * Проверяет, что лабиринт не пуст.
     *
     * @param maze лабиринт для проверки
     * @throws IllegalArgumentException если лабиринт пуст
     */
    private void validateMaze(Maze maze) {
        if (maze == null) {
            throw new IllegalArgumentException("Лабиринт не может быть пуст");
        }
    }

    /**
     * Проверяет, что путь не пуст.
     *
     * @param path путь для проверки
     * @throws IllegalArgumentException если путь пуст
     */
    private void validatePath(List<Coordinate> path) {
        if (path == null) {
            throw new IllegalArgumentException("Путь не может быть пуст");
        }
    }
}
