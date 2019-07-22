package org.apache.hc.core5.http;

import org.apache.hc.core5.io.ModalCloseable;
import org.apache.hc.core5.util.Timeout;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.SocketAddress;

/**
 * A generic HTTP connection, useful on client and server side.
 *
 * @since 4.0
 */
public interface HttpConnection extends ModalCloseable {

    /**
     * Closes this connection gracefully. This method will attempt to flush the internal output
     * buffer prior to closing the underlying socket. This method MUST NOT be called from a
     * different thread to force shutdown of the connection. Use {@link #close shutdown} instead.
     */
    @Override
    void close() throws IOException;

    /**
     * Returns this connection's endpoint details.
     *
     * @return this connection's endpoint details.
     */
    EndpointDetails getEndpointDetails();

    /**
     * Returns this connection's local address or {@code null} if it is not bound yet.
     *
     * @return this connection's local address or {@code null} if it is not bound yet.
     * @since 5.0
     */
    SocketAddress getLocalAddress();

    /**
     * Returns this connection's protocol version or {@code null} if unknown.
     *
     * @return this connection's protocol version or {@code null} if unknown.
     * @since 5.0
     */
    ProtocolVersion getProtocolVersion();

    /**
     * Returns this connection's remote address or {@code null} if it is not connected yet or
     * unconnected.
     *
     * @return this connection's remote address or {@code null} if it is not connected yet or
     * unconnected.
     * @since 5.0
     */
    SocketAddress getRemoteAddress();

    /**
     * Returns the socket timeout value.
     *
     * @return timeout value.
     */
    Timeout getSocketTimeout();

    /**
     * Sets the socket timeout value.
     *
     * @param timeout timeout value
     */
    void setSocketTimeout(Timeout timeout);

    /**
     * Returns this connection's SSL session or {@code null} if TLS has not been activated.
     *
     * @return this connection's SSL session or {@code null} if TLS has not been activated.
     */
    SSLSession getSSLSession();

    /**
     * Checks if this connection is open.
     *
     * @return true if it is open, false if it is closed.
     */
    boolean isOpen();

}
