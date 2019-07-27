package org.apache.hc.core5.http;

/**
 * Signals that an HTTP protocol violation has occurred.
 * For example a malformed status line or headers, a missing message body, etc.
 *
 * @since 4.0
 */
public class ProtocolException extends HttpException {

    private static final long serialVersionUID = -2143571074341228994L;

    /**
     * Creates a new ProtocolException with a {@code null} detail message.
     */
    public ProtocolException() {
        super();
    }

    /**
     * Creates a new ProtocolException with the specified detail message.
     *
     * @param message The exception detail message
     */
    public ProtocolException(final String message) {
        super(message);
    }

    /**
     * Constructs a new ProtocolException with the specified detail message.
     *
     * @param format The exception detail message format; see {@link String#format(String, Object...)}.
     * @param args   The exception detail message arguments; see {@link String#format(String, Object...)}.
     * @since 5.0
     */
    public ProtocolException(final String format, final Object... args) {
        super(format, args);
    }

    /**
     * Creates a new ProtocolException with the specified detail message and cause.
     *
     * @param message the exception detail message
     * @param cause   the {@code Throwable} that caused this exception, or {@code null}
     *                if the cause is unavailable, unknown, or not a {@code Throwable}
     */
    public ProtocolException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
