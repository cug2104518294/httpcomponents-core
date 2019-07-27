package org.apache.hc.core5.http;

import java.util.Locale;

/**
 * Common HTTP methods defined by the HTTP spec.
 *
 * @since 5.0
 */
public enum Methods {

    GET(true, true),
    HEAD(true, true),
    POST(false, false),
    PUT(false, true),
    DELETE(false, true),
    CONNECT(false, false),
    TRACE(true, true),
    OPTIONS(true, true),
    PATCH(false, false);

    private final boolean safe;
    private final boolean idempotent;

    Methods(final boolean safe, final boolean idempotent) {
        this.safe = safe;
        this.idempotent = idempotent;
    }

    public boolean isSafe() {
        return safe;
    }

    public boolean isIdempotent() {
        return idempotent;
    }

    public static boolean isSafe(final String value) {
        if (value == null) {
            return false;
        }
        try {
            return valueOf(value.toUpperCase(Locale.ROOT)).safe;
        } catch (final IllegalArgumentException ex) {
            return false;
        }
    }

    public static boolean isIdempotent(final String value) {
        if (value == null) {
            return false;
        }
        try {
            return valueOf(value.toUpperCase(Locale.ROOT)).idempotent;
        } catch (final IllegalArgumentException ex) {
            return false;
        }
    }

    public boolean isSame(final String value) {
        if (value == null) {
            return false;
        }
        return name().equalsIgnoreCase(value);
    }

}
