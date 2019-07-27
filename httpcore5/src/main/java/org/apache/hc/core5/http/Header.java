package org.apache.hc.core5.http;

/**
 * Represents an HTTP header field consisting of a field name and a field
 * value.
 */
public interface Header extends NameValuePair {

    /**
     * Returns {@code true} if the header should be considered sensitive.
     * <p>
     * Some encoding schemes such as HPACK impose restrictions on encoded
     * representation of sensitive headers.
     * <p>
     * 某些编码方案（如HPACK）对敏感标头的编码表示施加了限制。
     * </p>
     *
     * @return {@code true} if the header should be considered sensitive.
     * @since 5.0
     */
    boolean isSensitive();

}
