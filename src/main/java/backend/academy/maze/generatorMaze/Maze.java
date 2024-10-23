package backend.academy.maze.generatorMaze;

import java.util.Random;
import lombok.Getter;

/**
 * Представляет лабиринт в виде двумерной решетки клеток.
 * Класс позволяет создавать лабиринты с заданной высотой и шириной,
 * а также управлять типами клеток, добавлять препятствия и проверять валидность координат.
 */
public final class Maze {
    @Getter private final int height;
    @Getter private final int width;
    private final Cell[][] grid;
    private static final int DEGREE_RANDOM = 80;

    /**
     * Конструктор для создания лабиринта с заданной высотой и шириной.
     *
     * @param height высота лабиринта
     * @param width  ширина лабиринта
     * @throws IllegalArgumentException если высота или ширина меньше, или равна нулю
     */
    public Maze(int height, int width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Ширина и высота должны быть положительными числами");
        }
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
        initializeGrid();
    }

    /**
     * Инициализирует клетки лабиринта, заполняя их стенами.
     */
    private void initializeGrid() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col, Cell.Type.WALL);
            }
        }
    }

    /**
     * Получает клетку по заданным координатам.
     *
     * @param row строка клетки
     * @param col столбец клетки
     * @return клетка на заданных координатах
     * @throws IllegalArgumentException если координаты недопустимы
     */
    public Cell getCell(int row, int col) {
        if (isValidCell(row, col)) {
            return grid[row][col];
        }
        throw new IllegalArgumentException("Некорректные введенные координаты: (" + row + ", " + col + ")");
    }

    /**
     * Устанавливает тип клетки на заданных координатах.
     *
     * @param row  строка клетки
     * @param col  столбец клетки
     * @param type тип клетки
     * @throws IllegalArgumentException если координаты недопустимы или тип пуст
     */
    public void setCell(int row, int col, Cell.Type type) {
        if (type == null) {
            throw new IllegalArgumentException("Ячейка не может быть пустой");
        }
        if (isValidCell(row, col)) {
            grid[row][col] = new Cell(row, col, type);
        } else {
            throw new IllegalArgumentException("Некорректные координаты: (" + row + ", " + col + ")");
        }
    }

    /**
     * Добавляет случайные препятствия в лабиринт, заменяя проходы.
     */
    public void addObstacles() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].type() == Cell.Type.PASSAGE) {
                    grid[row][col] = new Cell(row, col, Cell.getRandomType());
                }
            }
        }
    }

    /**
     * Добавляет случайные стены в лабиринт.
     */
    public void addRandomWalls() {
        Random random = new Random();
        int totalCells = height * width;
        int numberOfWalls = totalCells / DEGREE_RANDOM;

        int wallsAdded = 0;
        while (wallsAdded < numberOfWalls) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);

            if (grid[row][col].type() == Cell.Type.PASSAGE) {
                grid[row][col] = new Cell(row, col, Cell.Type.WALL);
                wallsAdded++;
            }
        }
    }

    /**
     * Проверяет, являются ли заданные координаты допустимыми.
     *
     * @param row строка клетки
     * @param col столбец клетки
     * @return true, если координаты допустимы, иначе false
     */
    public boolean isValidCell(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }
}
