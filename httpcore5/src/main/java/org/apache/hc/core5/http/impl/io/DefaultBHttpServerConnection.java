package org.apache.hc.core5.http.impl.io;

import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.config.Http1Config;
import org.apache.hc.core5.http.impl.DefaultContentLengthStrategy;
import org.apache.hc.core5.http.io.*;
import org.apache.hc.core5.util.Args;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Default implementation of {@link HttpServerConnection}.
 *
 * @since 4.3
 */
public class DefaultBHttpServerConnection extends BHttpConnectionBase implements HttpServerConnection {

    private final String scheme;
    private final ContentLengthStrategy incomingContentStrategy;
    private final ContentLengthStrategy outgoingContentStrategy;
    private final HttpMessageParser<ClassicHttpRequest> requestParser;
    private final HttpMessageWriter<ClassicHttpResponse> responseWriter;

    /**
     * Creates new instance of DefaultBHttpServerConnection.
     *
     * @param scheme                  protocol scheme
     * @param http1Config             Message http1Config. If {@code null}
     *                                {@link Http1Config#DEFAULT} will be used.
     * @param charDecoder             decoder to be used for decoding HTTP protocol elements.
     *                                If {@code null} simple type cast will be used for byte to char conversion.
     * @param charEncoder             encoder to be used for encoding HTTP protocol elements.
     *                                If {@code null} simple type cast will be used for char to byte conversion.
     * @param incomingContentStrategy incoming content length strategy. If {@code null}
     *                                {@link DefaultContentLengthStrategy#INSTANCE} will be used.
     * @param outgoingContentStrategy outgoing content length strategy. If {@code null}
     *                                {@link DefaultContentLengthStrategy#INSTANCE} will be used.
     * @param requestParserFactory    request parser factory. If {@code null}
     *                                {@link DefaultHttpRequestParserFactory#INSTANCE} will be used.
     * @param responseWriterFactory   response writer factory. If {@code null}
     *                                {@link DefaultHttpResponseWriterFactory#INSTANCE} will be used.
     */
    public DefaultBHttpServerConnection(
            final String scheme,
            final Http1Config http1Config,
            final CharsetDecoder charDecoder,
            final CharsetEncoder charEncoder,
            final ContentLengthStrategy incomingContentStrategy,
            final ContentLengthStrategy outgoingContentStrategy,
            final HttpMessageParserFactory<ClassicHttpRequest> requestParserFactory,
            final HttpMessageWriterFactory<ClassicHttpResponse> responseWriterFactory) {
        super(http1Config, charDecoder, charEncoder);
        this.scheme = scheme;
        this.requestParser = (requestParserFactory != null ? requestParserFactory :
                DefaultHttpRequestParserFactory.INSTANCE).create(http1Config);
        this.responseWriter = (responseWriterFactory != null ? responseWriterFactory :
                DefaultHttpResponseWriterFactory.INSTANCE).create();
        this.incomingContentStrategy = incomingContentStrategy != null ? incomingContentStrategy :
                DefaultContentLengthStrategy.INSTANCE;
        this.outgoingContentStrategy = outgoingContentStrategy != null ? outgoingContentStrategy :
                DefaultContentLengthStrategy.INSTANCE;
    }

    public DefaultBHttpServerConnection(
            final String scheme,
            final Http1Config http1Config,
            final CharsetDecoder charDecoder,
            final CharsetEncoder charEncoder) {
        this(scheme, http1Config, charDecoder, charEncoder, null, null, null, null);
    }

    public DefaultBHttpServerConnection(
            final String scheme,
            final Http1Config http1Config) {
        this(scheme, http1Config, null, null);
    }

    protected void onRequestReceived(final ClassicHttpRequest request) {
    }

    protected void onResponseSubmitted(final ClassicHttpResponse response) {
    }

    @Override
    public void bind(final Socket socket) throws IOException {
        super.bind(socket);
    }

    @Override
    public ClassicHttpRequest receiveRequestHeader() throws HttpException, IOException {
        final SocketHolder socketHolder = ensureOpen();
        final ClassicHttpRequest request = this.requestParser.parse(this.inBuffer, socketHolder.getInputStream());
        final ProtocolVersion transportVersion = request.getVersion();
        if (transportVersion != null && transportVersion.greaterEquals(HttpVersion.HTTP_2)) {
            throw new UnsupportedHttpVersionException(transportVersion);
        }
        request.setScheme(this.scheme);
        this.version = transportVersion;
        onRequestReceived(request);
        incrementRequestCount();
        return request;
    }

    @Override
    public void receiveRequestEntity(final ClassicHttpRequest request)
            throws HttpException, IOException {
        Args.notNull(request, "HTTP request");
        final SocketHolder socketHolder = ensureOpen();

        final long len = this.incomingContentStrategy.determineLength(request);
        if (len == ContentLengthStrategy.UNDEFINED) {
            return;
        }
        request.setEntity(createIncomingEntity(request, this.inBuffer, socketHolder.getInputStream(), len));
    }

    @Override
    public void sendResponseHeader(final ClassicHttpResponse response)
            throws HttpException, IOException {
        Args.notNull(response, "HTTP response");
        final SocketHolder socketHolder = ensureOpen();
        this.responseWriter.write(response, this.outbuffer, socketHolder.getOutputStream());
        onResponseSubmitted(response);
        if (response.getCode() >= HttpStatus.SC_SUCCESS) {
            incrementResponseCount();
        }
    }

    @Override
    public void sendResponseEntity(final ClassicHttpResponse response)
            throws HttpException, IOException {
        Args.notNull(response, "HTTP response");
        final SocketHolder socketHolder = ensureOpen();
        final HttpEntity entity = response.getEntity();
        if (entity == null) {
            return;
        }
        final long len = this.outgoingContentStrategy.determineLength(response);
        try (final OutputStream outStream = createContentOutputStream(len, this.outbuffer, socketHolder.getOutputStream(), entity.getTrailers())) {
            entity.writeTo(outStream);
        }
    }
}
