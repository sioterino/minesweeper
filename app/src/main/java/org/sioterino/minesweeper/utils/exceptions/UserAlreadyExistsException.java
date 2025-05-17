package org.sioterino.minesweeper.utils.exceptions;

import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class UserAlreadyExistsException extends UserException {
    public UserAlreadyExistsException(String login) {
        super(ConsoleColor.RED.fg() + "User with \"" + login + "\" login name already exists.\n" + ConsoleColor.RESET);
    }
}
