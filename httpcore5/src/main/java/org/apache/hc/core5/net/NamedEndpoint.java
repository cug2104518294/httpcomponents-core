package org.apache.hc.core5.net;

/**
 * Endpoint identified by name (usually a fully qualified domain name) and port.
 */
public interface NamedEndpoint {

    /**
     * Returns name (IP or DNS name).
     *
     * @return the host name (IP or DNS name)
     */
    String getHostName();

    /**
     * Returns the port.
     *
     * @return the host port, or {@code -1} if not set
     */
    int getPort();

}
