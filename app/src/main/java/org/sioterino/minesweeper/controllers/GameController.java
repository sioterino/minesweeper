package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.App;
import org.sioterino.minesweeper.models.Board.Point;
import org.sioterino.minesweeper.models.Player;
import org.sioterino.minesweeper.models.Tile;
import org.sioterino.minesweeper.services.GameService;
import org.sioterino.minesweeper.utils.*;
import org.sioterino.minesweeper.utils.enums.*;
import org.sioterino.minesweeper.utils.exceptions.InvalidInputException;
import org.sioterino.minesweeper.utils.exceptions.game.BombException;
import org.sioterino.minesweeper.utils.exceptions.game.HintAlreadyGivenException;
import org.sioterino.minesweeper.utils.exceptions.game.VictoryException;

import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class GameController extends Controller {

    private int PADDING;
    private final Difficulty gamemode;

    private final Stack<Point> history;

    private GameService gameService;
    private final Scanner scanner;

    private final Map<Character, Runnable> commandMap;

    public GameController(Scanner scanner, Difficulty gamemode) {
        this.gameService = new GameService(gamemode);
        this.scanner = scanner;
        this.gamemode = gamemode;

        this.history = new Stack<>();

        commandMap = Map.of(
                'x', () -> mainMenu(scanner),
                'q', () -> safeExit(scanner, App.userController.service),
                'r', this::restartGame,
                'h', this::seeRules
        );

        printInGame();
    }

    @Override
    protected void handleUserInput() {

        String input = getInput();

        if (giveHint(input)) return;
        if (commandInput(input)) return;

        gameMoveInput(input);
    }

    private boolean giveHint(String input) {
        if (!input.contains("hint")) return false;

        try {
            gameService.hint();
            printInGame();

        } catch (HintAlreadyGivenException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }

    private boolean commandInput(String input) {

        if (input.trim().length() == 1) {
            char choice = input.charAt(0);

            Runnable action = commandMap.get(choice);
            if (action != null) {
                action.run();
                return true;
            }

        }
        return false;
    }

    private void gameMoveInput(String input) {
        if (input.contains("hint")) return;

        try {
            String[] movement = InputHandler.gameInput(input);
            Point p = Point.parse(movement[0], movement[1]);
            history.push(p);

            if (movement.length == 2) {
                gameService.reveal(p);
            } else {
                gameService.toggleFlag(p);
            }

            printInGame();

        } catch (VictoryException ignored) {
            printYouWon();
            updatePlayerStats();

        } catch (BombException ignored) {
            printYouLost();
            updatePlayerStats();

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

    private void restartGame() {
        gameService = new GameService(gamemode);
        printInGame();
    }

    private void seeRules() {
        new RulesController(scanner, GameRulesReturnPage.IN_GAME, this).start();
    }

    private void updatePlayerStats() {
        if (gameService.isWin()) {
            App.player.increaseWins();
        } else {
            App.player.increaseLosses();
        }
    }

    private void printBoard(boolean revealAll) {
        final int width = gameService.getBoard().getWidth();
        final int height = gameService.getBoard().getHeight();

        PADDING = (135 - (width * 2 + 5)) / 2;

        String[] rows = rows(width, height, revealAll);

        System.out.println(borderTop(width));
//        System.out.println(emptyLine(width));
        for (String row : rows) {
            System.out.println(row);
//            System.out.println(emptyLine(width));
        }
        System.out.println(borderBottom(width));
        System.out.println(footer(width));
        System.out.println("\n\n\n");

    }

    private void printYouWon() {
        Terminal.clearConsole();
        System.out.println(ASCIIMenu.YOU_WON);
        printBoard(true);
        System.out.println(ASCIIMenu.YOU_WON_SETTINGS);
    }

    private void printYouLost() {
        Terminal.clearConsole();
        System.out.println(ASCIIMenu.YOU_LOST);
        printBoard(true);
        System.out.printf(ASCIIMenu.EXPLODED_BOMB_SETTINGS.toString(), history.peek().toString());
    }

    protected void printInGame() {
        Terminal.clearConsole();
        System.out.println(ASCIIMenu.MINESWEEPER);
        printBoard(false);
        System.out.println(gameStats());
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

    private String gameStats() {

        StringBuilder sb = new StringBuilder();
        sb.append("Tiles : [ ").append(gameService.getBoard().getWidth() * gameService.getBoard().getHeight()).append(" ]       ");
        sb.append("Revealed : [ ").append(gameService.getRevealedTiles()).append(" ]       ");
        sb.append("Flagged : [ ").append(gameService.getFlaggedTiles()).append(" ]\n");

        int padding = (135 - sb.length()) / 2;
        padding = Math.max(padding, 0);

        return " ".repeat(padding) + sb;

    }

    private String[] rows(int width, int height, boolean revealAll) {

        String[] rows = new String[height];

        for (int row = 0; row < height; row++) {

            StringBuilder sb = new StringBuilder();

            sb.append(" ".repeat(PADDING));
            sb.append("│ ");

            for (int col = 0; col < width; col++) {

                Tile tile = gameService.getBoard().getTile(col, row);

                if (revealAll && !tile.isRevealed()) tile.reveal();

                sb.append(tile.toString()).append(" ");

            }

            sb.append("│ ");
            sb.append(row + 1);

            rows[row] = sb.toString();
        }

        return rows;
    }

}
