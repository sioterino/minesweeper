package org.sioterino.minesweeper.utils.exceptions.game;

import org.sioterino.minesweeper.models.Board.Point;
import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class CellIsFlaggedException extends GameException {
    public CellIsFlaggedException(Point p) {
        super(ConsoleColor.RED.fg() + "Cell" + ConsoleColor.BRIGHT_YELLOW.fg() +  " (" + p.toString() + ") " + ConsoleColor.RED.fg() + "can't be revealed, for it has been flagged.\n" + ConsoleColor.RESET);
    }
}
