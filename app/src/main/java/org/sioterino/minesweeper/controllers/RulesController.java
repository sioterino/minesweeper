package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;
import org.sioterino.minesweeper.utils.enums.ConsoleColor;

import java.util.Scanner;

public class RulesController extends Controller {

    private final Scanner scanner;

    public RulesController(Scanner scanner) {
        this.scanner = scanner;
        Terminal.redirect(ASCIIMenu.RULES);
    }

    @Override
    protected void handleUserInput() {
        char choice = choiceInput(scanner);

        if (choice == 'x') {
            mainMenu(scanner);
        } else {
            safeWarn(choice);
        }

    }
}
