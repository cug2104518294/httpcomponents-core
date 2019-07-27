package org.apache.hc.core5.http;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;

/**
 * Represents a strategy to determine length of the enclosed content entity
 * based on properties of the HTTP message.
 * <p>
 * 表示基于HTTP消息的属性确定所包含的内容实体的长度的策略。
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.STATELESS)
public interface ContentLengthStrategy {

    /**
     * Message body chunk coded
     */
    long CHUNKED = -1;

    /**
     * Message body not explicitly delineated. Legal for HTTP response messages
     * and illegal for HTTP request messages.
     */
    long UNDEFINED = -Long.MAX_VALUE;

    /**
     * Returns length of the given message in bytes. The returned value
     * must be a non-negative number, {@link #CHUNKED} if the message is
     * chunk coded, or {@link #UNDEFINED} if the message is not explicitly
     * delineated.
     *
     * @param message HTTP message
     * @return content length, {@link #UNDEFINED}, or {@link #CHUNKED}
     * @throws HttpException in case of HTTP protocol violation
     */
    long determineLength(HttpMessage message) throws HttpException;

}
