package org.apache.hc.core5.http;

import org.apache.hc.core5.net.InetAddressUtils;
import org.apache.hc.core5.util.Timeout;

import java.net.SocketAddress;

/**
 * HTTP connection endpoint details.
 *
 * @since 5.0
 */
public abstract class EndpointDetails implements HttpConnectionMetrics {

    private final SocketAddress remoteAddress;
    private final SocketAddress localAddress;
    private final Timeout socketTimeout;

    protected EndpointDetails(final SocketAddress remoteAddress, final SocketAddress localAddress, final Timeout socketTimeout) {
        this.remoteAddress = remoteAddress;
        this.localAddress = localAddress;
        this.socketTimeout = socketTimeout;
    }

    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public SocketAddress getLocalAddress() {
        return localAddress;
    }

    /**
     * Gets the number of requests transferred over the connection,
     * 0 if not available.
     */
    @Override
    public abstract long getRequestCount();

    /**
     * Gets the number of responses transferred over the connection,
     * 0 if not available.
     */
    @Override
    public abstract long getResponseCount();

    /**
     * Gets the number of bytes transferred over the connection,
     * 0 if not available.
     */
    @Override
    public abstract long getSentBytesCount();

    /**
     * Gets the number of bytes transferred over the connection,
     * 0 if not available.
     */
    @Override
    public abstract long getReceivedBytesCount();

    /**
     * Gets the socket timeout.
     *
     * @return the socket timeout.
     */
    public Timeout getSocketTimeout() {
        return socketTimeout;
    }

    @Override
    public String toString() {
        // Make enough room for two IPv6 addresses to avoid re-allocation in the StringBuilder.
        final StringBuilder buffer = new StringBuilder(90);
        InetAddressUtils.formatAddress(buffer, localAddress);
        buffer.append("<->");
        InetAddressUtils.formatAddress(buffer, remoteAddress);
        return buffer.toString();
    }

}
