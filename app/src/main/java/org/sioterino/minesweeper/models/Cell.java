package org.sioterino.minesweeper.models;

import org.sioterino.minesweeper.utils.enums.ConsoleColor;

public class Cell {

    private boolean isMine;
    private boolean isRevealed;
    private boolean isFlagged;
    private int adjacentMines;

    public Cell() {
        isMine = false;
        isRevealed = false;
        isFlagged = false;
        adjacentMines = 0;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void reveal() {
        isRevealed = true;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void toggleFlag() {
        isFlagged = !isFlagged;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void increaseAdjacentMines() {
        adjacentMines++;
    }

    private String getColor() {
        StringBuilder color = new StringBuilder(ConsoleColor.BOLD);

        switch (adjacentMines) {
            case 1: color.append(ConsoleColor.BLUE.fg());
            case 2: color.append(ConsoleColor.GREEN.fg());
            case 3: color.append(ConsoleColor.RED.fg());
            case 4: color.append(ConsoleColor.CYAN.fg());
            case 5: color.append(ConsoleColor.BLUE.fg());
            case 6: color.append(ConsoleColor.GREEN.fg());
            case 7: color.append(ConsoleColor.CYAN.fg());
            case 8: color.append(ConsoleColor.PURPLE.fg());
        }

        return color.toString();
    }

    public String toString() {
        String flag = ConsoleColor.BOLD + ConsoleColor.YELLOW.fg() + "F" + ConsoleColor.RESET;

        if (!isRevealed) {
            return isFlagged ? flag : "█";
        }

        String bomb = ConsoleColor.BOLD + ConsoleColor.RED.fg() + "B" + ConsoleColor.RESET;

        if (isMine) {
            return bomb;
        }

        return getColor() + adjacentMines + ConsoleColor.RESET;

    }

}
