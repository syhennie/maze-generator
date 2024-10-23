package backend.academy.maze.outputConsole;

import backend.academy.maze.generatorMaze.Generator;
import backend.academy.maze.generatorMaze.algorithms.DFSGenerator;
import backend.academy.maze.generatorMaze.algorithms.PrimGenerator;
import backend.academy.maze.searchWays.Solver;
import backend.academy.maze.searchWays.algorithms.AStarSolver;
import backend.academy.maze.searchWays.algorithms.BFSSolver;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс для взаимодействия с пользователем в текстовом интерфейсе игры.
 * Предоставляет методы для запроса размеров лабиринта, координат,
 * выбора генератора и решателя, а также для отображения информации.
 */
public class GameUI {
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream output;
    private static final int MIN_SIZE_MAZE = 3;
    private static final int MAX_SIZE_MAZE = 151;
    private static final String PROMPT = "Ваш выбор: ";
    private static final String ODD_SIZE_PROMPT =
        "Введем размеры лабиринта. Размеры должны быть нечётными числами в диапазоне от 3 до 151 включительно.";
    private static final String INCORRECT_INPUT = "Некорректный ввод. Пожалуйста, введите число.";
    private static final String CHECKSTYLE_IS_JOKE = "): ";

    /**
     * Конструктор, инициализирующий объект GameUI.
     *
     * @param output поток вывода для отображения информации пользователю
     */
    public GameUI(PrintStream output) {
        this.output = output;
    }

    /**
     * Запрашивает размеры лабиринта у пользователя.
     *
     * @return массив с высотой и шириной лабиринта
     */
    public int[] requestMazeSize() {
        output.println(ODD_SIZE_PROMPT);

        int height = getOddSize("Введите высоту лабиринта: ");
        int width = getOddSize("Введите ширину лабиринта: ");

        return new int[] {height, width};
    }

    private int getOddSize(String prompt) {
        int size;
        while (true) {
            output.print(prompt);
            try {
                size = scanner.nextInt();
                if (size >= MIN_SIZE_MAZE && size <= MAX_SIZE_MAZE && size % 2 != 0) {
                    break;
                } else {
                    output.println("Размер должен быть нечётным числом в диапазоне от 3 до 151.");
                }
            } catch (InputMismatchException e) {
                output.println(INCORRECT_INPUT);
                scanner.next();
            }
        }
        return size;
    }

    /**
     * Запрашивает у пользователя стартовые и конечные координаты.
     *
     * @param mazeHeight высота лабиринта
     * @param mazeWidth ширина лабиринта
     * @return массив с координатами стартовой и конечной точек
     */
    public Coordinate[] requestCoordinates(int mazeHeight, int mazeWidth) {
        output.print("Введите стартовую точку. Сначала строку (1 до " + (mazeHeight - 2) + CHECKSTYLE_IS_JOKE);
        int startRow = getCoordinate(mazeHeight - 2);
        output.print("Теперь столбец (1 до " + (mazeWidth - 2) + CHECKSTYLE_IS_JOKE);
        int startCol = getCoordinate(mazeWidth - 2);

        output.print(
            "Проделаем то же самое с конечной точкой. Выбранная строка (1 до " + (mazeHeight - 2) + CHECKSTYLE_IS_JOKE);
        int endRow = getCoordinate(mazeHeight - 2);
        output.print("И снова столбец (1 до " + (mazeWidth - 2) + CHECKSTYLE_IS_JOKE);
        int endCol = getCoordinate(mazeWidth - 2);

        return new Coordinate[] {new Coordinate(startRow, startCol), new Coordinate(endRow, endCol)};
    }

    private int getCoordinate(int max) {
        int coordinate;
        while (true) {
            try {
                coordinate = scanner.nextInt();
                if (coordinate >= 1 && coordinate <= max) {
                    break;
                } else {
                    output.println("Координата должна быть в диапазоне от " + 1 + " до " + max + ".");
                }
            } catch (InputMismatchException e) {
                output.println(INCORRECT_INPUT);
                scanner.next();
            }
        }
        return coordinate;
    }

    /**
     * Запрашивает у пользователя выбор метода генерации лабиринта.
     *
     * @return объект генератора лабиринта, выбранного пользователем
     */
    public Generator chooseMazeGenerator() {
        output.println("Выберите метод генерации: [1]-алгоритм Прима, [2]-DFS");

        int choice = getUserInput();

        return switch (choice) {
            case 1 -> new PrimGenerator();
            case 2 -> new DFSGenerator();
            default -> {
                output.println("Некорректный ввод. По умолчанию выбран DFS.");
                yield new DFSGenerator();
            }
        };
    }

    /**
     * Запрашивает у пользователя выбор алгоритма поиска пути.
     *
     * @return объект решателя, выбранного пользователем
     */
    public Solver chooseSolver() {
        output.println("Настала пора выбрать алгоритм для поиска пути: [1]-поиск в ширину, [2]-А*");

        int choice = getUserInput();

        return switch (choice) {
            case 1 -> new BFSSolver();
            case 2 -> new AStarSolver();
            default -> {
                output.println("Некорректный ввод. По умолчанию выбран BFS.");
                yield new BFSSolver();
            }
        };
    }

    /**
     * Запрашивает у пользователя, хочет ли он добавить препятствия в лабиринт.
     *
     * @return true, если пользователь хочет добавить препятствия, иначе false
     */
    public boolean addObstacles() {
        output.println("Хотите добавить препятствия в лабиринт? [1]-да, [2]-нет");

        int choice = getUserInput();
        return choice == 1;
    }

    /**
     * Выводит описание препятствий, которые могут быть добавлены в лабиринт.
     */
    public void descriptionObstacles() {
        output.println("⚡ - дождь: уменьшает скорость передвижения");
        output.println("☦ - кладбище: если есть другой путь - лучше выбрать его...");
        output.println("* - снег: значительно уменьшает скорость передвижения");
        output.println("₽ - монетка: улучшает настроение при проходе через неё\n");
    }

    /**
     * Запрашивает у пользователя тип лабиринта для генерации.
     *
     * @return 1 для идеального лабиринта, 2 для неидеального
     */
    public int requestMazeType() {
        output.println("Сгенерировать [1]-идеальный или [2]-неидеальный лабиринт?");
        int choice = scanner.nextInt();

        return choice == 2 ? 2 : 1;
    }

    /**
     * Запрашивает у пользователя, хочет ли он продолжить игру.
     *
     * @return true, если пользователь хочет продолжить, иначе false
     */
    public boolean continueGame() {
        output.println("Хотите продолжить игру? [1]-да, [2]-нет");

        int choice = getUserInput();
        return choice == 1;
    }

    private int getUserInput() {
        int input;
        while (true) {
            output.print(GameUI.PROMPT);
            try {
                input = scanner.nextInt();
                break; // Ввод успешен, выходим из цикла
            } catch (InputMismatchException e) {
                output.println(INCORRECT_INPUT);
                scanner.next();
            }
        }
        return input;
    }
}
