package org.sioterino.minesweeper.utils.exceptions.game;

import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class VictoryException extends GameException {
    public VictoryException() {
        super(ConsoleColor.GREEN.fg() + "You just won! Congratulations!\n" + ConsoleColor.RESET);
    }
}
