package org.apache.hc.core5.http.io;

import org.apache.hc.core5.http.MessageHeaders;

/**
 * Factory for {@link HttpMessageWriter} instances.
 *
 * @since 4.3
 */
public interface HttpMessageWriterFactory<T extends MessageHeaders> {

    HttpMessageWriter<T> create();

}
