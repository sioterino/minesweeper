package org.sioterino.minesweeper.services;

import org.sioterino.minesweeper.App;
import org.sioterino.minesweeper.utils.exceptions.InvalidPasswordException;
import org.sioterino.minesweeper.utils.exceptions.UserAlreadyExistsException;
import org.sioterino.minesweeper.utils.exceptions.UserNotFoundException;
import org.sioterino.minesweeper.models.Player;
import org.sioterino.minesweeper.models.User;
import org.sioterino.minesweeper.repository.UserRepository;
import org.sioterino.minesweeper.services.security.HashAlgorithm;

public class UserService {

    private final UserRepository repository;
    private final HashAlgorithm hashAlgorithm;

    public UserService(UserRepository repository, HashAlgorithm hashAlgorithm) {
        this.repository = repository;
        this.hashAlgorithm = hashAlgorithm;
    }

    public void register(String login, String password) {
        if (repository.findByLogin(login) != null) {
            throw new UserAlreadyExistsException(login);
        }

        String hash = hashAlgorithm.hash(password);
        repository.save(new User(login, hash, 0,0));
    }

    public Player authenticate(String login, String password) {
        User user = repository.findByLogin(login);

        if (user == null) {
            throw new UserNotFoundException(login);
        }

        if (!hashAlgorithm.compare(password, user.getPassword())) {
            throw new InvalidPasswordException(login);
        }

        return new Player(user);
    }

    public void logout(User user) {
        System.out.println("login out user: " + user.getLogin());
        repository.save(user);
        App.player = null;
    }

    public Player editPassword(String login, String newPassword, String oldPassword) {
        authenticate(login, oldPassword);
        User user = repository.findByLogin(login);

        String hash = hashAlgorithm.hash(newPassword);
        repository.editPassword(login, new User(login, hash, user.getWins(), user.getLosses()));

        return new Player(repository.findByLogin(login));
    }

    public Player editLoginName(String oldLogin, String newLogin, String password) {
        authenticate(oldLogin, password);
        User user = repository.findByLogin(oldLogin);

        String hash = hashAlgorithm.hash(password);
        repository.editLogin(oldLogin, new User(newLogin, hash, user.getWins(), user.getLosses()));

        return new Player(repository.findByLogin(newLogin));
    }

}
