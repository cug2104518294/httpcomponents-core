package org.apache.hc.core5.http.protocol;

import org.apache.hc.core5.http.EndpointDetails;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ProtocolVersion;
import org.apache.hc.core5.util.Args;

import javax.net.ssl.SSLSession;

/**
 * Implementation of {@link HttpContext} that provides convenience
 * setters for user assignable attributes and getter for readable attributes.
 *
 * @since 4.3
 */
public class HttpCoreContext implements HttpContext {

    /**
     * Attribute name of a {@link EndpointDetails} object that
     * represents the actual connection endpoint details.
     */
    public static final String CONNECTION_ENDPOINT = HttpContext.RESERVED_PREFIX + "connection-endpoint";

    /**
     * Attribute name of a {@link SSLSession} object that
     * represents the actual connection endpoint details.
     */
    public static final String SSL_SESSION = HttpContext.RESERVED_PREFIX + "ssl-session";

    /**
     * Attribute name of a {@link HttpRequest} object that
     * represents the actual HTTP request.
     */
    public static final String HTTP_REQUEST = HttpContext.RESERVED_PREFIX + "request";

    /**
     * Attribute name of a {@link HttpResponse} object that
     * represents the actual HTTP response.
     */
    public static final String HTTP_RESPONSE = HttpContext.RESERVED_PREFIX + "response";

    public static HttpCoreContext create() {
        return new HttpCoreContext();
    }

    public static HttpCoreContext adapt(final HttpContext context) {
        if (context == null) {
            return new HttpCoreContext();
        }
        if (context instanceof HttpCoreContext) {
            return (HttpCoreContext) context;
        }
        return new HttpCoreContext(context);
    }

    private final HttpContext context;

    public HttpCoreContext(final HttpContext context) {
        super();
        this.context = context;
    }

    public HttpCoreContext() {
        super();
        this.context = new BasicHttpContext();
    }

    /**
     * @since 5.0
     */
    @Override
    public ProtocolVersion getProtocolVersion() {
        return this.context.getProtocolVersion();
    }

    /**
     * @since 5.0
     */
    @Override
    public void setProtocolVersion(final ProtocolVersion version) {
        this.context.setProtocolVersion(version);
    }

    @Override
    public Object getAttribute(final String id) {
        return context.getAttribute(id);
    }

    @Override
    public Object setAttribute(final String id, final Object obj) {
        return context.setAttribute(id, obj);
    }

    @Override
    public Object removeAttribute(final String id) {
        return context.removeAttribute(id);
    }

    public <T> T getAttribute(final String attribname, final Class<T> clazz) {
        Args.notNull(clazz, "Attribute class");
        final Object obj = getAttribute(attribname);
        if (obj == null) {
            return null;
        }
        return clazz.cast(obj);
    }

    /**
     * @since 5.0
     */
    public SSLSession getSSLSession() {
        return getAttribute(SSL_SESSION, SSLSession.class);
    }

    /**
     * @since 5.0
     */
    public EndpointDetails getEndpointDetails() {
        return getAttribute(CONNECTION_ENDPOINT, EndpointDetails.class);
    }

    public HttpRequest getRequest() {
        return getAttribute(HTTP_REQUEST, HttpRequest.class);
    }

    public HttpResponse getResponse() {
        return getAttribute(HTTP_RESPONSE, HttpResponse.class);
    }

}
