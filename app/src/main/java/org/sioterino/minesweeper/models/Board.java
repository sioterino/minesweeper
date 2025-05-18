package org.sioterino.minesweeper.models;

public class Board {

    private final int width;
    private final int height;
    private final int mines;

    private final Cell[][] grid;

    public Board(int rows, int cols, int mines) {
        this.height = rows;
        this.width = cols;
        this.mines = mines;
        this.grid = new Cell[height][width];

        loadBoard();
    }

    private void loadBoard() {
        createCells();
        placeMines();
        setAdjacentMines();
    }

    private void createCells() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void placeMines() {
        int placedMines = 0;

        while (placedMines < mines) {
            int row = (int) (Math.random() * height);
            int col = (int) (Math.random() * width);

            Cell cell = grid[row][col];

            if (!cell.isMine()) {
                cell.setMine(true);
                placedMines++;
            }
        }
    }

    private void setAdjacentMines() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = grid[row][col];
                if (!cell.isMine()) {
                    countAdjacentMines(cell, row, col);
                }
            }
        }
    }

    private void countAdjacentMines(Cell cell, int row, int col) {
        int count = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isInside(i, j) && !(i == row && j == col)) {
                    if (grid[i][j].isMine()) {
                        count++;
                    }
                }
            }
        }

        cell.setAdjacentMines(count);
    }

    private boolean isInside(int x, int y) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }

    public Cell getCell(Position p) {
        return grid[p.x][p.y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMines() {
        return mines;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
