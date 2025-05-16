package org.sioterino.minesweeper.enums;

public enum ConsoleColor {
    BLACK("\u001B[30m", "\u001B[40m"),
    RED("\u001B[31m", "\u001B[41m"),
    GREEN("\u001B[32m", "\u001B[42m"),
    YELLOW("\u001B[33m", "\u001B[43m"),
    BLUE("\u001B[34m", "\u001B[44m"),
    PURPLE("\u001B[35m", "\u001B[45m"),
    CYAN("\u001B[36m", "\u001B[46m"),
    WHITE("\u001B[37m", "\u001B[47m");

    private final String foregroundCode;
    private final String backgroundCode;

    ConsoleColor(String foregroundCode, String backgroundCode) {
        this.foregroundCode = foregroundCode;
        this.backgroundCode = backgroundCode;
    }

    public String fg() {
        return foregroundCode;
    }

    public String bg() {
        return backgroundCode;
    }

    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINED = "\u001B[4m";
}
