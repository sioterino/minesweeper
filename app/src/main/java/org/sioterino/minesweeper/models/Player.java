package org.sioterino.minesweeper.models;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Player extends User {
    public Player(User user) {
        super(user.getLogin(), user.getPassword(), user.getWins(), user.getLosses());
    }

    public Map<String, String> getStats() {
        Map<String, String> stats = new HashMap<>();

        int gamesPlayed = getWins() + getLosses();

        double winRate = gamesPlayed > 0 ? (double) getWins() / gamesPlayed : 0.0;

        DecimalFormat df = new DecimalFormat("00.00%");
        String formattedWinRate = df.format(winRate);

        stats.put("games", String.valueOf(gamesPlayed));
        stats.put("winRate", formattedWinRate);
        stats.put("wins", String.valueOf(getWins()));
        stats.put("losses", String.valueOf(getLosses()));

        return stats;
    }
}
