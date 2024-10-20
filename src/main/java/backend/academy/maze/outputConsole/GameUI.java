package backend.academy.maze.outputConsole;

import backend.academy.maze.generatorMaze.Generator;
import backend.academy.maze.generatorMaze.algorithms.DFSGenerator;
import backend.academy.maze.generatorMaze.algorithms.PrimGenerator;
import backend.academy.maze.searchWays.Solver;
import backend.academy.maze.searchWays.algorithms.AStarSolver;
import backend.academy.maze.searchWays.algorithms.BFSSolver;
import java.io.PrintStream;
import java.util.Scanner;

public class GameUI {
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream output;

    public GameUI(PrintStream output) {
        this.output = output;
    }

    public int[] requestMazeSize() {
        output.println("Введем размеры лабиринта. Обратите внимание, размеры должны быть нечётными числами.");
        output.print("Введите высоту лабиринта: ");
        int height = scanner.nextInt();
        output.print("Введите ширину лабиринта: ");
        int width = scanner.nextInt();
        return new int[]{height, width};
    }

    public Generator chooseMazeGenerator() {
        output.println("Выберите метод генерации: [1]-алгоритм Прима, [2]-DFS");
        int choice = scanner.nextInt();

        return switch (choice) {
            case 1 -> new PrimGenerator();
            case 2 -> new DFSGenerator();
            default -> {
                output.println("Некорректный ввод. По умолчанию выбран DFS.");
                yield new DFSGenerator();
            }
        };
    }

    public Coordinate[] requestCoordinates() {
        output.print("Введите стартовую точку. Сначала х: ");
        int startRow = scanner.nextInt();
        output.print("Теперь у: ");
        int startCol = scanner.nextInt();

        output.print("Проделаем то же самое с конечной точкой. Выбранный х: ");
        int endRow = scanner.nextInt();
        output.print("И снова у: ");
        int endCol = scanner.nextInt();

        return new Coordinate[]{new Coordinate(startRow, startCol), new Coordinate(endRow, endCol)};
    }

    public Solver chooseSolver() {
        output.println("Настала пора выбрать алгоритм для поиска пути: [1]-поиск в ширину, [2]-А*");
        int choice = scanner.nextInt();

        return switch (choice) {
            case 1 -> new BFSSolver();
            case 2 -> new AStarSolver();
            default -> {
                output.println("Некорректный ввод. По умолчанию выбран BFS.");
                yield new BFSSolver();
            }
        };
    }

    public boolean continueGame() {
        output.println("Хотите продолжить игру? [1]-да, [0]-нет");
        int choice = scanner.nextInt();
        return choice == 1;
    }
}
