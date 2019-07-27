package org.apache.hc.core5.http.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * A watcher for {@link EofSensorInputStream}. Each stream will notify its
 * watcher at most once.
 *
 * @since 4.0
 */
public interface EofSensorWatcher {

    /**
     * Indicates that EOF is detected.
     *
     * @param wrapped the underlying stream which has reached EOF
     * @return {@code true} if {@code wrapped} should be closed,
     * {@code false} if it should be left alone
     * @throws IOException in case of an IO problem, for example if the watcher itself
     *                     closes the underlying stream. The caller will leave the
     *                     wrapped stream alone, as if {@code false} was returned.
     */
    boolean eofDetected(InputStream wrapped)
            throws IOException;

    /**
     * Indicates that the {@link EofSensorInputStream stream} is closed.
     * This method will be called only if EOF was <i>not</i> detected
     * before closing. Otherwise, {@link #eofDetected eofDetected} is called.
     *
     * @param wrapped the underlying stream which has not reached EOF
     * @return {@code true} if {@code wrapped} should be closed,
     * {@code false} if it should be left alone
     * @throws IOException in case of an IO problem, for example if the watcher itself
     *                     closes the underlying stream. The caller will leave the
     *                     wrapped stream alone, as if {@code false} was returned.
     */
    boolean streamClosed(InputStream wrapped)
            throws IOException;

    /**
     * Indicates that the {@link EofSensorInputStream stream} is aborted.
     * This method will be called only if EOF was <i>not</i> detected
     * before aborting. Otherwise, {@link #eofDetected eofDetected} is called.
     * <p>
     * This method will also be invoked when an input operation causes an
     * IOException to be thrown to make sure the input stream gets shut down.
     * </p>
     *
     * @param wrapped the underlying stream which has not reached EOF
     * @return {@code true} if {@code wrapped} should be closed,
     * {@code false} if it should be left alone
     * @throws IOException in case of an IO problem, for example if the watcher itself
     *                     closes the underlying stream. The caller will leave the
     *                     wrapped stream alone, as if {@code false} was returned.
     */
    boolean streamAbort(InputStream wrapped)
            throws IOException;

}
