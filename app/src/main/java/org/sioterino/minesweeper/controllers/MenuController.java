package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;
import org.sioterino.minesweeper.utils.enums.ConsoleColor;

import java.util.Scanner;

public class MenuController extends Controller {

    private final Scanner scanner;

    public MenuController(Scanner scanner) {
        this.scanner = scanner;
        Terminal.redirect(ASCIIMenu.MAIN);
    }

    @Override
    protected void handleUserInput() {
        char choice = choiceInput(scanner);

        switch (choice) {
            case 'q': safeExit(scanner); break;
            case 's': startGame(); break;
            case 'a': seeAccount(); break;
            case 'h': gameRules(); break;
            default: safeWarn(choice); break;
        }

    }

    private void startGame() {
        new GameController(scanner).start();
    }

    private void seeAccount() {
        new AccountController(scanner).start();
    }

    private void gameRules() {
        new RulesController(scanner).start();
    }

}
