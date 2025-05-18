package org.sioterino.minesweeper.utils.exceptions;

import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(ConsoleColor.RED.fg() + message + ConsoleColor.RESET);
    }
}
