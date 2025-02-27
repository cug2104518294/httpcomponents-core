package org.apache.hc.core5.http.impl;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.message.BasicTokenIterator;
import org.apache.hc.core5.http.message.MessageSupport;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.Args;

import java.util.Iterator;

/**
 * Default implementation of a strategy deciding about connection re-use. The strategy
 * determines whether a connection is persistent or not based on the message’s protocol
 * version and {@code Connection} header field if present. Connections will not be
 * re-used and will close if any of these conditions is met
 * <ul>
 * <li>the {@code close} connection option is present in the request message</li>
 * <li>the response message content body is incorrectly or ambiguously delineated</li>
 * <li>the {@code close} connection option is present in the response message</li>
 * <li>If the received protocol is {@code HTTP/1.0} (or earlier) and {@code keep-alive}
 * connection option is not present</li>
 * </ul>
 * In the absence of a {@code Connection} header field, the non-standard but commonly used
 * {@code Proxy-Connection} header field will be used instead. If no connection options are
 * explicitly given the default policy for the HTTP version is applied. {@code HTTP/1.1}
 * (or later) connections are re-used by default. {@code HTTP/1.0} (or earlier) connections
 * are not re-used by default.
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class DefaultConnectionReuseStrategy implements ConnectionReuseStrategy {

    public static final DefaultConnectionReuseStrategy INSTANCE = new DefaultConnectionReuseStrategy();

    public DefaultConnectionReuseStrategy() {
        super();
    }

    // see interface ConnectionReuseStrategy
    @Override
    public boolean keepAlive(
            final HttpRequest request, final HttpResponse response, final HttpContext context) {
        Args.notNull(response, "HTTP response");
        if (request != null) {
            final Iterator<String> ti = new BasicTokenIterator(request.headerIterator(HttpHeaders.CONNECTION));
            while (ti.hasNext()) {
                final String token = ti.next();
                if (HeaderElements.CLOSE.equalsIgnoreCase(token)) {
                    return false;
                }
            }
        }

        // If a HTTP 204 No Content response contains a Content-length with value > 0 or Transfer-Encoding header,
        // don't reuse the connection. This is to avoid getting out-of-sync if a misbehaved HTTP server
        // returns content as part of a HTTP 204 response.
        if (response.getCode() == HttpStatus.SC_NO_CONTENT) {
            final Header clh = response.getFirstHeader(HttpHeaders.CONTENT_LENGTH);
            if (clh != null) {
                try {
                    final int contentLen = Integer.parseInt(clh.getValue());
                    if (contentLen > 0) {
                        return false;
                    }
                } catch (final NumberFormatException ex) {
                    // fall through
                }
            }
            if (response.containsHeader(HttpHeaders.TRANSFER_ENCODING)) {
                return false;
            }
        }

        // Check for a self-terminating entity. If the end of the entity will
        // be indicated by closing the connection, there is no keep-alive.
        final Header teh = response.getFirstHeader(HttpHeaders.TRANSFER_ENCODING);
        if (teh != null) {
            if (!HeaderElements.CHUNKED_ENCODING.equalsIgnoreCase(teh.getValue())) {
                return false;
            }
        } else {
            final String method = request != null ? request.getMethod() : null;
            if (MessageSupport.canResponseHaveBody(method, response)
                    && response.countHeaders(HttpHeaders.CONTENT_LENGTH) != 1) {
                return false;
            }
        }

        // Check for the "Connection" header. If that is absent, check for
        // the "Proxy-Connection" header. The latter is an unspecified and
        // broken but unfortunately common extension of HTTP.
        Iterator<Header> headerIterator = response.headerIterator(HttpHeaders.CONNECTION);
        if (!headerIterator.hasNext()) {
            headerIterator = response.headerIterator("Proxy-Connection");
        }

        final ProtocolVersion ver = context.getProtocolVersion();
        if (headerIterator.hasNext()) {
            if (ver.greaterEquals(HttpVersion.HTTP_1_1)) {
                final Iterator<String> it = new BasicTokenIterator(headerIterator);
                while (it.hasNext()) {
                    final String token = it.next();
                    if (HeaderElements.CLOSE.equalsIgnoreCase(token)) {
                        return false;
                    }
                }
                return true;
            }
            final Iterator<String> it = new BasicTokenIterator(headerIterator);
            while (it.hasNext()) {
                final String token = it.next();
                if (HeaderElements.KEEP_ALIVE.equalsIgnoreCase(token)) {
                    return true;
                }
            }
            return false;
        }
        return ver.greaterEquals(HttpVersion.HTTP_1_1);
    }

}
