package org.sioterino.minesweeper.utils.exceptions;

import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class InvalidPasswordException extends UserException {
    public InvalidPasswordException(String login) {
        super(ConsoleColor.RED.fg() + "Password provided to user \"" + login + "\" is invalid.\n" + ConsoleColor.RESET);
    }
}
