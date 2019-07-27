package org.apache.hc.core5.http.io.entity;

import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.util.Args;

import java.io.*;

/**
 * A wrapping entity that buffers it content if necessary.
 * The buffered entity is always repeatable.
 * If the wrapped entity is repeatable itself, calls are passed through.
 * If the wrapped entity is not repeatable, the content is read into a
 * buffer once and provided from there as often as required.
 *
 * @since 4.0
 */
public class BufferedHttpEntity extends HttpEntityWrapper {

    private final byte[] buffer;

    /**
     * Creates a new buffered entity wrapper.
     *
     * @param entity the entity to wrap, not null
     * @throws IllegalArgumentException if wrapped is null
     */
    public BufferedHttpEntity(final HttpEntity entity) throws IOException {
        super(entity);
        if (!entity.isRepeatable() || entity.getContentLength() < 0) {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            entity.writeTo(out);
            out.flush();
            this.buffer = out.toByteArray();
        } else {
            this.buffer = null;
        }
    }

    @Override
    public long getContentLength() {
        if (this.buffer != null) {
            return this.buffer.length;
        }
        return super.getContentLength();
    }

    @Override
    public InputStream getContent() throws IOException {
        if (this.buffer != null) {
            return new ByteArrayInputStream(this.buffer);
        }
        return super.getContent();
    }

    /**
     * Tells that this entity does not have to be chunked.
     *
     * @return {@code false}
     */
    @Override
    public boolean isChunked() {
        return (buffer == null) && super.isChunked();
    }

    /**
     * Tells that this entity is repeatable.
     *
     * @return {@code true}
     */
    @Override
    public boolean isRepeatable() {
        return true;
    }


    @Override
    public void writeTo(final OutputStream outStream) throws IOException {
        Args.notNull(outStream, "Output stream");
        if (this.buffer != null) {
            outStream.write(this.buffer);
        } else {
            super.writeTo(outStream);
        }
    }


    // non-javadoc, see interface HttpEntity
    @Override
    public boolean isStreaming() {
        return (buffer == null) && super.isStreaming();
    }

} // class BufferedHttpEntity
