package org.apache.hc.core5.http.message;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ProtocolVersion;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.CharArrayBuffer;

/**
 * Default {@link org.apache.hc.core5.http.message.LineFormatter} implementation.
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class BasicLineFormatter implements LineFormatter {

    public final static BasicLineFormatter INSTANCE = new BasicLineFormatter();

    public BasicLineFormatter() {
        super();
    }

    void formatProtocolVersion(final CharArrayBuffer buffer, final ProtocolVersion version) {
        buffer.append(version.format());
    }

    @Override
    public void formatRequestLine(final CharArrayBuffer buffer, final RequestLine reqline) {
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(reqline, "Request line");
        buffer.append(reqline.getMethod());
        buffer.append(' ');
        buffer.append(reqline.getUri());
        buffer.append(' ');
        formatProtocolVersion(buffer, reqline.getProtocolVersion());
    }

    @Override
    public void formatStatusLine(final CharArrayBuffer buffer, final StatusLine statusLine) {
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(statusLine, "Status line");

        formatProtocolVersion(buffer, statusLine.getProtocolVersion());
        buffer.append(' ');
        buffer.append(Integer.toString(statusLine.getStatusCode()));
        buffer.append(' '); // keep whitespace even if reason phrase is empty
        final String reasonPhrase = statusLine.getReasonPhrase();
        if (reasonPhrase != null) {
            buffer.append(reasonPhrase);
        }
    }

    @Override
    public void formatHeader(final CharArrayBuffer buffer, final Header header) {
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(header, "Header");

        buffer.append(header.getName());
        buffer.append(": ");
        final String value = header.getValue();
        if (value != null) {
            buffer.ensureCapacity(buffer.length() + value.length());
            for (int valueIndex = 0; valueIndex < value.length(); valueIndex++) {
                char valueChar = value.charAt(valueIndex);
                if (valueChar == '\r'
                        || valueChar == '\n'
                        || valueChar == '\f'
                        || valueChar == 0x0b) {
                    valueChar = ' ';
                }
                buffer.append(valueChar);
            }
        }
    }

}
