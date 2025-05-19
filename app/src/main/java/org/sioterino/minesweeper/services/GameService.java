package org.sioterino.minesweeper.services;

import org.sioterino.minesweeper.models.Board;
import org.sioterino.minesweeper.models.Board.Point;
import org.sioterino.minesweeper.models.Tile;
import org.sioterino.minesweeper.utils.exceptions.game.*;

public class GameService {

    private final Board board;

    private boolean isGameOver;
    private boolean isWin;

    private int revealedTiles;
    private int correctlyFlaggedTiles;
    private int flaggedTiles;

    public GameService(int rows, int cols, int mines) {
        this.board = new Board(rows, cols, mines);

        this.isGameOver = false;
        this.isWin = false;

        this.revealedTiles = 0;
        this.correctlyFlaggedTiles = 0;
        this.flaggedTiles = 0;
    }

    private boolean checkWin() {
        int boardSize = board.getHeight() * board.getWidth();
        int mines = board.getMines();

        if (revealedTiles == boardSize - mines || mines == correctlyFlaggedTiles) {
            isWin = true;
            isGameOver = true;

            return true;
        }

        return false;
    }

    public void reveal(Point p) {

        if (!board.isInside(p)) {
            throw new PointCoordinateOutsideBoardException(p);
        };

        Tile tile = board.getTile(p);

        if (tile.isRevealed()) {
            throw new TileAlreadyRevealedException(p);
        };

        if (tile.isFlagged()) {
            throw new TileIsFlaggedException(p);
        };

        revealTile(tile);

        if (tile.isMine()) {
            isWin = false;
            isGameOver = true;
            throw new BombException(p);
        }

        if (tile.getAdjacentMines() == 0) revealAdjacentTiles(p);

        if (checkWin()) {
            throw new VictoryException();
        };

    }

    private void revealAdjacentTiles(Point p) {

        for (int row = p.y - 1; row <= p.y + 1; row++) {
            for (int col = p.x - 1; col <= p.x + 1; col++) {

                if (board.isInside(row, col)) {

                    Tile neighbor = board.getTile(row, col);

                    if (!neighbor.isRevealed() && !neighbor.isMine()) {
                        revealTile(neighbor);
                        if (neighbor.getAdjacentMines() == 0) revealAdjacentTiles(new Point(col, row));
                    }
                }

            }
        }

    }

    private void revealTile(Tile tile) {
        tile.reveal();
        revealedTiles++;
    }

    public void toggleFlag(Point p) {
        if (board.isInside(p)) {
            Tile tile = board.getTile(p);
            if (!tile.isRevealed()) {

                if (tile.isMine()) {
                    if (tile.isFlagged()) {
                        correctlyFlaggedTiles--;
                    } else {
                        correctlyFlaggedTiles++;
                    }
                }

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
