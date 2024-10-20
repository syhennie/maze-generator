package backend.academy.maze.generatorMaze;

import lombok.Getter;

@Getter public enum Type {
    PASSAGE(" ", "Обычная клетка", 1),
    WALL("█", "Стена", 10000000),
    SNOW("❄", "Снег: уменьшает скорость передвижения", 2),
    LOCKED("☠", "Радиоактивное поле: недоступно для прохождения", 1000000),
    RAIN("⛆", "Дождь: значительно уменьшает скорость передвижения", 3),
    MONEY("₽", "Монетка: Не только двигаемся, но и зарабатываем", 0);

    private final String symbol;
    private final String description;
    private final int wt;

    Type(String symbol, String description, int wt) {
        this.symbol = symbol;
        this.description = description;
        this.wt = wt;
    }
}
