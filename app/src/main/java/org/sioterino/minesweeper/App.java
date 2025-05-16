package org.sioterino.minesweeper;

import org.sioterino.minesweeper.controllers.UserController;
import org.sioterino.minesweeper.models.Player;

import java.util.Scanner;

public class App {

    public static Player player = null;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        UserController userController = new UserController(scanner);
        userController.start();

        scanner.close();

    }
}
