package org.apache.hc.core5.http2.impl;

import org.apache.hc.core5.http.impl.BasicHttpTransportMetrics;
import org.apache.hc.core5.http2.H2TransportMetrics;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Default implementation of {@link H2TransportMetrics}.
 *
 * @since 5.0
 */
public class BasicH2TransportMetrics extends BasicHttpTransportMetrics implements H2TransportMetrics {

    private final AtomicLong framesTransferred;

    public BasicH2TransportMetrics() {
        this.framesTransferred = new AtomicLong(0);
    }

    @Override
    public long getFramesTransferred() {
        return framesTransferred.get();
    }

    public void incrementFramesTransferred() {
        framesTransferred.incrementAndGet();
    }

}
