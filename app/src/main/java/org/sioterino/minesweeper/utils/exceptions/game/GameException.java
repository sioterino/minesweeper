package org.sioterino.minesweeper.utils.exceptions.game;

import org.sioterino.minesweeper.models.Board.Point;

public class GameException extends RuntimeException {
    public GameException(String message) {
        super(message);
    }
}
