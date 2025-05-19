package org.sioterino.minesweeper.utils.exceptions.game;

import org.sioterino.minesweeper.models.Board.Point;
import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class TileAlreadyRevealedException extends GameException {
    public TileAlreadyRevealedException(Point p) {
        super(ConsoleColor.RED.fg() + "Tile" + ConsoleColor.BRIGHT_YELLOW.fg() +  " (" + p.toString() + ") " + ConsoleColor.RED.fg() + "has already been revealed.\n" + ConsoleColor.RESET);
    }
}
