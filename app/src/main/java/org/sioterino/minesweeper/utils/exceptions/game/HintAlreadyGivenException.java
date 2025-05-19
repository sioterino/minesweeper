package org.sioterino.minesweeper.utils.exceptions.game;

import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class HintAlreadyGivenException extends GameException {
    public HintAlreadyGivenException() {
        super(ConsoleColor.BRIGHT_RED.fg() + "Only one hint allowed per game.\n" + ConsoleColor.RESET);
    }
}
