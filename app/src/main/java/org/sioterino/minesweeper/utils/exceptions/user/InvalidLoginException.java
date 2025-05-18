package org.sioterino.minesweeper.utils.exceptions.user;

import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class InvalidLoginException extends UserException {
    public InvalidLoginException(String login) {
        super(ConsoleColor.RED.fg() + "Login name \"" + login + "\" is invalid.\n" + ConsoleColor.RESET);
    }
}
