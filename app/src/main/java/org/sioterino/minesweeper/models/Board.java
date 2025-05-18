package org.sioterino.minesweeper.models;

public class Board {

    /*
    *   im quite stupid so I'll just let this be here
    *
    *          ROWS = HEIGHT
    *       COLUMNS = WIDTH
    */

    private final int width;
    private final int height;
    private final int mines;

    private final Cell[][] grid;

    public Board(int rows, int cols, int mines) {

        this.height = rows;
        this.width = cols;

        this.mines = mines;
//        this.grid = new Cell[rows][col];
        this.grid = new Cell[height][width];

        loadBoard();
    }

    private void loadBoard() {
        createCells();
        placeMines();
        setAdjacentMines();
    }

    private void createCells() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell();
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
        for (int y = row - 1; y <= row + 1; y++) {
            for (int x = col - 1; x <= col + 1; x++) {

                if (isInside(x, y)) {
                    Cell neighbor = getCell(x, y);
                    /*
                    *   rather than using method equals() to compare weather the
                    *   content of two objects are the same—in this case, they
                    *   might have the same value inside, but they're still two
                    *   different cells being places on different coordinates inside
                    *   the board—we should check if both variables (cell, neighbor)
                    *   are the same object reference
                    */
                    if (cell != neighbor && neighbor.isMine())
                        cell.increaseAdjacentMines();
                }

            }
        }
    }

    public boolean isInside(int x, int y) {

        boolean isXPositive = x >= 0;
        boolean isXWithinWidth = x < width;
        boolean isYPositive = y >= 0;
        boolean isYWithinWidth = y < height;

        return isXPositive && isXWithinWidth && isYPositive && isYWithinWidth;
    }

    public boolean isInside(Point p) {

        boolean isXPositive = p.x >= 0;
        boolean isXWithinWidth = p.x < width;
        boolean isYPositive = p.y >= 0;
        boolean isYWithinWidth = p.y < height;

        return isXPositive && isXWithinWidth && isYPositive && isYWithinWidth;
    }

    public Cell getCell(Point p) {
        return grid[p.x][p.y];
    }

    public Cell getCell(int x, int y) {
        return grid[y][x];
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

    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
