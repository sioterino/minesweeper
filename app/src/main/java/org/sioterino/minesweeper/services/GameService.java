package org.sioterino.minesweeper.services;

import org.sioterino.minesweeper.models.Board;
import org.sioterino.minesweeper.models.Board.Position;
import org.sioterino.minesweeper.models.Cell;

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

    public String reveal(Position p) {

        if (!board.isInside(p)) return "";

        Cell cell = board.getCell(p);
        if (cell.isRevealed()) return "";

        revealCell(cell);

        if (cell.isMine()) return "";

        if (cell.getAdjacentMines() == 0) revealAdjacentCells(p);

        if (checkWin()) return "";

        return "Cell revealed.";
    }

    public void revealAdjacentCells(Position p) {

        for (int i = p.x - 1; i <= p.x + 1; i++) {
            for (int j = p.y - 1; j <= p.y + 1; j++) {

                if (board.isInside(new Position(i, j))) {

                    Cell neighbor = board.getCell(new Position(i, j));

                    if (!neighbor.isRevealed() && !neighbor.isMine()) {
                        revealCell(neighbor);
                        if (neighbor.getAdjacentMines() == 0) revealAdjacentCells(new Position(i, j));
                    }
                }

            }
        }

    }

    private void revealCell(Cell cell) {
        cell.reveal();
        revealedCells++;
    }

    public void toggleFlag(Position p) {
        if (board.isInside(p)) {
            Cell cell = board.getCell(p);
            if (!cell.isRevealed()) cell.toggleFlag();
        }
    }

    public boolean isGameOver() { return isGameOver; }
    public boolean isWin() { return isWin; }
    public Board getBoard() { return board; }

}
