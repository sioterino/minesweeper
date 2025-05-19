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
        Terminal.redirect(ASCIIMenu.GAME_SETTINGS, Difficulty.BEGINNER, Difficulty.INTERMEDIATE, Difficulty.EXPERT);
    }


    @Override
    protected void handleUserInput() {
        char choice = choiceInput(scanner);

        switch (choice) {
            case 'x' -> mainMenu(scanner);
            case 'b' -> beginner();
            case 'i' -> intermediate();
            case 'e' -> expert();
            case 'h' -> gameRules();
            default -> safeWarn(choice);
        }
    }

    private void beginner() {
        new GameController(scanner, Difficulty.BEGINNER).start();
    }

    private void intermediate() {
        new GameController(scanner, Difficulty.INTERMEDIATE).start();
    }

    private void expert() {
        new GameController(scanner, Difficulty.EXPERT).start();
    }

    private void gameRules() {
        new RulesController(scanner, GameRulesReturnPage.SETTINGS).start();
    }

}
