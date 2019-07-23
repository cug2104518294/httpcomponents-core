package org.apache.hc.core5.http.impl;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.Internal;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.HttpConnection;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;

/**
 * HTTP/1.1 stream event listener.
 *
 * @since 5.0
 */
@Contract(threading = ThreadingBehavior.STATELESS)
@Internal
public interface Http1StreamListener {

    void onRequestHead(HttpConnection connection, HttpRequest request);

    void onResponseHead(HttpConnection connection, HttpResponse response);

    void onExchangeComplete(HttpConnection connection, boolean keepAlive);

}
