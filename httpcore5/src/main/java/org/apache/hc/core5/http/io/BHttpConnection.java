package org.apache.hc.core5.http.io;

import org.apache.hc.core5.http.HttpConnection;

import java.io.IOException;

/**
 * Abstract blocking HTTP connection interface.
 *
 * @since 5.0
 */
public interface BHttpConnection extends HttpConnection {

    /**
     * Checks if inout data is available from the connection. May wait for
     * the specified time until some data becomes available. Note that some
     * implementations may completely ignore the timeout parameter.
     *
     * @param timeout the maximum time in milliseconds to wait for data
     * @return true if data is available; false if there was no data available
     * even after waiting for {@code timeout} milliseconds.
     * @throws IOException if an error happens on the connection
     */
    boolean isDataAvailable(int timeout) throws IOException;

    /**
     * Checks whether this connection has gone down.
     * Network connections may get closed during some time of inactivity
     * for several reasons. The next time a read is attempted on such a
     * connection it will throw an IOException.
     * This method tries to alleviate this inconvenience by trying to
     * find out if a connection is still usable. Implementations may do
     * that by attempting a read with a very small timeout. Thus this
     * method may block for a small amount of time before returning a result.
     * It is therefore an <i>expensive</i> operation.
     *
     * @return {@code true} if attempts to use this connection are
     * likely to succeed, or {@code false} if they are likely
     * to fail and this connection should be closed
     */
    boolean isStale() throws IOException;

    /**
     * Writes out all pending buffered data over the open connection.
     *
     * @throws java.io.IOException in case of an I/O error
     */
    void flush() throws IOException;

}
