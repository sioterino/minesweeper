package org.sioterino.minesweeper.utils.exceptions.game;

public class VictoryException extends GameException {
    public VictoryException() {
        super("You just won! Congratulations!");
    }
}
