package org.sioterino.minesweeper.repository;

import org.sioterino.minesweeper.models.User;

public interface UserRepository {

    void save(User user);

    void editPassword(String login, User user);

    void editLogin(String login, User user);

    User findByLogin(String login);

}
