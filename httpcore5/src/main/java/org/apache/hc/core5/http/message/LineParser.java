package org.apache.hc.core5.http.message;

import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.util.CharArrayBuffer;

/**
 * Interface for parsing lines in the HEAD section of an HTTP message.
 * There are individual methods for parsing a request line, a
 * status line, or a header line.
 * Instances of this interface are expected to be stateless and thread-safe.
 * <p>
 * <p>
 * 用于解析HTTP消息的HEAD部分中的行的接口。
 * 解析请求行，状态行或标题行有各种方法。
 * 此接口的实例应该是无状态和线程安全的。
 *
 * @since 4.0
 */
public interface LineParser {

    /**
     * Parses a request line from the given buffer containing one line of text.
     *
     * @param buffer a buffer holding a line to parse
     * @return the parsed request line
     * @throws ParseException in case of a parse error
     */
    RequestLine parseRequestLine(CharArrayBuffer buffer) throws ParseException;

    /**
     * Parses a status line from the given buffer containing one line of text.
     *
     * @param buffer a buffer holding a line to parse
     * @return the parsed status line
     * @throws ParseException in case of a parse error
     */
    StatusLine parseStatusLine(CharArrayBuffer buffer) throws ParseException;

    /**
     * Parses a header from the given buffer containing one line of text.
     * The full header line is expected here. Header continuation
     * lines must be joined by the caller before invoking this method.
     *
     * @param buffer a buffer holding the full header line.
     * @return the header in the argument buffer.
     * @throws ParseException in case of a parse error
     */
    Header parseHeader(CharArrayBuffer buffer) throws ParseException;

}
