package backend.academy.maze.generatorMaze;

import java.util.Random;

/**
 * Представляет ячейку лабиринта с координатами и типом.
 *
 * @param row строка ячейки в лабиринте
 * @param col столбец ячейки в лабиринте
 * @param type тип ячейки (стена, проход, снег, дождь, заперто, монета)
 */
public record Cell(int row, int col, Type type) {
    private static final Random RANDOM = new Random();
    private static final int ALL_PERCENT = 100;
    private static final int NEEDED_PERCENT = 25;
    private static final int PASSAGE_WT = 1;
    private static final int SNOW_WT = 4;
    private static final int RAIN_WT = 2;
    private static final int LOCKED_WT = 1_000_000;
    private static final int MONEY_WT = 0;

    /**
     * Перечисление типов ячеек лабиринта.
     */
    public enum Type {
        WALL, PASSAGE, SNOW, RAIN, LOCKED, MONEY
    }

    /**
     * Возвращает вес ячейки в зависимости от её типа.
     *
     * @return вес ячейки
     */
    public int getWeight() {
        return switch (type) {
            case PASSAGE -> PASSAGE_WT;
            case SNOW -> SNOW_WT;
            case RAIN -> RAIN_WT;
            case LOCKED -> LOCKED_WT;
            case MONEY -> MONEY_WT;
            default -> Integer.MAX_VALUE; // Невозможный тип (стена)
        };
    }

    /**
     * Генерирует случайный тип ячейки.
     *
     * @return случайный тип ячейки (75% - проход, 25% - другой тип)
     */
    public static Type getRandomType() {
        return RANDOM.nextInt(ALL_PERCENT) < NEEDED_PERCENT
            ? getRandomNonWallType()
            : Type.PASSAGE; // 75% случаев возвращаем PASSAGE
    }

    /**
     * Генерирует случайный тип ячейки, который не является стеной.
     *
     * @return случайный тип ячейки (проход, снег, дождь, заперто, монета)
     */
    public static Type getRandomNonWallType() {
        Type[] nonWallTypes = {Type.PASSAGE, Type.SNOW, Type.RAIN, Type.LOCKED, Type.MONEY};
        return nonWallTypes[RANDOM.nextInt(nonWallTypes.length)];
    }
}
