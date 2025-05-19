package org.sioterino.minesweeper.services;

import org.sioterino.minesweeper.models.Board;
import org.sioterino.minesweeper.models.Board.Point;
import org.sioterino.minesweeper.models.Tile;
import org.sioterino.minesweeper.utils.enums.Difficulty;
import org.sioterino.minesweeper.utils.exceptions.game.*;

public class GameService {

    private final Board board;

    private boolean isGameOver;
    private boolean isWin;

    private int revealedTiles;
    private int flaggedTiles;

    private boolean canGiveHint;

    public GameService(Difficulty gamemode) {
        this.board = new Board(gamemode);

        this.isGameOver = false;
        this.isWin = false;
        this.canGiveHint = true;

        this.revealedTiles = 0;
        this.flaggedTiles = 0;
    }

    private boolean checkWin() {
        int boardSize = board.getHeight() * board.getWidth();
        int mines = board.getMines();

        if (revealedTiles == boardSize - mines) {
            isWin = true;
            isGameOver = true;

            return true;
        }

        return false;
    }

    public void hint() {
        if (!canGiveHint) throw new HintAlreadyGivenException();

        while (true) {

            int x = (int) (Math.random() * board.getWidth());
            int y = (int) (Math.random() * board.getHeight());

            Point p = new Point(x, y);
            Tile tile = board.getTile(p);

            if (!tile.isMine() && !tile.isRevealed() && !tile.isFlagged()) {
                reveal(p);
                break;
            }

        }

        canGiveHint = false;
    }

    public void toggleFlag(Point p) {
        if (board.isInside(p)) {
            Tile tile = board.getTile(p);
            if (!tile.isRevealed()) {

                if (tile.isFlagged()) {
                    flaggedTiles--;
                } else {
                    flaggedTiles++;
                }
                tile.toggleFlag();

            } else {
                throw new TileAlreadyRevealedException(p);
            }
        } else {
            throw new PointCoordinateOutsideBoardException(p);
        }
    }

    public void reveal(Point p) {

        if (!board.isInside(p)) {
            throw new PointCoordinateOutsideBoardException(p);
        }

        Tile tile = board.getTile(p);

        if (tile.isRevealed()) {
            throw new TileAlreadyRevealedException(p);
        }

        if (tile.isFlagged()) {
            throw new TileIsFlaggedException(p);
        }

        revealTile(tile);

        if (tile.isMine()) {
            isWin = false;
            isGameOver = true;
            throw new BombException(p);
        }

        if (tile.getAdjacentMines() == 0) revealAdjacentTiles(p);

        if (checkWin()) {
            throw new VictoryException();
        }

    }

    private void revealAdjacentTiles(Point p) {

        Tile tile = board.getTile(p);
        if (tile.getAdjacentMines() == 0) {

            Point[][] points = board.surroundingPoints(p);

            for (Point[] neighbors : points) {
                for (Point point : neighbors) {

                    if (point == null) continue;

                    Tile neighbor = board.getTile(point);
                    if (!neighbor.isRevealed() && !neighbor.isFlagged()) {
                        revealTile(neighbor);

                        if (neighbor.getAdjacentMines() == 0)
                            revealAdjacentTiles(point);
                    }

                }
            }

        }
    }

    private void revealTile(Tile tile) {
        tile.reveal();
        revealedTiles++;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isWin() {
        return isWin;
    }

    public Board getBoard() {
        return board;
    }

    public int getRevealedTiles() {
        return revealedTiles;
    }

    public int getFlaggedTiles() {
        return flaggedTiles;
    }

}
