package org.sioterino.minesweeper.services.security;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptHashAlgorithm implements HashAlgorithm {
    @Override
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    @Override
    public boolean compare(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
