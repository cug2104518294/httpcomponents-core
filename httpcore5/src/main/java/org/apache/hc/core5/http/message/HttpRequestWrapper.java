package org.apache.hc.core5.http.message;

import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.net.URIAuthority;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * {@link HttpRequest} wrapper.
 */
public class HttpRequestWrapper extends AbstractMessageWrapper implements HttpRequest {

    private final HttpRequest message;

    public HttpRequestWrapper(final HttpRequest message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMethod() {
        return message.getMethod();
    }

    @Override
    public String getPath() {
        return message.getPath();
    }

    @Override
    public void setPath(final String path) {
        message.setPath(path);
    }

    @Override
    public String getScheme() {
        return message.getScheme();
    }

    @Override
    public void setScheme(final String scheme) {
        message.setScheme(scheme);
    }

    @Override
    public URIAuthority getAuthority() {
        return message.getAuthority();
    }

    @Override
    public void setAuthority(final URIAuthority authority) {
        message.setAuthority(authority);
    }

    @Override
    public String getRequestUri() {
        return message.getRequestUri();
    }

    @Override
    public URI getUri() throws URISyntaxException {
        return message.getUri();
    }

    @Override
    public void setUri(final URI requestUri) {
        message.setUri(requestUri);
    }

}
