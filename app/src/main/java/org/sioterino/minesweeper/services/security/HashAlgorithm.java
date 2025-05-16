package org.sioterino.minesweeper.services.security;

public interface HashAlgorithm {

    String hash(String password);

    boolean compare(String password, String hash);

}
