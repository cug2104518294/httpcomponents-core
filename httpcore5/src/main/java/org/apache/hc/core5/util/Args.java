package org.apache.hc.core5.util;

import org.apache.hc.core5.http.EntityDetails;

import java.util.Collection;

public class Args {

    public static void check(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void check(final boolean expression, final String message, final Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }

    public static void check(final boolean expression, final String message, final Object arg) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, arg));
        }
    }

    public static long checkContentLength(final EntityDetails entityDetails) {
        // -1 is a special value
        // 0 is allowed as well
        return checkRange(entityDetails.getContentLength(), -1, Integer.MAX_VALUE,
                "HTTP entity too large to be buffered in memory)");
    }

    public static int checkRange(final int value, final int lowInclusive, final int highInclusive,
                                 final String message) {
        if (value < lowInclusive || value > highInclusive) {
            throw illegalArgumentException("%s: %,d is out of range [%,d, %,d]", message, Integer.valueOf(value),
                    Integer.valueOf(lowInclusive), Integer.valueOf(highInclusive));
        }
        return value;
    }

    public static long checkRange(final long value, final long lowInclusive, final long highInclusive,
                                  final String message) {
        if (value < lowInclusive || value > highInclusive) {
            throw illegalArgumentException("%s: %,d is out of range [%,d, %,d]", message, Long.valueOf(value),
                    Long.valueOf(lowInclusive), Long.valueOf(highInclusive));
        }
        return value;
    }

    public static <T extends CharSequence> T containsNoBlanks(final T argument, final String name) {
        if (argument == null) {
            throw illegalArgumentExceptionNotNull(name);
        }
        if (argument.length() == 0) {
            throw illegalArgumentExceptionNotEmpty(name);
        }
        if (TextUtils.containsBlanks(argument)) {
            throw new IllegalArgumentException(name + " must not contain blanks");
        }
        return argument;
    }

    private static IllegalArgumentException illegalArgumentException(final String format, final Object... args) {
        return new IllegalArgumentException(String.format(format, args));
    }

    private static IllegalArgumentException illegalArgumentExceptionNotEmpty(final String name) {
        return new IllegalArgumentException(name + " must not be empty");
    }

    private static IllegalArgumentException illegalArgumentExceptionNotNull(final String name) {
        return new IllegalArgumentException(name + " must not be null");
    }

    public static <T extends CharSequence> T notBlank(final T argument, final String name) {
        if (argument == null) {
            throw illegalArgumentExceptionNotNull(name);
        }
        if (TextUtils.isBlank(argument)) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return argument;
    }

    public static <T extends CharSequence> T notEmpty(final T argument, final String name) {
        if (argument == null) {
            throw illegalArgumentExceptionNotNull(name);
        }
        if (TextUtils.isEmpty(argument)) {
            throw illegalArgumentExceptionNotEmpty(name);
        }
        return argument;
    }

    public static <E, T extends Collection<E>> T notEmpty(final T argument, final String name) {
        if (argument == null) {
            throw illegalArgumentExceptionNotNull(name);
        }
        if (argument.isEmpty()) {
            throw illegalArgumentExceptionNotEmpty(name);
        }
        return argument;
    }

    public static int notNegative(final int n, final String name) {
        if (n < 0) {
            throw illegalArgumentException("%s must not be negative: %,d", name, n);
        }
        return n;
    }

    public static long notNegative(final long n, final String name) {
        if (n < 0) {
            throw illegalArgumentException("%s must not be negative: %,d", name, n);
        }
        return n;
    }

    public static <T> T notNull(final T argument, final String name) {
        if (argument == null) {
            throw illegalArgumentExceptionNotNull(name);
        }
        return argument;
    }

    public static int positive(final int n, final String name) {
        if (n <= 0) {
            throw illegalArgumentException("%s must not be negative or zero: %,d", name, n);
        }
        return n;
    }

    public static long positive(final long n, final String name) {
        if (n <= 0) {
            throw illegalArgumentException("%s must not be negative or zero: %,d", name, n);
        }
        return n;
    }

    private Args() {
        // Do not allow utility class to be instantiated.
    }

}
