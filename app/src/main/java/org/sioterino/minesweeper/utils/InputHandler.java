package org.sioterino.minesweeper.utils;

import org.sioterino.minesweeper.utils.enums.ConsoleColor;
import org.sioterino.minesweeper.utils.exceptions.InvalidInputException;

public class InputHandler {

    public static boolean defaultYes(String input) {
        return input.equals("yes") || input.equals("y") || input.isBlank();
    }

    public static String username(String input) {

        if (input.contains(" ")) {
            throw new InvalidInputException(ConsoleColor.RED.fg() + "Usernames can't have spaces to them.\n" + ConsoleColor.RESET);
        }

        if (!input.matches("^[a-zA-Z0-9_]+$")) {
            throw new InvalidInputException(ConsoleColor.RED.fg() + "Usernames can only contain letters, numbers, and underscores.\n" + ConsoleColor.RESET);
        }

        if (input.length() < 4) {
            throw new InvalidInputException(ConsoleColor.RED.fg() + "Usernames need to be longer than 4 characters.\n" + ConsoleColor.RESET);
        }

        if (input.length() > 20) {
            throw new InvalidInputException(ConsoleColor.RED.fg() + "Usernames can't be longer than 20 characters.\n" + ConsoleColor.RESET);
        }

        return input;
    }

    public static String password(String input) {

        if (input.contains(" ")) {
            throw new InvalidInputException(ConsoleColor.RED.fg() + "Passwords can't have spaces to them.\n" + ConsoleColor.RESET);
        }

        if (input.length() < 8) {
            throw new InvalidInputException(ConsoleColor.RED.fg() + "Passwords need to be longer than 8 characters.\n" + ConsoleColor.RESET);
        }

        return input;
    }

}
