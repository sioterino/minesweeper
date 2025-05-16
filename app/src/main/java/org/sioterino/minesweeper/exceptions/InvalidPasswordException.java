package org.sioterino.minesweeper.exceptions;

import org.sioterino.minesweeper.enums.ConsoleColor;

public class InvalidPasswordException extends UserException {
    public InvalidPasswordException(String login) {
        super(ConsoleColor.RED.fg() + "Password provided to user \"" + login + "\" is invalid.\n" + ConsoleColor.RESET);
    }
}
