package org.apache.hc.core5.http;

import org.apache.hc.core5.http.protocol.HttpContext;

/**
 * This class can be used to resolve an object matching a particular {@link HttpRequest}.
 * Usually the mapped object will be a request handler to process the request.
 *
 * @since 5.0
 */
public interface HttpRequestMapper<T> {

    /**
     * Resolves a handler matching the given request.
     *
     * @param request the request to map to a handler
     * @return HTTP request handler or {@code null} if no match
     * is found.
     */
    T resolve(HttpRequest request, HttpContext context) throws HttpException;

}
