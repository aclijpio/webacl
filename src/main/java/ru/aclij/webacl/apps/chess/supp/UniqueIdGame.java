package ru.aclij.webacl.apps.chess.supp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
@Component
public class UniqueIdGame {
    private static SecureRandom random = new SecureRandom();

    private final String characters;
    private final int length;
    @Autowired
    public UniqueIdGame(UniqueIdProperties properties) {
        this.characters = properties.getCharacters();
        this.length = properties.getLength();
    }

    public String generateUniqueId() {
        StringBuilder uniqueID = new StringBuilder(this.length);
        while (uniqueID.length() < 6) {
            char randomChar = characters.charAt(random.nextInt(this.characters.length()));
            if (uniqueID.indexOf(String.valueOf(randomChar)) == -1)
                uniqueID.append(randomChar);
        }
        return uniqueID.toString();
    }
}
