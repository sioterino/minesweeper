package org.sioterino.minesweeper.models;

public class User {

    private String login;
    private String password;

    private int wins;
    private int losses;

    public User(String login, String password, int wins, int losses) {
        this.login = login;
        this.password = password;
        this.wins = wins;
        this.losses = losses;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

}
