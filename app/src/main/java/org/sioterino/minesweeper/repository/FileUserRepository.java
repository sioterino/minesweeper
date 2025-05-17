package org.sioterino.minesweeper.repository;

import org.sioterino.minesweeper.models.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileUserRepository implements UserRepository {

    private final File file = new File("users.txt");
    private final Map<String, User> users = new HashMap<>();

    public FileUserRepository() {
        loadFromFile();
    }

    private Scanner newScanner() {
        try {
            return new Scanner(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error loading users file: " + e.getMessage());
        }
    }

    private PrintWriter newPrintWriter() {
        try {
            return new PrintWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new RuntimeException("Error saving users file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        if (!file.exists()) return;

        Scanner scanner = newScanner();
        while (scanner.hasNext()) {

            String line = scanner.nextLine();
            String[] data = line.split(":");

            String login = data[0];
            String password = data[1];
            int wins = data.length > 2 ? Integer.parseInt(data[2]) : 0;
            int losses = data.length > 3 ? Integer.parseInt(data[3]) : 0;

            users.put(login, new User(login, password, wins, losses));
        }
    }

    private void saveToFile() {
        PrintWriter writer = newPrintWriter();
        for (User user : users.values()) {
            writer.println(user.getLogin() + ":" + user.getPassword() + ":" + user.getWins() + ":" + user.getLosses());
        }
        writer.close();
    }

    @Override
    public void save(User user) {
        if (user.isGuest()) return;

        users.put(user.getLogin(), user);
        saveToFile();
    }

    @Override
    public void editPassword(String login, User user) {
        users.put(login, user);
        saveToFile();
    }

    @Override
    public void editLogin(String oldLogin, User user) {
        users.remove(oldLogin);
        users.put(user.getLogin(), user);
        saveToFile();
    }

    @Override
    public User findByLogin(String login) {
        return users.get(login);
    }
}
