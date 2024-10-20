package backend.academy.maze.outputConsole;

import backend.academy.maze.generatorMaze.Maze;
import java.util.List;

public interface Renderer {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
