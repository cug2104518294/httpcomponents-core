package org.apache.hc.core5.http2;

import org.apache.hc.core5.http.io.HttpTransportMetrics;

/**
 * The point of access to connection statistics.
 *
 * @since 5.0
 */
public interface H2TransportMetrics extends HttpTransportMetrics {

    long getFramesTransferred();

}
