package backend.academy.maze.outputConsole;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Maze;
import java.util.List;

public class ConsoleRenderer implements Renderer {
    private static final String WALL = "███";
    private static final String PASSAGE = "   ";
    private static final String RAIN = " ⚡ ";
    private static final String SNOW = " * ";
    private static final String LOCKED = " ☦ ";
    private static final String MONEY = " ₽ ";
    private static final String PATH = " o ";

    @Override
    public String render(Maze maze) {
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

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder builder = new StringBuilder();

        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Cell cell = maze.getCell(row, col);

                if (isPartOfPath(path, row, col)) {
                    builder.append(PATH);  // Символ для пути
                } else {
                    chooseType(builder, cell);
                }
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    private void chooseType(StringBuilder builder, Cell cell) {
        switch (cell.type()) {
            case WALL -> builder.append(WALL);
            case PASSAGE -> builder.append(PASSAGE);
            case RAIN -> builder.append(RAIN);
            case SNOW -> builder.append(SNOW);
            case LOCKED -> builder.append(LOCKED);
            default -> builder.append(MONEY);
        }
    }

    private boolean isPartOfPath(List<Coordinate> path, int row, int col) {
        return path.stream().anyMatch(coordinate -> coordinate.row() == row && coordinate.col() == col);
    }
}