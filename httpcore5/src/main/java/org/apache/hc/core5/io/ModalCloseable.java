package org.apache.hc.core5.io;

import java.io.Closeable;

/**
 * Process or endpoint that can be closed either immediately or gracefully.
 *
 * @since 5.0
 */
public interface ModalCloseable extends Closeable {

    /**
     * Closes this process or endpoint and releases any system resources associated
     * with it. If the endpoint or the process is already closed then invoking this
     * method has no effect.
     *
     * @param closeMode How to close the receiver.
     */
    void close(CloseMode closeMode);

}
