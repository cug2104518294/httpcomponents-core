package org.apache.hc.core5.http.impl;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.util.Args;

/**
 * The default implementation of the content length strategy. This class
 * will throw {@link ProtocolException} if it encounters an unsupported
 * transfer encoding, multiple {@code Content-Length} header
 * values or a malformed {@code Content-Length} header value.
 * <p>
 * <p>
 * This class recognizes "chunked" transfer-coding only.
 *
 * @since 5.0
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class DefaultContentLengthStrategy implements ContentLengthStrategy {

    public static final DefaultContentLengthStrategy INSTANCE = new DefaultContentLengthStrategy();

    /**
     * Creates {@code DefaultContentLengthStrategy} instance. {@link ContentLengthStrategy#UNDEFINED}
     * is used per default when content length is not explicitly specified in the message.
     */
    public DefaultContentLengthStrategy() {
    }


    /**
     * 根据报文去获取相对应的头部信息中的长度
     **/
    @Override
    public long determineLength(final HttpMessage message) throws HttpException {
        Args.notNull(message, "HTTP message");
        // Although Transfer-Encoding is specified as a list, in practice
        // it is either missing or has the single value "chunked". So we
        // treat it as a single-valued header here.
        final Header transferEncodingHeader = message.getFirstHeader(HttpHeaders.TRANSFER_ENCODING);
        if (transferEncodingHeader != null) {
            final String headerValue = transferEncodingHeader.getValue();
            if (HeaderElements.CHUNKED_ENCODING.equalsIgnoreCase(headerValue)) {
                return CHUNKED;
            }
            throw new NotImplementedException("Unsupported transfer encoding: " + headerValue);
        }
        if (message.countHeaders(HttpHeaders.CONTENT_LENGTH) > 1) {
            throw new ProtocolException("Multiple Content-Length headers");
        }
        final Header contentLengthHeader = message.getFirstHeader(HttpHeaders.CONTENT_LENGTH);
        if (contentLengthHeader != null) {
            final String s = contentLengthHeader.getValue();
            try {
                final long len = Long.parseLong(s);
                if (len < 0) {
                    throw new ProtocolException("Negative content length: " + s);
                }
                return len;
            } catch (final NumberFormatException e) {
                throw new ProtocolException("Invalid content length: " + s);
            }
        }
        return UNDEFINED;
    }

}
