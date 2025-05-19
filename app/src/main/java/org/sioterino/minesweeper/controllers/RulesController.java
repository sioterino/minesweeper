package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;
import org.sioterino.minesweeper.utils.enums.Difficulty;
import org.sioterino.minesweeper.utils.enums.GameRulesReturnPage;

import java.util.Scanner;

public class RulesController extends Controller {

    private final Scanner scanner;
    private final GameRulesReturnPage page;
    private final Difficulty gamemode;

    public RulesController(Scanner scanner, GameRulesReturnPage page) {
        this.scanner = scanner;
        this.page = page;
        this.gamemode = null;
        Terminal.redirect(ASCIIMenu.RULES, Difficulty.BEGINNER, Difficulty.INTERMEDIATE, Difficulty.EXPERT);
    }

    public RulesController(Scanner scanner, GameRulesReturnPage page, Difficulty gamemode) {
        this.scanner = scanner;
        this.page = page;
        this.gamemode = gamemode;
        Terminal.redirect(ASCIIMenu.RULES, Difficulty.BEGINNER, Difficulty.INTERMEDIATE, Difficulty.EXPERT);
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

    public void returnToPreviousPage() {
        switch (page) {
            case MAIN_MENU -> mainMenu(scanner);
            case SETTINGS -> new SettingsController(scanner).start();
            case IN_GAME ->  new GameController(scanner, gamemode).start();
        }
    }

}
