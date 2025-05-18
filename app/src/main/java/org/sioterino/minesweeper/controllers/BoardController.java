package org.sioterino.minesweeper.controllers;

import org.sioterino.minesweeper.models.Cell;
import org.sioterino.minesweeper.services.GameService;

import java.util.Scanner;

public class BoardController extends Controller {

    private final int PADDING = 12;

    private final GameService gameService;
    private final Scanner scanner;

    public BoardController(Scanner scanner, int rows, int cols, int mines) {
        this.gameService = new GameService(rows, cols, mines);
        this.scanner = scanner;
    }

    @Override
    protected void handleUserInput() {

    }

    public void printBoard(boolean revealAll) {
        final int width = gameService.getBoard().getWidth();
        final int height = gameService.getBoard().getHeight();

        String[] rows = rows(width, height, revealAll);

        System.out.println();
        System.out.println(borderTop(width));
        System.out.println(emptyLine(width));
        for (String row : rows) {
            System.out.println(row);
        }
        System.out.println(borderBottom(width));
        System.out.println(footer(width));
        System.out.println();

    }

    private String borderTop(int width) {
        StringBuilder sb = new StringBuilder();

        sb.append(" ".repeat(PADDING));
        sb.append("┌");
        sb.append("─".repeat(width * 2 + 1));
        sb.append("┐");

        return sb.toString();
    }

    private String borderBottom(int width) {
        StringBuilder sb = new StringBuilder();

        sb.append(" ".repeat(PADDING));
        sb.append("└");
        sb.append("─".repeat(width * 2 + 1));
        sb.append("┘");

        return sb.toString();
    }

    private String emptyLine(int width) {
        StringBuilder sb = new StringBuilder();

        sb.append(" ".repeat(PADDING));
        sb.append("│");
        sb.append("─".repeat(width * 2 + 1));
        sb.append("│");

        return sb.toString();
    }

    private String footer(int width) {
        StringBuilder sb = new StringBuilder();

        sb.append(" ".repeat(PADDING + 2));

        for (int i = 0; i < width; i++) {
            sb.append((char) ('A' + i));
            sb.append(" ");
        }

        return sb.toString();
    }

    private String[] rows(int width, int height, boolean revealAll) {

        String[] rows = new String[height * 2 + 1];

        for (int row = 0; row < width; row++) {

            StringBuilder sb = new StringBuilder();

            sb.append(" ".repeat(PADDING));
            sb.append("│");

            for (int col = 0; col < height; col++) {

                Cell cell = gameService.getBoard().getGrid()[row][col];

                if (revealAll && !cell.isRevealed()) cell.reveal();

                sb.append(cell.toString()).append(" ");

            }

            sb.append("│ ");
            sb.append(row + 1);

            sb.append(emptyLine(width));

            rows[row] = sb.toString();
        }

        return rows;
    }

}
