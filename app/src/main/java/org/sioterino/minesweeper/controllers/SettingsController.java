package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;
import org.sioterino.minesweeper.utils.enums.Difficulty;
import org.sioterino.minesweeper.utils.enums.GameRulesReturnPage;

import java.util.Scanner;

public class SettingsController extends Controller {

    private final Scanner scanner;

    public SettingsController(Scanner scanner) {
        this.scanner = scanner;
        Terminal.redirect(ASCIIMenu.GAME_SETTINGS);
    }


    @Override
    protected void handleUserInput() {
        char choice = choiceInput(scanner);

        switch (choice) {
            case 'x': mainMenu(scanner); break;
            case 'b': beginner(); break;
            case 'i': intermediate(); break;
            case 'e': expert(); break;
            case 'h': gameRules(); break;
            default: safeWarn(choice);
        }
    }

    private void beginner() {
        new BoardController(scanner, Difficulty.BEGINNER);
    }

    private void intermediate() {
        new BoardController(scanner, Difficulty.INTERMEDIATE);
    }

    private void expert() {
        new BoardController(scanner, Difficulty.EXPERT);
    }

    private void gameRules() {
        new RulesController(scanner, GameRulesReturnPage.SETTINGS);
    }

}
