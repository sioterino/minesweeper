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

    private final Tile[][] grid;

    public Board(int rows, int cols, int mines) {

        this.height = rows;
        this.width = cols;

        this.mines = mines;
//        this.grid = new Tile[rows][col];
        this.grid = new Tile[height][width];

        loadBoard();
    }

    private void loadBoard() {
        createTiles();
        placeMines();
        setAdjacentMines();
    }

    private void createTiles() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Tile();
            }
        }
    }

    private void placeMines() {
        int placedMines = 0;

        while (placedMines < mines) {
            int row = (int) (Math.random() * height);
            int col = (int) (Math.random() * width);

            Tile tile = grid[row][col];

            if (!tile.isMine()) {
                tile.setMine(true);
                placedMines++;
            }
        }
    }

    private void setAdjacentMines() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Tile tile = grid[row][col];
                if (!tile.isMine()) {
                    countAdjacentMines(tile, row, col);
                }
            }
        }
    }

    private void countAdjacentMines(Tile tile, int row, int col) {
        for (int y = row - 1; y <= row + 1; y++) {
            for (int x = col - 1; x <= col + 1; x++) {

                if (isInside(x, y)) {
                    Tile neighbor = getTile(x, y);
                    /*
                    *   rather than using method equals() to compare weather the
                    *   content of two objects are the same—in this case, they
                    *   might have the same value inside, but they're still two
                    *   different tiles being places on different coordinates inside
                    *   the board—we should check if both variables (tile, neighbor)
                    *   are the same object reference
                    */
                    if (tile != neighbor && neighbor.isMine())
                        tile.increaseAdjacentMines();
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

    public Tile getTile(Point p) {
        return grid[p.y][p.x];
    }

    public Tile getTile(int x, int y) {
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

        private static final String xCoord = "abcdefghijklmnopkrstuvwxyzABCDEFGHIJKLMNOPRSTUVWXLZ";

        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static Point parse(String x, String y) {

            int pointY = Integer.parseInt(y) - 1;
            int pointX = xCoord.indexOf(x.charAt(0));

            return new Point(pointX, pointY);
        }

        @Override
        public String toString() {
            return String.format("%c%d", xCoord.charAt(x), y + 1);
        }
    }

}
