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
     * </p>
     *
     * @return {@code true} if the header should be considered sensitive.
     * @since 5.0
     */
    boolean isSensitive();

}
