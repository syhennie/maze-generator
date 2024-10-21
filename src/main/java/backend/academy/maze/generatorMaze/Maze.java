package backend.academy.maze.generatorMaze;

import lombok.Getter;

public final class Maze {
    @Getter private final int height;
    @Getter private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Ширина и высота должны быть положительными числами");
        }
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col, Cell.Type.WALL);
            }
        }
    }

    public Cell getCell(int row, int col) {
        if (isValidCell(row, col)) {
            return grid[row][col];
        }
        throw new IllegalArgumentException("Некорректные введенные координаты: (" + row + ", " + col + ")");
    }

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

    public void addObstacles() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].type() == Cell.Type.PASSAGE) {
                    grid[row][col] = new Cell(row, col, Cell.getRandomType());
                }
            }
        }
    }

    public boolean isValidCell(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }
}
