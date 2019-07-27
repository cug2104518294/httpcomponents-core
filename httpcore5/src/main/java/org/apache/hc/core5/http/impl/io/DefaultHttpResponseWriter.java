package org.apache.hc.core5.http.impl.io;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpVersion;
import org.apache.hc.core5.http.ProtocolVersion;
import org.apache.hc.core5.http.message.LineFormatter;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.util.CharArrayBuffer;

import java.io.IOException;

/**
 * HTTP response writer that serializes its output to an instance of
 * {@link org.apache.hc.core5.http.io.SessionOutputBuffer}.
 *
 * @since 4.3
 */
public class DefaultHttpResponseWriter extends AbstractMessageWriter<ClassicHttpResponse> {

    /**
     * Creates an instance of DefaultHttpResponseWriter.
     *
     * @param formatter the line formatter If {@code null}
     *                  {@link org.apache.hc.core5.http.message.BasicLineFormatter#INSTANCE}
     *                  will be used.
     */
    public DefaultHttpResponseWriter(final LineFormatter formatter) {
        super(formatter);
    }

    public DefaultHttpResponseWriter() {
        super(null);
    }

    @Override
    protected void writeHeadLine(
            final ClassicHttpResponse message, final CharArrayBuffer lineBuf) throws IOException {
        final ProtocolVersion transportVersion = message.getVersion();
        getLineFormatter().formatStatusLine(lineBuf, new StatusLine(
                transportVersion != null ? transportVersion : HttpVersion.HTTP_1_1,
                message.getCode(),
                message.getReasonPhrase()));
    }

}
