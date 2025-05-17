package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.App;
import org.sioterino.minesweeper.services.UserService;
import org.sioterino.minesweeper.utils.InputHandler;
import org.sioterino.minesweeper.utils.enums.ConsoleColor;
import org.sioterino.minesweeper.utils.exceptions.InvalidInputException;

import java.io.Console;
import java.util.Scanner;

public abstract class Controller {

    public void start() {
        while (true) {
            handleUserInput();
        }
    }

    protected void mainMenu(Scanner scanner) {
        new MenuController(scanner).start();
    }

    protected void safeWarn(char input) {
        System.err.println(ConsoleColor.YELLOW.fg() + "\nPlease, insert only valid control choices.");
        System.err.println(ConsoleColor.RED.fg() + "\"" + input + "\" is not a valid input.\n" +    ConsoleColor.RESET);
    }

    protected void safeExit(Scanner scanner, UserService service) {
        System.out.println(ConsoleColor.YELLOW.fg() + "\nYou're about to exit the game.");
        System.out.print(ConsoleColor.RED.fg() + "Are you sure you want to continue? " + ConsoleColor.BOLD + ConsoleColor.BLUE.fg() + "(yes/no) " + ConsoleColor.RESET + ConsoleColor.GREEN.fg() + "[default = yes]: " + ConsoleColor.RESET);

        boolean exit = InputHandler.defaultYes(scanner.nextLine());

        if (exit) {
            if(App.player != null) service.logout(App.player.getUser());

            System.out.println("\n\nGoodbye...\n");
            System.exit(0);
        }
    }

    protected char choiceInput(Scanner scanner) {
        while (true) {
            System.out.print(ConsoleColor.BOLD + "> " + ConsoleColor.RESET);
            String input = scanner.nextLine();
            try {
                InputHandler.notBlank(input);
                return input.toLowerCase().charAt(0);
            } catch (InvalidInputException ignored) {}
        }
    }

    protected String getLoginInput(Scanner scanner, String message) {
        System.out.print(message);
        return InputHandler.username(scanner.nextLine());
    }

    protected String getPasswordInput(Scanner scanner, String message) {
        System.out.print(message);

        Console console = System.console();

        if (console == null) {
            return InputHandler.password(scanner.nextLine());
        } else {
            return InputHandler.password(new String(console.readPassword()));
        }
    }

    protected abstract void handleUserInput();

}
