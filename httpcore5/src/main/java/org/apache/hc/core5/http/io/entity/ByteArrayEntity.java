package org.apache.hc.core5.http.io.entity;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.util.Args;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A self contained, repeatable entity that obtains its content from a byte array.
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public class ByteArrayEntity extends AbstractHttpEntity {

    private final byte[] b;
    private final int off, len;

    /**
     * @since 5.0
     */
    public ByteArrayEntity(
            final byte[] b, final int off, final int len, final ContentType contentType, final String contentEncoding,
            final boolean chunked) {
        super(contentType, contentEncoding, chunked);
        Args.notNull(b, "Source byte array");
        if ((off < 0) || (off > b.length) || (len < 0) ||
                ((off + len) < 0) || ((off + len) > b.length)) {
            throw new IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + b.length);
        }
        this.b = b;
        this.off = off;
        this.len = len;
    }

    /**
     * @since 5.0
     */
    public ByteArrayEntity(
            final byte[] b, final int off, final int len, final ContentType contentType, final String contentEncoding) {
        this(b, off, len, contentType, contentEncoding, false);
    }

    /**
     * @since 5.0
     */
    public ByteArrayEntity(
            final byte[] b, final ContentType contentType, final String contentEncoding, final boolean chunked) {
        super(contentType, contentEncoding, chunked);
        Args.notNull(b, "Source byte array");
        this.b = b;
        this.off = 0;
        this.len = this.b.length;
    }

    /**
     * @since 5.0
     */
    public ByteArrayEntity(final byte[] b, final ContentType contentType, final String contentEncoding) {
        this(b, contentType, contentEncoding, false);
    }

    public ByteArrayEntity(final byte[] b, final ContentType contentType, final boolean chunked) {
        this(b, contentType, null, chunked);
    }

    public ByteArrayEntity(final byte[] b, final ContentType contentType) {
        this(b, contentType, null, false);
    }

    public ByteArrayEntity(
            final byte[] b, final int off, final int len, final ContentType contentType, final boolean chunked) {
        this(b, off, len, contentType, null, chunked);
    }

    public ByteArrayEntity(final byte[] b, final int off, final int len, final ContentType contentType) {
        this(b, off, len, contentType, null, false);
    }

    @Override
    public final boolean isRepeatable() {
        return true;
    }

    @Override
    public final long getContentLength() {
        return this.len;
    }

    @Override
    public final InputStream getContent() {
        return new ByteArrayInputStream(this.b, this.off, this.len);
    }

    @Override
    public final void writeTo(final OutputStream outStream) throws IOException {
        Args.notNull(outStream, "Output stream");
        outStream.write(this.b, this.off, this.len);
        outStream.flush();
    }

    @Override
    public final boolean isStreaming() {
        return false;
    }

    @Override
    public final void close() throws IOException {
    }

}
