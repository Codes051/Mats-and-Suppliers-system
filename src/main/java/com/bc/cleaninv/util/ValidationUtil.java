package com.bc.cleaninv.util;

/**
 * Owner: shared by all members — add module-specific checks here as needed.
 *
 * Small, dependency-free validation helpers. Keep these generic;
 * put entity-specific rules (e.g. "quantity <= stock on hand") inside
 * the relevant Service class instead.
 */
public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (isBlank(email)) return false;
        return email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean isPositiveInteger(String value) {
        if (isBlank(value)) return false;
        try {
            return Integer.parseInt(value.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNonNegativeInteger(String value) {
        if (isBlank(value)) return false;
        try {
            return Integer.parseInt(value.trim()) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
