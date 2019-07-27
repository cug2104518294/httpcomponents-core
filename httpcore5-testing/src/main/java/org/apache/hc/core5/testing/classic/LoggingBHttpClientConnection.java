package org.apache.hc.core5.testing.classic;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentLengthStrategy;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.config.Http1Config;
import org.apache.hc.core5.http.impl.io.DefaultBHttpClientConnection;
import org.apache.hc.core5.http.impl.io.SocketHolder;
import org.apache.hc.core5.http.io.HttpMessageParserFactory;
import org.apache.hc.core5.http.io.HttpMessageWriterFactory;
import org.apache.hc.core5.http.message.RequestLine;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.util.Identifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.atomic.AtomicLong;

public class LoggingBHttpClientConnection extends DefaultBHttpClientConnection implements Identifiable {

    private static final AtomicLong COUNT = new AtomicLong();

    private final String id;
    private final Logger log;
    private final Logger headerLog;
    private final Wire wire;

    public LoggingBHttpClientConnection(
            final Http1Config http1Config,
            final CharsetDecoder charDecoder,
            final CharsetEncoder charEncoder,
            final ContentLengthStrategy incomingContentStrategy,
            final ContentLengthStrategy outgoingContentStrategy,
            final HttpMessageWriterFactory<ClassicHttpRequest> requestWriterFactory,
            final HttpMessageParserFactory<ClassicHttpResponse> responseParserFactory) {
        super(http1Config, charDecoder, charEncoder,
                incomingContentStrategy, outgoingContentStrategy,
                requestWriterFactory, responseParserFactory);
        this.id = "http-outgoing-" + COUNT.incrementAndGet();
        this.log = LoggerFactory.getLogger(getClass());
        this.headerLog = LoggerFactory.getLogger("org.apache.hc.core5.http.headers");
        this.wire = new Wire(LoggerFactory.getLogger("org.apache.hc.core5.http.wire"), this.id);
    }

    public LoggingBHttpClientConnection(final Http1Config http1Config) {
        this(http1Config, null, null, null, null, null, null);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void close() throws IOException {
        if (this.log.isDebugEnabled()) {
            this.log.debug(this.id + ": Close connection");
        }
        super.close();
    }

    @Override
    public void close(final CloseMode closeMode) {
        if (this.log.isDebugEnabled()) {
            this.log.debug(this.id + ": Shutdown connection");
        }
        super.close(closeMode);
    }

    @Override
    public void bind(final Socket socket) throws IOException {
        super.bind(this.wire.isEnabled() ? new LoggingSocketHolder(socket, wire) : new SocketHolder(socket));
    }

    @Override
    protected void onResponseReceived(final ClassicHttpResponse response) {
        if (response != null && this.headerLog.isDebugEnabled()) {
            this.headerLog.debug(this.id + " << " + new StatusLine(response));
            final Header[] headers = response.getHeaders();
            for (final Header header : headers) {
                this.headerLog.debug(this.id + " << " + header.toString());
            }
        }
    }

    @Override
    protected void onRequestSubmitted(final ClassicHttpRequest request) {
        if (request != null && this.headerLog.isDebugEnabled()) {
            this.headerLog.debug(id + " >> " + new RequestLine(request));
            final Header[] headers = request.getHeaders();
            for (final Header header : headers) {
                this.headerLog.debug(this.id + " >> " + header.toString());
            }
        }
    }

}
