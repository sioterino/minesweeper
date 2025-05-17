package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.App;
import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;
import org.sioterino.minesweeper.utils.enums.ConsoleColor;

import java.util.Scanner;

public class AccountController extends Controller {

    private final Scanner scanner;

    public AccountController(Scanner scanner) {
        this.scanner = scanner;

        Terminal.redirect(ASCIIMenu.ACCOUNT, App.player);
    }

    @Override
    protected void handleUserInput() {
        char choice = choiceInput(scanner);

        switch (choice) {
            case 'x': mainMenu(scanner); break;
            case 'l': logout(); break;
            case 'u': editUsername(); break;
            case 'p': editPassword(); break;
            default: safeWarn(choice); break;
        }
    }

    private void logout() {
        System.out.println("logout");
    }

    private void editUsername() {
        System.out.println("edit username");
    }

    private void editPassword() {
        System.out.println("edit password");
    }

}
