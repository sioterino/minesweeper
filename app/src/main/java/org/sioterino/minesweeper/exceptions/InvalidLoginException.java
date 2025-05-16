package org.sioterino.minesweeper.exceptions;

import org.sioterino.minesweeper.enums.ConsoleColor;

public class InvalidLoginException extends UserException {
    public InvalidLoginException(String login) {
        super(ConsoleColor.RED.fg() + "Login name \"" + login + "\" is invalid.\n" + ConsoleColor.RESET);
    }
}
