package org.apache.hc.core5.http.impl.io;

import org.apache.hc.core5.http.StreamClosedException;
import org.apache.hc.core5.http.io.SessionInputBuffer;
import org.apache.hc.core5.util.Args;

import java.io.IOException;
import java.io.InputStream;

/**
 * Input stream that reads data without any transformation. The end of the
 * content entity is demarcated by closing the underlying connection
 * (EOF condition). Entities transferred using this input stream can be of
 * unlimited length.
 * <p>
 * Note that this class NEVER closes the underlying stream, even when close
 * gets called.  Instead, it will read until the end of the stream (until
 * {@code -1} is returned).
 *
 * @since 4.0
 */
public class IdentityInputStream extends InputStream {

    private final SessionInputBuffer buffer;
    private final InputStream inputStream;

    private boolean closed = false;

    /**
     * Default constructor.
     *
     * @param buffer      Session input buffer
     * @param inputStream Input stream
     */
    public IdentityInputStream(final SessionInputBuffer buffer, final InputStream inputStream) {
        super();
        this.buffer = Args.notNull(buffer, "Session input buffer");
        this.inputStream = Args.notNull(inputStream, "Input stream");
    }

    @Override
    public int available() throws IOException {
        if (this.closed) {
            return 0;
        }
        final int n = this.buffer.length();
        return n > 0 ? n : this.inputStream.available();
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
    }

    @Override
    public int read() throws IOException {
        if (this.closed) {
            throw new StreamClosedException();
        }
        return this.buffer.read(this.inputStream);
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        if (this.closed) {
            throw new StreamClosedException();
        }
        return this.buffer.read(b, off, len, this.inputStream);
    }

}
