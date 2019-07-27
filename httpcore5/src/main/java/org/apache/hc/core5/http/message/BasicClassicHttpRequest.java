package org.apache.hc.core5.http.message;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.Methods;

import java.net.URI;

/**
 * Basic implementation of {@link ClassicHttpRequest}.
 *
 * @since 5.0
 */
public class BasicClassicHttpRequest extends BasicHttpRequest implements ClassicHttpRequest {

    private static final long serialVersionUID = 1L;

    private HttpEntity entity;

    /**
     * Creates request message with the given method and request path.
     *
     * @param method request method.
     * @param path   request path.
     */
    public BasicClassicHttpRequest(final String method, final String path) {
        super(method, path);
    }

    /**
     * Creates request message with the given method, host and request path.
     *
     * @param method request method.
     * @param host   request host.
     * @param path   request path.
     */
    public BasicClassicHttpRequest(final String method, final HttpHost host, final String path) {
        super(method, host, path);
    }

    /**
     * Creates request message with the given method, request URI.
     *
     * @param method     request method.
     * @param requestUri request URI.
     */
    public BasicClassicHttpRequest(final String method, final URI requestUri) {
        super(method, requestUri);
    }

    /**
     * Creates request message with the given method and request path.
     *
     * @param method request method.
     * @param path   request path.
     */
    public BasicClassicHttpRequest(final Methods method, final String path) {
        super(method, path);
    }

    /**
     * Creates request message with the given method, host and request path.
     *
     * @param method request method.
     * @param host   request host.
     * @param path   request path.
     */
    public BasicClassicHttpRequest(final Methods method, final HttpHost host, final String path) {
        super(method, host, path);
    }

    /**
     * Creates request message with the given method, request URI.
     *
     * @param method     request method.
     * @param requestUri request URI.
     */
    public BasicClassicHttpRequest(final Methods method, final URI requestUri) {
        super(method, requestUri);
    }

    @Override
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override
    public void setEntity(final HttpEntity entity) {
        this.entity = entity;
    }

}
