package org.apache.hc.core5.http.io;

import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.MessageHeaders;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Message writer intended to serialize HTTP message head to an output stream.
 * <p>
 * 消息编写器旨在将HTTP消息头序列化为输出流。
 *
 * @since 4.0
 */
public interface HttpMessageWriter<T extends MessageHeaders> {

    /**
     * Serializes an instance of {@link MessageHeaders} to the given output stream.
     *
     * @param message HTTP message head
     * @param buffer  session output buffer
     * @throws IOException   in case of an I/O error
     * @throws HttpException in case of HTTP protocol violation
     */
    void write(T message, SessionOutputBuffer buffer, OutputStream outputStream) throws IOException, HttpException;

}
