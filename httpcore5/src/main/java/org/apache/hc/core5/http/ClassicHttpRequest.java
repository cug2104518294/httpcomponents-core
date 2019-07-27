package org.apache.hc.core5.http;

/**
 * 'Classic' {@link HttpRequest} message that can enclose {@link HttpEntity}.
 *
 * @since 5.0
 */
public interface ClassicHttpRequest extends HttpRequest, HttpEntityContainer {
}
