package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.models.Board.Point;
import org.sioterino.minesweeper.models.Tile;
import org.sioterino.minesweeper.services.GameService;
import org.sioterino.minesweeper.utils.Terminal;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;

public class BoardController {

    private final GameService gameService;
    private int PADDING;

    public BoardController(GameService gameService) {
        this.gameService = gameService;
    }

    protected void printYouWon() {
        Terminal.clearConsole();
        System.out.println(ASCIIMenu.YOU_WON);
        printBoard(true);
        System.out.println(ASCIIMenu.YOU_WON_SETTINGS);
    }

    protected void printYouLost(Point bomb) {
        Terminal.clearConsole();
        System.out.println(ASCIIMenu.YOU_LOST);
        printBoard(true);
        System.out.printf(ASCIIMenu.EXPLODED_BOMB_SETTINGS.toString(), bomb.toString());
    }

    protected void printInGame() {
        Terminal.clearConsole();
        System.out.println(ASCIIMenu.MINESWEEPER);
        printBoard(false);
        System.out.println(gameStats());
        System.out.println(ASCIIMenu.INSTRUCTIONS);
    }

    private void printBoard(boolean revealAll) {
        final int width = gameService.getBoard().getWidth();
        final int height = gameService.getBoard().getHeight();

        PADDING = (135 - (width * 2 + 5)) / 2;

        String[] rows = rows(width, height, revealAll);

        System.out.println(borderTop(width));
        for (String row : rows) {
            System.out.println(row);
        }
        System.out.println(borderBottom(width));
        System.out.println(footer(width));
        System.out.println("\n\n\n");

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
        sb.append("Mines : [ ").append(gameService.getBoard().getMines()).append(" ]       ");
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
