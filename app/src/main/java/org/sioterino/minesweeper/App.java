package org.sioterino.minesweeper;

import org.sioterino.minesweeper.controllers.UserController;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        UserController userController = new UserController(scanner);
        userController.start();

        scanner.close();

    }
}
