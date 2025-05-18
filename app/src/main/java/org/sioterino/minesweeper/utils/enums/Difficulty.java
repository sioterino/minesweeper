package org.sioterino.minesweeper.utils.enums;

public enum Difficulty {
    BEGINNER(7, 14, 10),
    INTERMEDIATE(12, 24, 40),
    EXPERT(17, 34, 99);

    private final int rows;
    private final int cols;
    private final int mines;

    Difficulty(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getMines() { return mines; }
}
