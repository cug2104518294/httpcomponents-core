package org.apache.hc.core5.http.io;

/**
 * Transport level metrics.
 *
 * @since 4.0
 */
public interface HttpTransportMetrics {

    /**
     * Returns the number of bytes transferred.
     */
    long getBytesTransferred();

}
