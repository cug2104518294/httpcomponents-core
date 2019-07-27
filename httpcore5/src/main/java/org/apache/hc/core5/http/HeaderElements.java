package org.apache.hc.core5.http;

/**
 * Constants for frequently used Header elements.
 *
 * @since 5.0
 */
public final class HeaderElements {

    private HeaderElements() {
    }

    public static final String CHUNKED_ENCODING = "chunked";
    public static final String CLOSE = "close";
    public static final String KEEP_ALIVE = "keep-alive";
    public static final String UPGRADE = "upgrade";
    public static final String CONTINUE = "100-continue";

}
