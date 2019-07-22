package org.apache.hc.core5.http;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hc.core5.net.URIAuthority;

/**
 * A request message from a client to a server includes, within the
 * first line of that message, the method to be applied to the resource,
 * the identifier of the resource, and the protocol version in use.
 */
public interface HttpRequest extends HttpMessage {

    /**
     * Returns method of this request message.
     *
     * @return  the request method.
     */
    String getMethod();

    /**
     * Returns URI path of this request message or {@code null} if not set.
     *
     * @return  the request URI or {@code null}.
     */
    String getPath();

    /**
     * Sets URI path of this request message.
     *
     * @since 5.0
     */
    void setPath(String path);

    /**
     * Returns scheme of this request message.
     *
     * @return  the scheme or {@code null}.
     *
     * @since 5.0
     */
    String getScheme();

    /**
     * Sets scheme of this request message.
     *
     * @since 5.0
     */
    void setScheme(String scheme);

    /**
     * Returns authority of this request message.
     *
     * @return  the authority or {@code null}.
     *
     * @since 5.0
     */
    URIAuthority getAuthority();

    /**
     * Sets authority of this request message.
     *
     * @since 5.0
     */
    void setAuthority(URIAuthority authority);

    /**
     * Returns request URI of this request message. It may be an absolute or relative URI.
     * Applicable to HTTP/1.1 version or earlier.
     *
     * @return  the request URI.
     *
     * @since 5.0
     */
    String getRequestUri();

    /**
     * Returns full request URI of this request message.
     *
     * @return  the request URI.
     *
     * @since 5.0
     */
    URI getUri() throws URISyntaxException;

    /**
     * Sets the full request URI of this request message.
     *
     * @param requestUri the request URI.
     *
     * @since 5.0
     */
    void setUri(final URI requestUri);

}
