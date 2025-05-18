package org.sioterino.minesweeper.utils.exceptions.game;

import org.sioterino.minesweeper.models.Board.Point;

public class CellIsFlaggedException extends GameException {
    public CellIsFlaggedException(Point p) {
        super("Cell (" + p.x + ", " + p.y + ") can't be revealed, for it has been flagged.\n");
    }
}
