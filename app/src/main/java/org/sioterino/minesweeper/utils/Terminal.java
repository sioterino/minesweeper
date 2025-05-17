package org.sioterino.minesweeper.utils;

import org.sioterino.minesweeper.models.Player;
import org.sioterino.minesweeper.utils.enums.ASCIIMenu;

public class Terminal {

    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void redirect(ASCIIMenu menu) {
//        Terminal.clearConsole();
        System.out.println(menu);
    }

    public static void redirect(ASCIIMenu menu, Player player) {
        Terminal.clearConsole();
        System.out.printf(menu.toString(), player.getUser().getLogin(), player.getStats().gamesPlayed, player.getStats().winRate, player.getStats().losses);
    }

}
