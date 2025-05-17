package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;

import java.util.Scanner;

public class GameController extends Controller {

    private Scanner scanner;

    public GameController(Scanner scanner) {
        this.scanner = scanner;
        Terminal.redirect(ASCIIMenu.BOARD);
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
