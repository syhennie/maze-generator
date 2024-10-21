package backend.academy.maze.generatorMaze;

import java.util.Random;

public record Cell(int row, int col, Type type) {
    private static final Random RANDOM = new Random();

    public enum Type {WALL, PASSAGE, SNOW, RAIN, LOCKED, MONEY}

    public static Type getRandomType() {
        // Вероятность 25%
        if (RANDOM.nextInt(100) < 25) {
            Type[] nonWallTypes = {Type.PASSAGE, Type.SNOW, Type.RAIN, Type.LOCKED, Type.MONEY};
            return nonWallTypes[RANDOM.nextInt(nonWallTypes.length)];
        }
        return Type.PASSAGE; // 75% случаев возвращаем PASSAGE
    }
}
