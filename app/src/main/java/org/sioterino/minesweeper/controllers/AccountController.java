package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.App;
import org.sioterino.minesweeper.services.UserService;
import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;

import java.util.Scanner;

public class AccountController extends Controller {

    private final Scanner scanner;
    private final UserService service = App.userController.service;

    public AccountController(Scanner scanner) {
        this.scanner = scanner;
        Terminal.redirect(ASCIIMenu.ACCOUNT, App.player);
    }

    @Override
    protected void handleUserInput() {
        char choice = choiceInput(scanner);

        switch (choice) {
            case 'x' -> mainMenu(scanner);
            case 'l' -> logout();
            case 'u' -> editUsername();
            case 'p' -> editPassword();
            default -> safeWarn(choice);
        }
    }

    private void logout() {
        service.logout(App.player.getUser());
        new UserController(scanner).start();
    }

    private void editUsername() {
        try {
            System.out.println(ASCIIMenu.EDIT_USERNAME);
            String oldLogin = getLoginInput(scanner, "Current username: ");
            String newLogin = getLoginInput(scanner, "New username: ");
            String password = getPasswordInput(scanner, "Password: ");

            App.player = service.editLoginName(oldLogin, newLogin, password);

            mainMenu(scanner);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void editPassword() {
        try {
            System.out.println(ASCIIMenu.EDIT_USERNAME);
            String login = getLoginInput(scanner, "Username: ");
            String oldPassword = getPasswordInput(scanner, "Current password: ");
            String newPassword = getPasswordInput(scanner, "New password: ");

            App.player = service.editPassword(login, newPassword, oldPassword);

            mainMenu(scanner);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
