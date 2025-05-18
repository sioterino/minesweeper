package org.sioterino.minesweeper.utils.enums;

public enum ConsoleColor {
    BLACK("\u001B[30m", "\u001B[40m"),
    RED("\u001B[31m", "\u001B[41m"),
    GREEN("\u001B[32m", "\u001B[42m"),
    YELLOW("\u001B[33m", "\u001B[43m"),
    BLUE("\u001B[34m", "\u001B[44m"),
    PURPLE("\u001B[35m", "\u001B[45m"),
    CYAN("\u001B[36m", "\u001B[46m"),
    WHITE("\u001B[37m", "\u001B[47m"),

    BRIGHT_BLACK("\u001B[90m", "\u001B[100m"),
    BRIGHT_RED("\u001B[91m", "\u001B[101m"),
    BRIGHT_GREEN("\u001B[92m", "\u001B[102m"),
    BRIGHT_YELLOW("\u001B[93m", "\u001B[103m"),
    BRIGHT_BLUE("\u001B[94m", "\u001B[104m"),
    BRIGHT_PURPLE("\u001B[95m", "\u001B[105m"),
    BRIGHT_CYAN("\u001B[96m", "\u001B[106m"),
    BRIGHT_WHITE("\u001B[97m", "\u001B[107m");

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
