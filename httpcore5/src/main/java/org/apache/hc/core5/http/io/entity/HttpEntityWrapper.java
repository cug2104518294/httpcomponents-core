package org.apache.hc.core5.http.io.entity;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.function.Supplier;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.util.Args;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

/**
 * Base class for wrapping entities that delegates all calls to the wrapped entity.
 * Implementations can derive from this class and override only those methods that
 * should not be delegated to the wrapped entity.
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public class HttpEntityWrapper implements HttpEntity {

    /**
     * The wrapped entity.
     */
    private final HttpEntity wrappedEntity;

    /**
     * Creates a new entity wrapper.
     *
     * @param wrappedEntity the entity to wrap.
     */
    public HttpEntityWrapper(final HttpEntity wrappedEntity) {
        super();
        this.wrappedEntity = Args.notNull(wrappedEntity, "Wrapped entity");
    }

    @Override
    public boolean isRepeatable() {
        return wrappedEntity.isRepeatable();
    }

    @Override
    public boolean isChunked() {
        return wrappedEntity.isChunked();
    }

    @Override
    public long getContentLength() {
        return wrappedEntity.getContentLength();
    }

    @Override
    public String getContentType() {
        return wrappedEntity.getContentType();
    }

    @Override
    public String getContentEncoding() {
        return wrappedEntity.getContentEncoding();
    }

    @Override
    public InputStream getContent()
            throws IOException {
        return wrappedEntity.getContent();
    }

    @Override
    public void writeTo(final OutputStream outStream)
            throws IOException {
        wrappedEntity.writeTo(outStream);
    }

    @Override
    public boolean isStreaming() {
        return wrappedEntity.isStreaming();
    }

    @Override
    public Supplier<List<? extends Header>> getTrailers() {
        return wrappedEntity.getTrailers();
    }

    @Override
    public Set<String> getTrailerNames() {
        return wrappedEntity.getTrailerNames();
    }

    @Override
    public void close() throws IOException {
        wrappedEntity.close();
    }

    @Override
    public String toString() {
        return "Wrapper [" + wrappedEntity + "]";
    }

}
