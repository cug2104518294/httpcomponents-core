package org.apache.hc.core5.http.message;

import org.apache.hc.core5.http.FormattedHeader;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.CharArrayBuffer;

import java.io.Serializable;

/**
 * This class represents a raw HTTP header whose content is parsed 'on demand'
 * only when the header value needs to be consumed.
 *
 * @since 4.0
 */
public class BufferedHeader implements FormattedHeader, Serializable {

    private static final long serialVersionUID = -2768352615787625448L;

    /**
     * Header name.
     */
    private final String name;

    /**
     * The buffer containing the entire header line.
     */
    private final CharArrayBuffer buffer;

    /**
     * The beginning of the header value in the buffer
     */
    private final int valuePos;

    /**
     * @since 5.0
     */
    public static BufferedHeader create(final CharArrayBuffer buffer) {
        try {
            return new BufferedHeader(buffer);
        } catch (final ParseException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    /**
     * Creates a new header from a buffer.
     * The name of the header will be parsed immediately,
     * the value only if it is accessed.
     *
     * @param buffer the buffer containing the header to represent
     * @throws ParseException in case of a parse error
     */
    public BufferedHeader(final CharArrayBuffer buffer) throws ParseException {
        this(buffer, true);
    }

    BufferedHeader(final CharArrayBuffer buffer, final boolean strict) throws ParseException {
        super();
        Args.notNull(buffer, "Char array buffer");
        final int colon = buffer.indexOf(':');
        if (colon <= 0) {
            throw new ParseException("Invalid header", buffer, 0, buffer.length());
        }
        if (strict && TokenParser.isWhitespace(buffer.charAt(colon - 1))) {
            throw new ParseException("Invalid header", buffer, 0, buffer.length(), colon - 1);
        }
        final String s = buffer.substringTrimmed(0, colon);
        if (s.isEmpty()) {
            throw new ParseException("Invalid header", buffer, 0, buffer.length(), colon);
        }
        this.buffer = buffer;
        this.name = s;
        this.valuePos = colon + 1;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.buffer.substringTrimmed(this.valuePos, this.buffer.length());
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public int getValuePos() {
        return this.valuePos;
    }

    @Override
    public CharArrayBuffer getBuffer() {
        return this.buffer;
    }

    @Override
    public String toString() {
        return this.buffer.toString();
    }

}
