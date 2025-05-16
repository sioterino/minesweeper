package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.enums.ASCIIMenu;
import org.sioterino.minesweeper.enums.ConsoleColor;
import org.sioterino.minesweeper.repository.FileUserRepository;
import org.sioterino.minesweeper.services.UserService;
import org.sioterino.minesweeper.services.security.BCryptHashAlgorithm;
import org.sioterino.minesweeper.utils.InputHandler;
import org.sioterino.minesweeper.utils.Terminal;

import java.io.Console;
import java.util.Scanner;

public class UserController {

    private final Scanner scanner;
    private final UserService service = new UserService(new FileUserRepository(), new BCryptHashAlgorithm());

    public UserController(Scanner scanner) {
        this.scanner = scanner;
        redirect(ASCIIMenu.LOGIN);
    }

    public void start() {
        while (true) {
            handleUserInput();
        }
    }

    private void redirect(ASCIIMenu menu) {
        Terminal.clearConsole();
        System.out.println(menu);
    }

    private void handleUserInput() {

        System.out.print(ConsoleColor.BOLD + "> " + ConsoleColor.RESET);
        String input = scanner.nextLine();
        char choice = input.toLowerCase().charAt(0);

        switch (choice) {
            case 'x': safeExit(); break;
            case 'r': userRegister(); break;
            case 'l': userAuthenticate(); break;
            case 'g': playAsGuest(); break;
            default: safeWarn(input); break;
        }

    }

    private String getLoginInput(String message) {
        System.out.print(message);
        return InputHandler.username(scanner.nextLine());
    }

    private String getPasswordInput(String message) {
        System.out.print(message);

        Console console = System.console();

        if (console == null) {
            return InputHandler.password(scanner.nextLine());
        } else {
            return InputHandler.password(new String(console.readPassword()));
        }
    }

    private void safeExit() {
        System.out.println(ConsoleColor.YELLOW.fg() + "\nYou're about to exit the game.");
        System.out.print(ConsoleColor.RED.fg() + "Are you sure you want to continue? " + ConsoleColor.BOLD + ConsoleColor.BLUE.fg() + "(yes/no) " + ConsoleColor.RESET + ConsoleColor.GREEN.fg() + "[default = yes]: " + ConsoleColor.RESET);

        boolean exit = InputHandler.defaultYes(scanner.nextLine());

        if (exit) {
            System.out.println("\n\nGoodbye...\n");
            System.exit(0);
        }
    }

    private void userRegister() {
        try {
            System.out.println(ASCIIMenu.CREATE_NEW_ACCOUNT);

            String username = getLoginInput("Username: ");
            String password = getPasswordInput("Password: ");

            service.register(username, password);

            System.out.println(ConsoleColor.GREEN.fg() + "\nUser created successfully!");
            System.out.println(ConsoleColor.YELLOW.fg() + "Please login before continuing to play.\n" + ConsoleColor.RESET);
            userAuthenticate();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void userAuthenticate() {
        try {
            System.out.println(ASCIIMenu.AUTHENTICATE_ACCOUNT);
            String username = getLoginInput("Username: ");
            String password = getPasswordInput("Password: ");

            service.authenticate(username, password);

            redirect(ASCIIMenu.MAIN);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void playAsGuest() {
        redirect(ASCIIMenu.MAIN);
    }

    private void safeWarn(String input) {
        System.err.println(ConsoleColor.YELLOW.fg() + "\nPlease, insert only valid control choices.");
        System.err.println(ConsoleColor.RED.fg() + "\"" + input + "\" is not a valid input.\n" +    ConsoleColor.RESET);
    }

}
