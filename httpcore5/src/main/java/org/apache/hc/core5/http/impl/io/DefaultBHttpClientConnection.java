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
 * Default implementation of {@link HttpClientConnection}.
 *
 * @since 4.3
 */
public class DefaultBHttpClientConnection extends BHttpConnectionBase
        implements HttpClientConnection {

    //http协议最后都是要通过对应的socket接口 将对应的数据先放在charArray中 然后写入到输入输出流中
    //通过底层的tcp和udp进行通信  最后才是去完成对应的请求
    private final HttpMessageParser<ClassicHttpResponse> responseParser;
    private final HttpMessageWriter<ClassicHttpRequest> requestWriter;
    private final ContentLengthStrategy incomingContentStrategy;
    private final ContentLengthStrategy outgoingContentStrategy;
    private volatile boolean consistent;

    /**
     * Creates new instance of DefaultBHttpClientConnection.
     *
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
     * @param requestWriterFactory    request writer factory. If {@code null}
     *                                {@link DefaultHttpRequestWriterFactory#INSTANCE} will be used.
     * @param responseParserFactory   response parser factory. If {@code null}
     *                                {@link DefaultHttpResponseParserFactory#INSTANCE} will be used.
     */
    public DefaultBHttpClientConnection(
            final Http1Config http1Config,
            final CharsetDecoder charDecoder,
            final CharsetEncoder charEncoder,
            final ContentLengthStrategy incomingContentStrategy,
            final ContentLengthStrategy outgoingContentStrategy,
            final HttpMessageWriterFactory<ClassicHttpRequest> requestWriterFactory,
            final HttpMessageParserFactory<ClassicHttpResponse> responseParserFactory) {
        super(http1Config, charDecoder, charEncoder);
        this.requestWriter = (requestWriterFactory != null ? requestWriterFactory :
                DefaultHttpRequestWriterFactory.INSTANCE).create();
        this.responseParser = (responseParserFactory != null ? responseParserFactory :
                DefaultHttpResponseParserFactory.INSTANCE).create(http1Config);
        this.incomingContentStrategy = incomingContentStrategy != null ? incomingContentStrategy :
                DefaultContentLengthStrategy.INSTANCE;
        this.outgoingContentStrategy = outgoingContentStrategy != null ? outgoingContentStrategy :
                DefaultContentLengthStrategy.INSTANCE;
        this.consistent = true;
    }

    public DefaultBHttpClientConnection(
            final Http1Config http1Config,
            final CharsetDecoder charDecoder,
            final CharsetEncoder charEncoder) {
        this(http1Config, charDecoder, charEncoder, null, null, null, null);
    }

    public DefaultBHttpClientConnection(final Http1Config http1Config) {
        this(http1Config, null, null);
    }

    protected void onResponseReceived(final ClassicHttpResponse response) {
    }

    protected void onRequestSubmitted(final ClassicHttpRequest request) {
    }

    @Override
    public void bind(final Socket socket) throws IOException {
        super.bind(socket);
    }

    @Override
    public void sendRequestHeader(final ClassicHttpRequest request)
            throws HttpException, IOException {
        Args.notNull(request, "HTTP request");
        final SocketHolder socketHolder = ensureOpen();
        this.requestWriter.write(request, this.outbuffer, socketHolder.getOutputStream());
        onRequestSubmitted(request);
        incrementRequestCount();
    }

    @Override
    public void sendRequestEntity(final ClassicHttpRequest request) throws HttpException, IOException {
        Args.notNull(request, "HTTP request");
        final SocketHolder socketHolder = ensureOpen();
        final HttpEntity entity = request.getEntity();
        if (entity == null) {
            return;
        }
        final long len = this.outgoingContentStrategy.determineLength(request);
        if (len == ContentLengthStrategy.UNDEFINED) {
            throw new LengthRequiredException();
        }
        try (final OutputStream outStream = createContentOutputStream(len, this.outbuffer, socketHolder.getOutputStream(), entity.getTrailers())) {
            entity.writeTo(outStream);
        }
    }

    @Override
    public boolean isConsistent() {
        return this.consistent;
    }

    @Override
    public void terminateRequest(final ClassicHttpRequest request) throws HttpException, IOException {
        Args.notNull(request, "HTTP request");
        final SocketHolder socketHolder = ensureOpen();
        final HttpEntity entity = request.getEntity();
        if (entity == null) {
            return;
        }
        final long len = this.outgoingContentStrategy.determineLength(request);
        if (len == ContentLengthStrategy.CHUNKED) {
            try (final OutputStream outStream = createContentOutputStream(len, this.outbuffer, socketHolder.getOutputStream(), entity.getTrailers())) {
                // just close
            }
        } else if (len >= 0 && len <= 1024) {
            try (final OutputStream outStream = createContentOutputStream(len, this.outbuffer, socketHolder.getOutputStream(), null)) {
                entity.writeTo(outStream);
            }
        } else {
            this.consistent = false;
        }
    }

    @Override
    public ClassicHttpResponse receiveResponseHeader() throws HttpException, IOException {
        final SocketHolder socketHolder = ensureOpen();
        final ClassicHttpResponse response = this.responseParser.parse(this.inBuffer, socketHolder.getInputStream());
        final ProtocolVersion transportVersion = response.getVersion();
        if (transportVersion != null && transportVersion.greaterEquals(HttpVersion.HTTP_2)) {
            throw new UnsupportedHttpVersionException(transportVersion);
        }
        this.version = transportVersion;
        onResponseReceived(response);
        final int status = response.getCode();
        if (status < HttpStatus.SC_INFORMATIONAL) {
            throw new ProtocolException("Invalid response: " + status);
        }
        if (response.getCode() >= HttpStatus.SC_SUCCESS) {
            incrementResponseCount();
        }
        return response;
    }

    @Override
    public void receiveResponseEntity(final ClassicHttpResponse response) throws HttpException, IOException {
        Args.notNull(response, "HTTP response");
        final SocketHolder socketHolder = ensureOpen();
        final long len = this.incomingContentStrategy.determineLength(response);
        response.setEntity(createIncomingEntity(response, this.inBuffer, socketHolder.getInputStream(), len));
    }
}
