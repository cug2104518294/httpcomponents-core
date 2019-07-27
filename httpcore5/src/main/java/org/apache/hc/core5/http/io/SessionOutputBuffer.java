package org.apache.hc.core5.http.io;

import org.apache.hc.core5.util.CharArrayBuffer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Session output buffer for blocking HTTP/1.1 connections.
 * <p>
 * 用于HTTP / 1.1连接的会话输出缓冲区。
 * <p>
 * This interface facilitates intermediate buffering of output data streamed out
 * to an output stream and provides provides methods for writing lines of text.
 *
 * @since 4.0
 */
public interface SessionOutputBuffer {

    /**
     * Return length data stored in the buffer
     *
     * @return data length
     */
    int length();

    /**
     * Returns total capacity of the buffer
     *
     * @return total capacity
     */
    int capacity();

    /**
     * Returns available space in the buffer.
     *
     * @return available space.
     */
    int available();

    /**
     * Writes {@code len} bytes from the specified byte array
     * starting at offset {@code off} to this session buffer.
     * <p>
     * If {@code off} is negative, or {@code len} is negative, or
     * {@code off+len} is greater than the length of the array
     * {@code b}, then an {@code IndexOutOfBoundsException} is thrown.
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     * @throws IOException if an I/O error occurs.
     */
    void write(byte[] b, int off, int len, OutputStream outputStream) throws IOException;

    /**
     * Writes {@code b.length} bytes from the specified byte array
     * to this session buffer.
     *
     * @param b the data.
     * @throws IOException if an I/O error occurs.
     */
    void write(byte[] b, OutputStream outputStream) throws IOException;

    /**
     * Writes the specified byte to this session buffer.
     *
     * @param b the {@code byte}.
     * @throws IOException if an I/O error occurs.
     */
    void write(int b, OutputStream outputStream) throws IOException;

    /**
     * Writes characters from the specified char array followed by a line
     * delimiter to this session buffer.
     * <p>
     * The choice of a char encoding and line delimiter sequence is up to the
     * specific implementations of this interface.
     *
     * @param buffer the buffer containing chars of the line.
     * @throws IOException if an I/O error occurs.
     */
    void writeLine(CharArrayBuffer buffer, OutputStream outputStream) throws IOException;

    /**
     * Flushes this session buffer and forces any buffered output bytes
     * to be written out. The general contract of {@code flush} is
     * that calling it is an indication that, if any bytes previously
     * written have been buffered by the implementation of the output
     * stream, such bytes should immediately be written to their
     * intended destination.
     *
     * @throws IOException if an I/O error occurs.
     */
    void flush(OutputStream outputStream) throws IOException;

    /**
     * Returns {@link HttpTransportMetrics} for this session buffer.
     *
     * @return transport metrics.
     */
    HttpTransportMetrics getMetrics();

}
