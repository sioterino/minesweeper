package org.sioterino.minesweeper.models;

import java.text.DecimalFormat;

public class Player {
    private final User user;

    public Player(User user) {
        this.user = user;
    }

    public Stats getStats() {
        return new Stats(user.getWins(), user.getLosses());
    }

    public void increaseWins() {
        user.setWins(user.getWins() + 1);
    }

    public void increaseLosses() {
        user.setLosses(user.getLosses() + 1);
    }

    public User getUser() {
        return user;
    }

    public static Player guestPlayer() {
        String username = "guest_" + System.currentTimeMillis();
        User guest = new User(username, "", 0, 0);
        return new Player(guest);
    }

    public static class Stats {
        public final int wins;
        public final int losses;
        public final int gamesPlayed;
        public final String winRate;

        public Stats(int wins, int losses) {
            this.wins = wins;
            this.losses = losses;
            this.gamesPlayed = wins + losses;

            DecimalFormat df = new DecimalFormat("0.00%");
            this.winRate = df.format(gamesPlayed > 0 ? (double) wins / gamesPlayed : 0.0);
        }
    }
}
