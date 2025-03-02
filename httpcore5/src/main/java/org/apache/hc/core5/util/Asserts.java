package org.apache.hc.core5.util;

public class Asserts {

    private Asserts() {
        // Do not allow utility class to be instantiated.
    }

    public static void check(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void check(final boolean expression, final String message, final Object... args) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, args));
        }
    }

    public static void check(final boolean expression, final String message, final Object arg) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, arg));
        }
    }

    public static void notNull(final Object object, final String name) {
        if (object == null) {
            throw new IllegalStateException(name + " is null");
        }
    }

    public static void notEmpty(final CharSequence s, final String name) {
        if (TextUtils.isEmpty(s)) {
            throw new IllegalStateException(name + " is empty");
        }
    }

    public static void notBlank(final CharSequence s, final String name) {
        if (TextUtils.isBlank(s)) {
            throw new IllegalStateException(name + " is blank");
        }
    }

}
