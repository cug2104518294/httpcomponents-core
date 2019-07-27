package org.apache.hc.core5.http.io;

import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.MessageHeaders;

import java.io.IOException;
import java.io.InputStream;

/**
 * Message parser intended to build HTTP message head from an input stream.
 *
 * @param <T> {@link MessageHeaders} or a subclass
 * @since 4.0
 */
public interface HttpMessageParser<T extends MessageHeaders> {

    /**
     * Generates an instance of {@link MessageHeaders} from the given input stream..
     *
     * @param buffer      Session input buffer
     * @param inputStream Input stream
     * @return HTTP message head
     * @throws IOException   in case of an I/O error
     * @throws HttpException in case of HTTP protocol violation
     */
    T parse(SessionInputBuffer buffer, InputStream inputStream) throws IOException, HttpException;

}
