package org.apache.hc.core5.http.config;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.util.Args;

/**
 * HTTP/1.1 char coding configuration.
 *
 * @since 4.3
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class CharCodingConfig {

    public static final CharCodingConfig DEFAULT = new Builder().build();

    private final Charset charset;
    private final CodingErrorAction malformedInputAction;
    private final CodingErrorAction unmappableInputAction;

    CharCodingConfig(
            final Charset charset,
            final CodingErrorAction malformedInputAction,
            final CodingErrorAction unmappableInputAction) {
        super();
        this.charset = charset;
        this.malformedInputAction = malformedInputAction;
        this.unmappableInputAction = unmappableInputAction;
    }

    public Charset getCharset() {
        return charset;
    }

    public CodingErrorAction getMalformedInputAction() {
        return malformedInputAction;
    }

    public CodingErrorAction getUnmappableInputAction() {
        return unmappableInputAction;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[charset=").append(this.charset)
                .append(", malformedInputAction=").append(this.malformedInputAction)
                .append(", unmappableInputAction=").append(this.unmappableInputAction)
                .append("]");
        return builder.toString();
    }

    public static CharCodingConfig.Builder custom() {
        return new Builder();
    }

    public static CharCodingConfig.Builder copy(final CharCodingConfig config) {
        Args.notNull(config, "Config");
        return new Builder()
            .setCharset(config.getCharset())
            .setMalformedInputAction(config.getMalformedInputAction())
            .setUnmappableInputAction(config.getUnmappableInputAction());
    }

    public static class Builder {

        private Charset charset;
        private CodingErrorAction malformedInputAction;
        private CodingErrorAction unmappableInputAction;

        Builder() {
        }

        public Builder setCharset(final Charset charset) {
            this.charset = charset;
            return this;
        }

        public Builder setMalformedInputAction(final CodingErrorAction malformedInputAction) {
            this.malformedInputAction = malformedInputAction;
            if (malformedInputAction != null && this.charset == null) {
                this.charset = StandardCharsets.US_ASCII;
            }
            return this;
        }

        public Builder setUnmappableInputAction(final CodingErrorAction unmappableInputAction) {
            this.unmappableInputAction = unmappableInputAction;
            if (unmappableInputAction != null && this.charset == null) {
                this.charset = StandardCharsets.US_ASCII;
            }
            return this;
        }

        public CharCodingConfig build() {
            Charset charsetCopy = charset;
            if (charsetCopy == null && (malformedInputAction != null || unmappableInputAction != null)) {
                charsetCopy = StandardCharsets.US_ASCII;
            }
            return new CharCodingConfig(charsetCopy, malformedInputAction, unmappableInputAction);
        }

    }

}
