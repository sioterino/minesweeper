package org.sioterino.minesweeper.utils.exceptions.user;

import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String login) {
        super(ConsoleColor.RED.fg() + "User with \"" + login + "\" login name not found.\n" + ConsoleColor.RESET);
    }
}
