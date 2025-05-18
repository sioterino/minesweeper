package org.sioterino.minesweeper;

import org.sioterino.minesweeper.controllers.BoardController;
import org.sioterino.minesweeper.controllers.UserController;
import org.sioterino.minesweeper.models.Player;
import org.sioterino.minesweeper.utils.Terminal;

import java.util.Scanner;

public class App {

    public static Player player = null;
    public static UserController userController;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

//        userController = new UserController(scanner);
//        userController.start();

        Terminal.clearConsole();
        new BoardController(scanner, 10, 20, 40).printBoard(true);

        scanner.close();
    }

}
