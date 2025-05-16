package org.sioterino.minesweeper.exceptions;

import org.sioterino.minesweeper.enums.ConsoleColor;

public class UserAlreadyExistsException extends UserException {
    public UserAlreadyExistsException(String login) {
        super(ConsoleColor.RED.fg() + "User with \"" + login + "\" login name already exists.\n" + ConsoleColor.RESET);
    }
}
