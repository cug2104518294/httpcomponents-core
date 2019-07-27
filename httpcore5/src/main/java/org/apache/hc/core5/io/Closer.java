package org.apache.hc.core5.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * Closes resources.
 *
 * @since 5.0
 */
public final class Closer {

    /**
     * Closes the given Closeable in a null-safe manner.
     *
     * @param closeable what to close.
     * @throws IOException
     */
    public static void close(final Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }

    /**
     * Closes the given Closeable in a null-safe manner.
     *
     * @param closeable what to close.
     * @param closeMode How to close the given resource.
     */
    public static void close(final ModalCloseable closeable, final CloseMode closeMode) {
        if (closeable != null) {
            closeable.close(closeMode);
        }
    }

    /**
     * Closes the given Closeable quietly in a null-safe manner even in the event of an exception.
     *
     * @param closeable what to close.
     */
    public static void closeQuietly(final Closeable closeable) {
        try {
            close(closeable);
        } catch (final IOException e) {
            // Quietly ignore
        }
    }
}
