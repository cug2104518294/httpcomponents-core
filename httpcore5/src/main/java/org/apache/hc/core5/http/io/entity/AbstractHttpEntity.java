package org.apache.hc.core5.http.io.entity;

import org.apache.hc.core5.function.Supplier;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.util.Args;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Abstract base class for mutable entities.
 * Provides the commonly used attributes for streamed and
 * self-contained implementations.
 *
 * 重点就是writeTo方法 将对应的entity实体转成stream
 *
 * @since 4.0
 */
public abstract class AbstractHttpEntity implements HttpEntity {

    static final int OUTPUT_BUFFER_SIZE = 4096;

    private final String contentType;
    private final String contentEncoding;
    private final boolean chunked;

    protected AbstractHttpEntity(final String contentType, final String contentEncoding, final boolean chunked) {
        this.contentType = contentType;
        this.contentEncoding = contentEncoding;
        this.chunked = chunked;
    }

    protected AbstractHttpEntity(final ContentType contentType, final String contentEncoding, final boolean chunked) {
        this.contentType = contentType != null ? contentType.toString() : null;
        this.contentEncoding = contentEncoding;
        this.chunked = chunked;
    }

    protected AbstractHttpEntity(final String contentType, final String contentEncoding) {
        this(contentType, contentEncoding, false);
    }

    protected AbstractHttpEntity(final ContentType contentType, final String contentEncoding) {
        this(contentType, contentEncoding, false);
    }

    public static void writeTo(final HttpEntity entity, final OutputStream outStream) throws IOException {
        Args.notNull(entity, "Entity");
        Args.notNull(outStream, "Output stream");
        try (final InputStream inStream = entity.getContent()) {
            if (inStream != null) {
                int count;
                final byte[] tmp = new byte[OUTPUT_BUFFER_SIZE];
                while ((count = inStream.read(tmp)) != -1) {
                    outStream.write(tmp, 0, count);
                }
            }
        }
    }

    @Override
    public void writeTo(final OutputStream outStream) throws IOException {
        writeTo(this, outStream);
    }

    @Override
    public final String getContentType() {
        return contentType;
    }

    @Override
    public final String getContentEncoding() {
        return contentEncoding;
    }

    @Override
    public final boolean isChunked() {
        return chunked;
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public Supplier<List<? extends Header>> getTrailers() {
        return null;
    }

    @Override
    public Set<String> getTrailerNames() {
        return Collections.emptySet();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[Entity-Class: ");
        sb.append(getClass().getSimpleName());
        sb.append(", Content-Type: ");
        sb.append(contentType);
        sb.append(", Content-Encoding: ");
        sb.append(contentEncoding);
        sb.append(", chunked: ");
        sb.append(chunked);
        sb.append(']');
        return sb.toString();
    }

}
