package org.sioterino.minesweeper.exceptions;

import org.sioterino.minesweeper.enums.ConsoleColor;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String login) {
        super(ConsoleColor.RED.fg() + "User with \"" + login + "\" login name not found.\n" + ConsoleColor.RESET);
    }
}
