package org.sioterino.minesweeper.utils;

import org.sioterino.minesweeper.utils.exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {

    public static boolean defaultYes(String input) {
        return input.equals("yes") || input.equals("y") || input.isBlank();
    }

    public static String username(String input) {

        if (input.contains(" ")) {
            throw new InvalidInputException("Usernames can't have spaces to them.\n");
        }

        if (!input.matches("^[a-zA-Z0-9_]+$")) {
            throw new InvalidInputException("Usernames can only contain letters, numbers, and underscores.\n");
        }

        if (input.length() < 4) {
            throw new InvalidInputException("Usernames need to be longer than 4 characters.\n");
        }

        if (input.length() > 20) {
            throw new InvalidInputException("Usernames can't be longer than 20 characters.\n");
        }

        return input;
    }

    public static String password(String input) {

        if (input.contains(" ")) {
            throw new InvalidInputException("Passwords can't have spaces to them.\n");
        }

        if (input.length() < 8) {
            throw new InvalidInputException("Passwords need to be longer than 8 characters.\n");
        }

        return input;
    }

    public static String notBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new InvalidInputException("Input cannot be blank or just spaces.\n");
        }
        return input.trim();
    }

    public static String[] gameInput(String input) {

        input = input.replaceAll("[^a-zA-Z0-9]", "");

        List<String> parts = new ArrayList<>();

        Matcher matcher = Pattern.compile("[a-zA-Z]+|\\d+").matcher(input);
        while (matcher.find()) {
            parts.add(matcher.group());
        }

        if (parts.size() < 2 ||parts.size() > 3) {
            throw new InvalidInputException("Incorrect board input.\n");
        }

        String first = parts.get(0);
        String second = parts.get(1);
        String third = parts.size() == 3 ? parts.get(2) : "";

        if (first.matches("\\d+") || second.matches("[a-zA-Z]") || third.matches("\\d+")) {
            throw new InvalidInputException("Incorrect board input.\n");
        }

        if (third.isBlank()) {
            return new String[]{first, second};
        } else {
            return new String[]{first, second, third};
        }
    }


}
