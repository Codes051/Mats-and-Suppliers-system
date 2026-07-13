package com.bc.cleaninv.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Owner: Member 2 (Authentication & Security Developer)
 *
 * Wraps jBCrypt so the rest of the app never handles raw hashing calls.
 */
public final class PasswordUtil {

    private PasswordUtil() {
    }

    /** Hash a plain-text password for storage. */
    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /** Check a plain-text password against a stored hash. */
    public static boolean verify(String plainPassword, String storedHash) {
        return BCrypt.checkpw(plainPassword, storedHash);
    }
}
