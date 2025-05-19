package org.sioterino.minesweeper.utils.exceptions.game;

import org.sioterino.minesweeper.models.Board.Point;
import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class TileIsFlaggedException extends GameException {
    public TileIsFlaggedException(Point p) {
        super(ConsoleColor.RED.fg() + "Tile" + ConsoleColor.BRIGHT_YELLOW.fg() +  " (" + p.toString() + ") " + ConsoleColor.RED.fg() + "can't be revealed, for it has been flagged.\n" + ConsoleColor.RESET);
    }
}
