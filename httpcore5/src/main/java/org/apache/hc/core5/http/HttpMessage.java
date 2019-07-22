package org.apache.hc.core5.http;

/**
 * HTTP messages consist of requests from client to server and responses
 * from server to client.
 */
public interface HttpMessage extends MessageHeaders {

    /**
     * Sets protocol version.
     * <p>
     * For incoming messages it represents protocol version this message was transmitted with.
     * For outgoing messages it represents a hint what protocol version should be used to transmit
     * the message.
     *
     * @since 5.0
     */
    void setVersion(ProtocolVersion version);

    /**
     * Returns protocol version or {@code null} when not available.
     * <p>
     * For incoming messages it represents protocol version this message was transmitted with.
     * For outgoing messages it represents a hint what protocol version should be used to transmit
     * the message.
     *
     * @since 5.0
     */
    ProtocolVersion getVersion();

    /**
     * Adds a header to this message. The header will be appended to the end of
     * the list.
     *
     * @param header the header to append.
     */
    void addHeader(Header header);

    /**
     * Adds a header to this message. The header will be appended to the end of
     * the list.
     *
     * @param name  the name of the header.
     * @param value the value of the header, taken as the value's {@link Object#toString()}.
     */
    void addHeader(String name, Object value);

    /**
     * Overwrites the first header with the same name. The new header will be appended to
     * the end of the list, if no header with the given name can be found.
     *
     * @param header the header to set.
     */
    void setHeader(Header header);

    /**
     * Overwrites the first header with the same name. The new header will be appended to
     * the end of the list, if no header with the given name can be found.
     *
     * @param name  the name of the header.
     * @param value the value of the header, taken as the value's {@link Object#toString()}.
     */
    void setHeader(String name, Object value);

    /**
     * Overwrites all the headers in the message.
     *
     * @param headers the array of headers to set.
     */
    void setHeaders(Header... headers);

    /**
     * Removes a header from this message.
     *
     * @param header the header to remove.
     * @return <code>true</code> if a header was removed as a result of this call.
     */
    boolean removeHeader(Header header);

    /**
     * Removes all headers with a certain name from this message.
     *
     * @param name The name of the headers to remove.
     * @return <code>true</code> if any header was removed as a result of this call.
     */
    boolean removeHeaders(String name);

}
