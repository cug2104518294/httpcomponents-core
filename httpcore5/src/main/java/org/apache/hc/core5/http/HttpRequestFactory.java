package org.apache.hc.core5.http;

import java.net.URI;

/**
 * A factory for {@link HttpRequest} objects.
 *
 * @since 4.0
 */
public interface HttpRequestFactory<T extends HttpRequest> {

    /**
     * Creates request message with the given request method and request URI.
     *
     * @param method the request method
     * @param uri    the request URI
     * @return request message
     * @throws MethodNotSupportedException if the given {@code method} is not supported.
     * @since 5.0
     */
    T newHttpRequest(String method, String uri) throws MethodNotSupportedException;

    /**
     * Creates request message with the given request method and request URI.
     *
     * @param method the request method
     * @param uri    the request URI
     * @return request message
     * @throws MethodNotSupportedException if the given {@code method} is not supported.
     */
    T newHttpRequest(String method, URI uri) throws MethodNotSupportedException;

}
