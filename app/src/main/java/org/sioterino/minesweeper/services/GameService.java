package org.sioterino.minesweeper.services;

import org.sioterino.minesweeper.models.Board;
import org.sioterino.minesweeper.models.Board.Point;
import org.sioterino.minesweeper.models.Cell;
import org.sioterino.minesweeper.utils.exceptions.game.*;

public class GameService {

    private final Board board;

    private boolean isGameOver;
    private boolean isWin;

    private int revealedCells;

    public GameService(int rows, int cols, int mines) {
        this.board = new Board(rows, cols, mines);

        this.isGameOver = false;
        this.isWin = false;

        this.revealedCells = 0;
    }

    private boolean checkWin() {
        int boardSize = board.getHeight() * board.getWidth();
        int mines = board.getMines();

        if (revealedCells == boardSize - mines) {
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

        Cell cell = board.getCell(p);

        if (cell.isRevealed()) {
            throw new CellAlreadyRevealedException(p);
        };

        if (cell.isFlagged()) {
            throw new CellIsFlaggedException(p);
        };

        revealCell(cell);

        if (cell.isMine()) {
            isWin = false;
            isGameOver = true;
            throw new BombException(p);
        }

        if (cell.getAdjacentMines() == 0) revealAdjacentCells(p);

        if (checkWin()) {
            throw new VictoryException();
        };

    }

    private void revealAdjacentCells(Point p) {

        for (int row = p.y - 1; row <= p.y + 1; row++) {
            for (int col = p.x - 1; col <= p.x + 1; col++) {

                if (board.isInside(row, col)) {

                    Cell neighbor = board.getCell(row, col);

                    if (!neighbor.isRevealed() && !neighbor.isMine()) {
                        revealCell(neighbor);
                        if (neighbor.getAdjacentMines() == 0) revealAdjacentCells(new Point(col, row));
                    }
                }

            }
        }

    }

    private void revealCell(Cell cell) {
        cell.reveal();
        revealedCells++;
    }

    public void toggleFlag(Point p) {
        if (board.isInside(p)) {
            Cell cell = board.getCell(p);
            if (!cell.isRevealed()) {
                cell.toggleFlag();
            } else {
                throw new CellAlreadyRevealedException(p);
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

}
