package org.sioterino.minesweeper.utils.exceptions.game;

import org.sioterino.minesweeper.models.Board.Point;

public class PointCoordinateOutsideBoardException extends GameException {
    public PointCoordinateOutsideBoardException(Point p) {
        super("This cell (" + p.x + ", " + p.y + ") has already been revealed.\n");
    }
}
