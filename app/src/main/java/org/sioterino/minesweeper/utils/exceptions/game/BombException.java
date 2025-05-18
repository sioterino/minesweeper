package org.sioterino.minesweeper.utils.exceptions.game;

import org.sioterino.minesweeper.models.Board.Point;
import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class BombException extends GameException {
    public BombException(Point p) {
        super(ConsoleColor.RED.fg() + "Oops!" + ConsoleColor.BRIGHT_YELLOW.fg() +  " (" + p.toString() + ") " + ConsoleColor.RED.fg() + "is a BOMB!\n" + ConsoleColor.RESET);
    }
}
