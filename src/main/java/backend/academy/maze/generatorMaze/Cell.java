package backend.academy.maze.generatorMaze;

import java.util.Random;

public record Cell(int row, int col, Type type) {
    private static final Random RANDOM = new Random();
    private static final int ALL_PERCENT = 100;
    private static final int NEEDED_PERCENT = 25;

    public enum Type { WALL, PASSAGE, SNOW, RAIN, LOCKED, MONEY }

    public int getWeight() {
        return switch (type) {
            case PASSAGE -> 1;
            case SNOW -> 4;
            case RAIN -> 2;
            case LOCKED -> 1_000_000;
            case MONEY -> 0;
            default -> Integer.MAX_VALUE;
        };
    }

    public static Type getRandomType() {
        return RANDOM.nextInt(ALL_PERCENT) < NEEDED_PERCENT
            ? getRandomNonWallType()
            : Type.PASSAGE; // 75% случаев возвращаем PASSAGE
    }

    private static Type getRandomNonWallType() {
        Type[] nonWallTypes = {Type.PASSAGE, Type.SNOW, Type.RAIN, Type.LOCKED, Type.MONEY};
        return nonWallTypes[RANDOM.nextInt(nonWallTypes.length)];
    }
}
