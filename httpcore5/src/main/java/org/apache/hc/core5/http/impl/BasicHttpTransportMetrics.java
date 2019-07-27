package org.apache.hc.core5.http.impl;

import org.apache.hc.core5.http.io.HttpTransportMetrics;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Default implementation of {@link HttpTransportMetrics}.
 *
 * @since 4.0
 */
public class BasicHttpTransportMetrics implements HttpTransportMetrics {

    private final AtomicLong bytesTransferred;

    public BasicHttpTransportMetrics() {
        this.bytesTransferred = new AtomicLong(0);
    }

    @Override
    public long getBytesTransferred() {
        return this.bytesTransferred.get();
    }

    public void incrementBytesTransferred(final long count) {
        this.bytesTransferred.addAndGet(count);
    }

}
