package org.apache.hc.core5.http.impl.io;

import org.apache.hc.core5.http.FormattedHeader;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpMessage;
import org.apache.hc.core5.http.io.HttpMessageWriter;
import org.apache.hc.core5.http.io.SessionOutputBuffer;
import org.apache.hc.core5.http.message.BasicLineFormatter;
import org.apache.hc.core5.http.message.LineFormatter;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.CharArrayBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

/**
 * Abstract base class for HTTP message writers that serialize output to
 * an instance of {@link org.apache.hc.core5.http.io.SessionOutputBuffer}.
 *
 * @since 4.0
 */
public abstract class AbstractMessageWriter<T extends HttpMessage> implements HttpMessageWriter<T> {

    private final CharArrayBuffer lineBuf;
    private final LineFormatter lineFormatter;

    /**
     * Creates an instance of AbstractMessageWriter.
     *
     * @param formatter the line formatter If {@code null} {@link BasicLineFormatter#INSTANCE}
     *                  will be used.
     * @since 4.3
     */
    public AbstractMessageWriter(final LineFormatter formatter) {
        super();
        this.lineFormatter = formatter != null ? formatter : BasicLineFormatter.INSTANCE;
        this.lineBuf = new CharArrayBuffer(128);
    }

    LineFormatter getLineFormatter() {
        return this.lineFormatter;
    }

    /**
     * Subclasses must override this method to write out the first header line
     * based on the {@link HttpMessage} passed as a parameter.
     *
     * @param message the message whose first line is to be written out.
     * @param lineBuf line buffer
     * @throws IOException in case of an I/O error.
     */
    protected abstract void writeHeadLine(T message, CharArrayBuffer lineBuf) throws IOException;

    @Override
    public void write(final T message, final SessionOutputBuffer buffer, final OutputStream outputStream) throws IOException, HttpException {
        Args.notNull(message, "HTTP message");
        Args.notNull(buffer, "Session output buffer");
        Args.notNull(outputStream, "Output stream");
        writeHeadLine(message, this.lineBuf);
        buffer.writeLine(this.lineBuf, outputStream);
        for (final Iterator<Header> it = message.headerIterator(); it.hasNext(); ) {
            final Header header = it.next();
            if (header instanceof FormattedHeader) {
                final CharArrayBuffer chbuffer = ((FormattedHeader) header).getBuffer();
                buffer.writeLine(chbuffer, outputStream);
            } else {
                this.lineBuf.clear();
                lineFormatter.formatHeader(this.lineBuf, header);
                buffer.writeLine(this.lineBuf, outputStream);
            }
        }
        this.lineBuf.clear();
        buffer.writeLine(this.lineBuf, outputStream);
    }

}
