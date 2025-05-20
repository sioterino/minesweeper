package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.App;
import org.sioterino.minesweeper.models.Board.Point;
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

    private final Difficulty gamemode;

    private GameService gameService;
    protected final BoardController boardController;

    private final Scanner scanner;
    private final Stack<Point> history;

    private final Map<Character, Runnable> commandMap;

    public GameController(Scanner scanner, Difficulty gamemode) {
        this.gameService = new GameService(gamemode);
        this.boardController = new BoardController(this.gameService);

        this.scanner = scanner;
        this.gamemode = gamemode;

        this.history = new Stack<>();

        commandMap = Map.of(
                'x', () -> mainMenu(scanner),
                'q', () -> safeExit(scanner, App.userController.service),
                'r', this::restartGame,
                'h', () -> seeGameRules(scanner, this)
        );

        boardController.printInGame();
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
            boardController.printInGame();

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

            boardController.printInGame();

        } catch (VictoryException ignored) {
            boardController.printYouWon();
            updatePlayerStats();

        } catch (BombException ignored) {
            boardController.printYouLost(history.peek());
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
        boardController.printInGame();
    }

    private void updatePlayerStats() {
        if (gameService.isWin()) {
            App.player.increaseWins();
        } else {
            App.player.increaseLosses();
        }
    }

}
