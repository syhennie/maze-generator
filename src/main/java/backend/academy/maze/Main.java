package backend.academy.maze;

import backend.academy.maze.generatorMaze.Generator;
import backend.academy.maze.generatorMaze.Maze;
import backend.academy.maze.outputConsole.ConsoleRenderer;
import backend.academy.maze.outputConsole.Coordinate;
import backend.academy.maze.outputConsole.GameUI;
import backend.academy.maze.outputConsole.Renderer;
import backend.academy.maze.searchWays.Solver;
import java.io.PrintStream;
import java.util.List;
import lombok.experimental.UtilityClass;

/**
 * Главный класс, отвечающий за запуск игры "Лабиринты".
 * Этот класс содержит основной цикл игры, который управляет
 * генерацией лабиринта, выбором координат, выбором алгоритма решения
 * и выводом результатов пользователю.
 */
@UtilityClass
public class Main {
    /**
     * Главный метод, запускающий игру.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        PrintStream output = System.out;
        GameUI menu = new GameUI(output);
        output.println("Добро пожаловать в игру Лабиринты!");

        while (true) {
            try {
                int[] size = menu.requestMazeSize();
                int height = size[0];
                int width = size[1];

                Generator generator = menu.chooseMazeGenerator();
                int mazeType = menu.requestMazeType();
                boolean withObstacles = menu.addObstacles();

                Maze maze = generator.generate(height, width);

                if (mazeType == 2) {
                    maze.addRandomWalls();
                }

                if (withObstacles) {
                    menu.descriptionObstacles();
                    maze.addObstacles();
                }

                Renderer renderer = new ConsoleRenderer();
                output.println(renderer.render(maze));

                Coordinate[] coordinates = menu.requestCoordinates(height, width);
                Coordinate start = coordinates[0];
                Coordinate end = coordinates[1];

                Solver solver = menu.chooseSolver();
                List<Coordinate> path = solver.solve(maze, start, end);

                if (!path.isEmpty()) {
                    output.println("Путь успешно найден!");
                    output.println(renderer.render(maze, path));
                } else {
                    output.println("К сожалению, пути не существует!");
                }

                if (!menu.continueGame()) {
                    output.println("Спасибо за игру!");
                    break;
                }

            } catch (Exception e) {
                handleError(output, e);
            }
        }
    }

    /**
     * Обрабатывает ошибки, возникающие в процессе игры,
     * выводя сообщение об ошибке и предлагая пользователю
     * попробовать снова.
     *
     * @param output поток вывода
     * @param e      исключение, которое произошло
     */
    public void handleError(PrintStream output, Exception e) {
        output.println("Произошла ошибка: " + e.getMessage());
        output.println("Попробуйте снова.");
    }
}
