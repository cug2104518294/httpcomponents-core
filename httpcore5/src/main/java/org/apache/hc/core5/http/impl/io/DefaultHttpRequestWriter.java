package org.apache.hc.core5.http.impl.io;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpVersion;
import org.apache.hc.core5.http.ProtocolVersion;
import org.apache.hc.core5.http.message.LineFormatter;
import org.apache.hc.core5.http.message.RequestLine;
import org.apache.hc.core5.util.CharArrayBuffer;

import java.io.IOException;

/**
 * HTTP request writer that serializes its output to an instance of
 * {@link org.apache.hc.core5.http.io.SessionOutputBuffer}.
 *
 * @since 4.3
 */
public class DefaultHttpRequestWriter extends AbstractMessageWriter<ClassicHttpRequest> {

    /**
     * Creates an instance of DefaultHttpRequestWriter.
     *
     * @param formatter the line formatter If {@code null}
     *                  {@link org.apache.hc.core5.http.message.BasicLineFormatter#INSTANCE}
     *                  will be used.
     */
    public DefaultHttpRequestWriter(final LineFormatter formatter) {
        super(formatter);
    }

    public DefaultHttpRequestWriter() {
        this(null);
    }

    @Override
    protected void writeHeadLine(
            final ClassicHttpRequest message, final CharArrayBuffer lineBuf) throws IOException {
        final ProtocolVersion transportVersion = message.getVersion();
        getLineFormatter().formatRequestLine(lineBuf, new RequestLine(
                message.getMethod(),
                message.getRequestUri(),
                transportVersion != null ? transportVersion : HttpVersion.HTTP_1_1));
    }

}
