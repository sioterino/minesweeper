package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.App;
import org.sioterino.minesweeper.models.Board.Point;
import org.sioterino.minesweeper.models.Cell;
import org.sioterino.minesweeper.services.GameService;
import org.sioterino.minesweeper.utils.InputHandler;
import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;
import org.sioterino.minesweeper.utils.enums.ConsoleColor;
import org.sioterino.minesweeper.utils.enums.Difficulty;
import org.sioterino.minesweeper.utils.enums.GameRulesReturnPage;
import org.sioterino.minesweeper.utils.exceptions.InvalidInputException;
import org.sioterino.minesweeper.utils.exceptions.game.BombException;
import org.sioterino.minesweeper.utils.exceptions.game.VictoryException;

import java.util.Scanner;

public class BoardController extends Controller {

    private int PADDING;
    private final Difficulty gamemode;

    private final GameService gameService;
    private final Scanner scanner;

    public BoardController(Scanner scanner, Difficulty gamemode) {
        this.gameService = new GameService(gamemode.getRows(), gamemode.getCols(), gamemode.getMines());
        this.scanner = scanner;
        this.gamemode = gamemode;

        Terminal.clearConsole();
        printBoard(false);
    }

    @Override
    protected void handleUserInput() {

        String input = getInput();

        if (input.contains("hint")) {
            giveHint();
            return;
        }

        if (input.trim().length() == 1) {
            char choice = input.charAt(0);

            if (choice == 'x') {
                mainMenu(scanner);
                return;
            }

            if (choice == 'q') {
                safeExit(scanner, App.userController.service);
                return;
            }

            if (choice == 'r') {
                restartGame();
                return;
            }

            if (choice == 'h') {
                seeRules();
                return;
            }
        }

        try {
            String[] commands = InputHandler.gameInput(input);
            Point p = Point.parse(commands[0], commands[1]);

            if (commands.length == 2) {
                gameService.reveal(p);
            } else {
                gameService.toggleFlag(p);
            }

            Terminal.clearConsole();
            printBoard(false);

        } catch (VictoryException e) {
            Terminal.clearConsole();
            System.out.println(e.getMessage());

        } catch (BombException e) {
            Terminal.clearConsole();
            printBoard(true);
            System.out.println(e.getMessage());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private String getInput() {
        while (true) {
            System.out.print(ConsoleColor.BOLD + "> " + ConsoleColor.RESET);
            String input = scanner.nextLine();
            try {
                return InputHandler.notBlank(input);
            } catch (InvalidInputException ignored) {}
        }
    }

    private void restartGame() {}

    private void giveHint() {
        System.out.println("super duper hint");
    }

    private void seeRules() {
        new RulesController(scanner, GameRulesReturnPage.IN_GAME, gamemode).start();
    }

    private void printBoard(boolean revealAll) {
        final int width = gameService.getBoard().getWidth();
        final int height = gameService.getBoard().getHeight();

        PADDING = (135 - (width * 2 + 5)) / 2;

        String[] rows = rows(width, height, revealAll);

        System.out.println(ASCIIMenu.BOARD);
        System.out.println(borderTop(width));
        System.out.println(emptyLine(width));
        for (String row : rows) {
            System.out.println(row);
            System.out.println(emptyLine(width));
        }
        System.out.println(borderBottom(width));
        System.out.println(footer(width));
        System.out.println("\n\n\n");
        System.out.println(ASCIIMenu.INSTRUCTIONS);

    }

    private String borderTop(int width) {

        return " ".repeat(PADDING) +
                "┌" +
                "─".repeat(width * 2 + 1) +
                "┐";
    }

    private String borderBottom(int width) {

        return " ".repeat(PADDING) +
                "└" +
                "─".repeat(width * 2 + 1) +
                "┘";
    }

    private String emptyLine(int width) {

        return " ".repeat(PADDING) +
                "│" +
                " ".repeat(width * 2 + 1) +
                "│";
    }

    private String footer(int width) {
        StringBuilder sb = new StringBuilder();

        sb.append(" ".repeat(PADDING + 2));

        for (int i = 0; i < width; i++) {
            char letter;

            if (i < 26) {
                letter = (char) ('a' + i);
            } else {
                letter = (char) ('A' + (i - 26));
            }

            sb.append(letter);
            sb.append(" ");
        }

        return sb.toString();
    }

    private String[] rows(int width, int height, boolean revealAll) {

        String[] rows = new String[height];

        for (int row = 0; row < height; row++) {

            StringBuilder sb = new StringBuilder();

            sb.append(" ".repeat(PADDING));
            sb.append("│ ");

            for (int col = 0; col < width; col++) {

                Cell cell = gameService.getBoard().getCell(col, row);

                if (revealAll && !cell.isRevealed()) cell.reveal();

                sb.append(cell.toString()).append(" ");

            }

            sb.append("│ ");
            sb.append(row + 1);

            rows[row] = sb.toString();
        }

        return rows;
    }

}
