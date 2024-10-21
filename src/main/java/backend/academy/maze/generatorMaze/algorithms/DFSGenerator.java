package backend.academy.maze.generatorMaze.algorithms;

import backend.academy.maze.generatorMaze.Cell;
import backend.academy.maze.generatorMaze.Generator;
import backend.academy.maze.generatorMaze.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DFSGenerator implements Generator {
    private static final int SHIFT = 2;

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        boolean[][] visited = new boolean[height][width];

        dfs(maze, visited, 1, 1);

        return maze;
    }

    private void dfs(Maze maze, boolean[][] visited, int row, int col) {
        visited[row][col] = true;
        maze.setCell(row, col, Cell.Type.PASSAGE);

        List<int[]> directions = new ArrayList<>(List.of(
            new int[] {-SHIFT, 0},
            new int[] {SHIFT, 0},
            new int[] {0, -SHIFT},
            new int[] {0, SHIFT}
        ));
        Collections.shuffle(directions);

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (maze.isValidCell(newRow, newCol) && !visited[newRow][newCol]) {
                maze.setCell((row + newRow) / 2, (col + newCol) / 2, Cell.Type.PASSAGE);
                dfs(maze, visited, newRow, newCol);
            }
        }
    }
}

