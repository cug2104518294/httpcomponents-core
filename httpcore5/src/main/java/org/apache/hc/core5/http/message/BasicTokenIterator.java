package org.apache.hc.core5.http.message;

import java.util.BitSet;
import java.util.Iterator;

import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.util.TextUtils;

/**
 * {@link java.util.Iterator} of {@link org.apache.hc.core5.http.Header} tokens..
 *
 * @since 4.0
 */
public class BasicTokenIterator extends AbstractHeaderElementIterator<String> {

    private static final BitSet COMMA = TokenParser.INIT_BITSET(',');

    private final TokenParser parser;

    /**
     * Creates a new instance of {@link BasicTokenIterator}.
     *
     * @param headerIterator    the iterator for the headers to tokenize
     */
    public BasicTokenIterator(final Iterator<Header> headerIterator) {
        super(headerIterator);
        this.parser = TokenParser.INSTANCE;
    }

    @Override
    String parseHeaderElement(final CharSequence buf, final ParserCursor cursor) {
        final String token = this.parser.parseToken(buf, cursor, COMMA);
        if (!cursor.atEnd()) {
            final int pos = cursor.getPos();
            if (buf.charAt(pos) == ',') {
                cursor.updatePos(pos + 1);
            }
        }
        return !TextUtils.isBlank(token) ? token : null;
    }

}

