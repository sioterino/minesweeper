package org.sioterino.minesweeper.utils.exceptions.game;

import org.sioterino.minesweeper.models.Board.Point;

public class BombException extends GameException {
    public BombException(Point p) {
        super("Oops! (" + p.x + ", " + p.y + ") is a BOMB!\n");
    }
}
