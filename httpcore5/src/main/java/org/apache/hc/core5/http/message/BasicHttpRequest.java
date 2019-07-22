package org.apache.hc.core5.http.message;

import org.apache.hc.core5.http.*;
import org.apache.hc.core5.net.URIAuthority;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.TextUtils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Basic implementation of {@link HttpRequest}.
 *
 * @since 4.0
 */
public class BasicHttpRequest extends HeaderGroup implements HttpRequest {

    private static final long serialVersionUID = 1L;

    private final String method;
    private String path;
    private String scheme;
    private URIAuthority authority;
    private ProtocolVersion version;
    private URI requestUri;

    /**
     * Creates request message with the given method and request path.
     *
     * @param method request method.
     * @param path   request path.
     */
    public BasicHttpRequest(final String method, final String path) {
        super();
        this.method = method;
        if (path != null) {
            try {
                setUri(new URI(path));
            } catch (final URISyntaxException ex) {
                this.path = path;
            }
        }
    }

    /**
     * Creates request message with the given method, host and request path.
     *
     * @param method request method.
     * @param host   request host.
     * @param path   request path.
     * @since 5.0
     */
    public BasicHttpRequest(final String method, final HttpHost host, final String path) {
        super();
        this.method = Args.notNull(method, "Method name");
        this.scheme = host != null ? host.getSchemeName() : null;
        this.authority = host != null ? new URIAuthority(host) : null;
        this.path = path;
    }

    /**
     * Creates request message with the given method, request URI.
     *
     * @param method     request method.
     * @param requestUri request URI.
     * @since 5.0
     */
    public BasicHttpRequest(final String method, final URI requestUri) {
        super();
        this.method = Args.notNull(method, "Method name");
        setUri(Args.notNull(requestUri, "Request URI"));
    }

    /**
     * Creates request message with the given method and request path.
     *
     * @param method request method.
     * @param path   request path.
     * @since 5.0
     */
    public BasicHttpRequest(final Methods method, final String path) {
        super();
        this.method = Args.notNull(method, "Method").name();
        if (path != null) {
            try {
                setUri(new URI(path));
            } catch (final URISyntaxException ex) {
                this.path = path;
            }
        }
    }

    /**
     * Creates request message with the given method, host and request path.
     *
     * @param method request method.
     * @param host   request host.
     * @param path   request path.
     * @since 5.0
     */
    public BasicHttpRequest(final Methods method, final HttpHost host, final String path) {
        super();
        this.method = Args.notNull(method, "Method").name();
        this.scheme = host != null ? host.getSchemeName() : null;
        this.authority = host != null ? new URIAuthority(host) : null;
        this.path = path;
    }

    /**
     * Creates request message with the given method, request URI.
     *
     * @param method     request method.
     * @param requestUri request URI.
     * @since 5.0
     */
    public BasicHttpRequest(final Methods method, final URI requestUri) {
        super();
        this.method = Args.notNull(method, "Method").name();
        setUri(Args.notNull(requestUri, "Request URI"));
    }

    @Override
    public void addHeader(final String name, final Object value) {
        Args.notNull(name, "Header name");
        addHeader(new BasicHeader(name, value));
    }

    @Override
    public void setHeader(final String name, final Object value) {
        Args.notNull(name, "Header name");
        setHeader(new BasicHeader(name, value));
    }

    @Override
    public void setVersion(final ProtocolVersion version) {
        this.version = version;
    }

    @Override
    public ProtocolVersion getVersion() {
        return this.version;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public void setPath(final String path) {
        this.path = path;
        this.requestUri = null;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    @Override
    public void setScheme(final String scheme) {
        this.scheme = scheme;
        this.requestUri = null;
    }

    @Override
    public URIAuthority getAuthority() {
        return this.authority;
    }

    @Override
    public void setAuthority(final URIAuthority authority) {
        this.authority = authority;
        this.requestUri = null;
    }

    @Override
    public String getRequestUri() {
        return getPath();
    }

    @Override
    public void setUri(final URI requestUri) {
        this.scheme = requestUri.getScheme();
        this.authority = requestUri.getHost() != null ? new URIAuthority(
                requestUri.getRawUserInfo(),
                requestUri.getHost(),
                requestUri.getPort()) : null;
        final StringBuilder buf = new StringBuilder();
        final String rawPath = requestUri.getRawPath();
        if (!TextUtils.isBlank(rawPath)) {
            buf.append(rawPath);
        } else {
            buf.append("/");
        }
        final String query = requestUri.getRawQuery();
        if (query != null) {
            buf.append('?').append(query);
        }
        this.path = buf.toString();
    }

    @Override
    public URI getUri() throws URISyntaxException {
        if (this.requestUri == null) {
            final StringBuilder buf = new StringBuilder();
            if (this.authority != null) {
                buf.append(this.scheme != null ? this.scheme : URIScheme.HTTP.id).append("://");
                buf.append(this.authority.getHostName());
                if (this.authority.getPort() >= 0) {
                    buf.append(":").append(this.authority.getPort());
                }
            }
            if (this.path == null) {
                buf.append("/");
            } else {
                if (buf.length() > 0 && !this.path.startsWith("/")) {
                    buf.append("/");
                }
                buf.append(this.path);
            }
            this.requestUri = new URI(buf.toString());
        }
        return this.requestUri;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.method).append(" ").append(this.scheme).append("://").append(this.authority).append(this.path);
        return sb.toString();
    }

}
