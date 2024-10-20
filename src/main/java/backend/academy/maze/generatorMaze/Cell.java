package backend.academy.maze.generatorMaze;

import java.util.Random;

public record Cell(int row, int col, Type type) {
    private static final Random RANDOM = new Random();

    public enum Type { WALL, PASSAGE, SNOW, RAIN, LOCKED, MONEY }

    public static Type getRandomType() {
        Type[] types = {Type.PASSAGE, Type.SNOW, Type.RAIN, Type.LOCKED, Type.MONEY};
        return types[RANDOM.nextInt(types.length)];
    }
}
