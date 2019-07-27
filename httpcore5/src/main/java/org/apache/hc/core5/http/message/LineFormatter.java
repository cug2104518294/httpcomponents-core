package org.apache.hc.core5.http.message;

import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.util.CharArrayBuffer;

/**
 * Interface for formatting elements of the HEAD section of an HTTP message.
 * There are individual methods for formatting a request line, a
 * status line, or a header line. The formatting methods are expected to produce
 * one line of formatted content that does <i>not</i> include a line delimiter
 * (such as CR-LF). Instances of this interface are expected to be stateless and
 * thread-safe.
 *
 * @since 4.0
 */
public interface LineFormatter {

    /**
     * Formats a request line.
     *
     * @param buffer  buffer to write formatted content to.
     * @param reqline the request line to format
     */
    void formatRequestLine(CharArrayBuffer buffer, RequestLine reqline);

    /**
     * Formats a status line.
     *
     * @param buffer   buffer to write formatted content to.
     * @param statline the status line to format
     */
    void formatStatusLine(CharArrayBuffer buffer, StatusLine statline);

    /**
     * Formats a header.
     *
     * @param buffer buffer to write formatted content to.
     * @param header the header to format
     */
    void formatHeader(CharArrayBuffer buffer, Header header);

}
