package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.App;
import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;
import org.sioterino.minesweeper.utils.enums.GameRulesReturnPage;

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
            case 'q' -> safeExit(scanner, App.userController.service);
            case 's' -> startGame();
            case 'a' -> seeAccount();
            case 'h' -> seeGameRules(scanner, GameRulesReturnPage.MAIN_MENU);
            default -> safeWarn(choice);
        }

    }

    private void startGame() {
        new SettingsController(scanner).start();
    }

    private void seeAccount() {
        new AccountController(scanner).start();
    }

}
