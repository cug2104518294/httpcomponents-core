package org.apache.hc.core5.http;

import java.io.Closeable;

/**
 * 'Classic' {@link HttpResponse} message that can enclose {@link HttpEntity}.
 *
 * @since 5.0
 */
public interface ClassicHttpResponse extends HttpResponse, HttpEntityContainer, Closeable {
}
