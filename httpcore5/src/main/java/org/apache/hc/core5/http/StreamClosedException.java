package org.apache.hc.core5.http;

import java.io.IOException;

/**
 * Signals that data stream has already been closed.
 *
 * @since 5.0
 */
public class StreamClosedException extends IOException {

    private static final long serialVersionUID = 1L;

    public StreamClosedException() {
        super("Stream already closed");
    }

    public StreamClosedException(final String message) {
        super(message);
    }

}
