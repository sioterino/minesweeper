package org.sioterino.minesweeper.models;

import org.sioterino.minesweeper.utils.enums.Difficulty;

public class Board {

    private final int width;
    private final int height;
    private final int mines;

    private final Tile[][] grid;

    public Board(Difficulty gamemode) {

        this.height = gamemode.getRows();
        this.width = gamemode.getCols();

        this.mines = gamemode.getMines();
        this.grid = new Tile[height][width];

        loadBoard();
    }

    private void loadBoard() {
        createTiles();
        placeMines();
        countAdjacentMines();
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

    private void countAdjacentMines() {

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                Tile tile = grid[row][col];
                if (tile.isMine()) {

                    Point[][] points = surroundingPoints(col, row);
                    for (Point[] neighbors : points) {
                        for (Point point : neighbors) {

                            if (point == null) continue;
                            getTile(point).increaseAdjacentMines();

                        }
                    }

                }

            }
        }
    }

    public Point[][] surroundingPoints(int x, int y) {
        return surroundingPoints(new Point(x, y));
    }

    public Point[][] surroundingPoints(Point p) {

        Point[][] points = new Point[3][3];

        for (int dy = 1; dy >= -1; dy--) {
            for (int dx = -1; dx <= 1; dx++) {

                int row = 1 - dy;
                int col = dx + 1;

                if (dx == 0 && dy == 0) {
                    points[row][col] = null;
                    continue;
                }

                Point neighbor = new Point(p.x + dx, p.y + dy);
                points[row][col] = isInside(neighbor) ? neighbor : null;

            }
        }
        return points;
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
