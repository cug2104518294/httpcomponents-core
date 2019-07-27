package org.apache.hc.core5.testing.classic;

import org.apache.hc.core5.http.HttpConnection;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.impl.Http1StreamListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingHttp1StreamListener implements Http1StreamListener {

    public static final LoggingHttp1StreamListener INSTANCE = new LoggingHttp1StreamListener();

    private final Logger connLog = LoggerFactory.getLogger("org.apache.hc.core5.http.connection");

    @Override
    public void onRequestHead(final HttpConnection connection, final HttpRequest request) {
    }

    @Override
    public void onResponseHead(final HttpConnection connection, final HttpResponse response) {
    }

    @Override
    public void onExchangeComplete(final HttpConnection connection, final boolean keepAlive) {
        if (connLog.isDebugEnabled()) {
            if (keepAlive) {
                connLog.debug(LoggingSupport.getId(connection) + " Connection is kept alive");
            } else {
                connLog.debug(LoggingSupport.getId(connection) + " Connection is not kept alive");
            }
        }
    }

}
