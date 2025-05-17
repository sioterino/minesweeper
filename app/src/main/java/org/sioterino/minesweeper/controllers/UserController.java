package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.App;
import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;
import org.sioterino.minesweeper.utils.enums.ConsoleColor;
import org.sioterino.minesweeper.models.Player;
import org.sioterino.minesweeper.repository.FileUserRepository;
import org.sioterino.minesweeper.services.UserService;
import org.sioterino.minesweeper.services.security.BCryptHashAlgorithm;

import java.util.Scanner;

public class UserController extends Controller {

    private final Scanner scanner;
    public final UserService service = new UserService(new FileUserRepository(), new BCryptHashAlgorithm());

    public UserController(Scanner scanner) {
        this.scanner = scanner;
        Terminal.redirect(ASCIIMenu.LOGIN);
    }

    @Override
    protected void handleUserInput() {
        char choice = choiceInput(scanner);

        switch (choice) {
            case 'q': safeExit(scanner, service); break;
            case 'r': userRegister(); break;
            case 'l': userAuthenticate(); break;
            case 'g': playAsGuest(); break;
            default: safeWarn(choice); break;
        }
    }

    private void userRegister() {
        try {
            System.out.println(ASCIIMenu.CREATE_NEW_ACCOUNT);

            String username = getLoginInput(scanner, "Username: ");
            String password = getPasswordInput(scanner, "Password: ");

            service.register(username, password);

            System.out.println(ConsoleColor.GREEN.fg() + "\nUser created successfully!");
            System.out.println(ConsoleColor.YELLOW.fg() + "Please login before continuing to play.\n" + ConsoleColor.RESET);
            userAuthenticate();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void userAuthenticate() {
        try {
            System.out.println(ASCIIMenu.AUTHENTICATE_ACCOUNT);
            String username = getLoginInput(scanner, "Username: ");
            String password = getPasswordInput(scanner, "Password: ");

            service.authenticate(username, password);

            App.player = service.authenticate(username, password);

            mainMenu(scanner);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void playAsGuest() {
        App.player = Player.guestPlayer();
        mainMenu(scanner);
    }
    
}
