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
        Terminal.redirect(ASCIIMenu.RULES);
        System.out.println(page.toString());
    }

    public RulesController(Scanner scanner, GameRulesReturnPage page, Difficulty gamemode) {
        this.scanner = scanner;
        this.page = page;
        this.gamemode = gamemode;
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
            case GameRulesReturnPage.MAIN_MENU -> mainMenu(scanner);
            case GameRulesReturnPage.SETTINGS -> new SettingsController(scanner).start();
            case GameRulesReturnPage.IN_GAME -> resumeGame();
        }
    }

    private void resumeGame() {
        new BoardController(scanner, gamemode);
    }

}
