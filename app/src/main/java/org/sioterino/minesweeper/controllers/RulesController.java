package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.models.Board;
import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;
import org.sioterino.minesweeper.utils.enums.GameRulesReturnPage;

import java.util.Scanner;

public class RulesController extends Controller {

    private final Scanner scanner;
    private final GameRulesReturnPage page;

    public RulesController(Scanner scanner, GameRulesReturnPage page) {
        this.scanner = scanner;
        this.page = page;
        Terminal.redirect(ASCIIMenu.RULES);
    }

    @Override
    protected void handleUserInput() {
        char choice = choiceInput(scanner);

        if (choice == 'x') {
            returnToPreviousPage();
        } else {
            safeWarn(choice);
        }

    }

    private void returnToPreviousPage() {
        switch (page) {
            case MAIN_MENU -> mainMenu(scanner);
            case SETTINGS -> new SettingsController(scanner).start();
            case IN_GAME -> mainMenu(scanner);
        }
    }

}
