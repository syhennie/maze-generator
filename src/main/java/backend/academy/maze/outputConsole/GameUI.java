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
        return new int[] {height, width};
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
        output.print("Введите стартовую точку. Сначала строку: ");
        int startRow = scanner.nextInt();
        output.print("Теперь столбец: ");
        int startCol = scanner.nextInt();

        output.print("Проделаем то же самое с конечной точкой. Выбранная строка: ");
        int endRow = scanner.nextInt();
        output.print("И снова столбец: ");
        int endCol = scanner.nextInt();

        return new Coordinate[] {new Coordinate(startRow, startCol), new Coordinate(endRow, endCol)};
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

    public boolean addObstacles() {
        output.println("Хотите добавить препятствия в лабиринт? [1]-да, [2]-нет");
        int choice = scanner.nextInt();
        return choice == 1;
    }

    public void descriptionObstacles() {
        output.println("⚡ - дождь: уменьшает скорость передвижения");
        output.println("☦ - кладбище: если есть другой путь - лучше выбрать его...");
        output.println("* - снег: значительно уменьшает скорость передвижения");
        output.println("₽ - монетка: улучшает настроение при проходе через неё\n");
    }

    public boolean continueGame() {
        output.println("Хотите продолжить игру? [1]-да, [2]-нет");
        int choice = scanner.nextInt();
        return choice == 1;
    }
}
