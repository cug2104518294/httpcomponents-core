package org.apache.hc.core5.http;

/**
 * The point of access to the statistics of an {@link HttpConnection}.
 *
 * @since 4.0
 */
public interface HttpConnectionMetrics {

    /**
     * Gets the number of requests transferred over the connection,
     * 0 if not available.
     */
    long getRequestCount();

    /**
     * Gets the number of responses transferred over the connection,
     * 0 if not available.
     */
    long getResponseCount();

    /**
     * Gets the number of bytes transferred over the connection,
     * 0 if not available.
     */
    long getSentBytesCount();

    /**
     * Gets the number of bytes transferred over the connection,
     * 0 if not available.
     */
    long getReceivedBytesCount();

}
