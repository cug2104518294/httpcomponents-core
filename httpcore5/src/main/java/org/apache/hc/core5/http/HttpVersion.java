package org.apache.hc.core5.http;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;

/**
 * Represents an HTTP version. HTTP uses a "major.minor" numbering
 * scheme to indicate versions of the protocol.
 * <p>
 * The version of an HTTP message is indicated by an HTTP-Version field
 * in the first line of the message.
 * </p>
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public final class HttpVersion extends ProtocolVersion {

    private static final long serialVersionUID = -5856653513894415344L;

    /**
     * The protocol name.
     */
    public static final String HTTP = "HTTP";

    /**
     * HTTP protocol version 0.9
     */
    public static final HttpVersion HTTP_0_9 = new HttpVersion(0, 9);

    /**
     * HTTP protocol version 1.0
     */
    public static final HttpVersion HTTP_1_0 = new HttpVersion(1, 0);

    /**
     * HTTP protocol version 1.1
     */
    public static final HttpVersion HTTP_1_1 = new HttpVersion(1, 1);

    /**
     * HTTP protocol version 2.0
     */
    public static final HttpVersion HTTP_2_0 = new HttpVersion(2, 0);
    public static final HttpVersion HTTP_2 = HTTP_2_0;

    /**
     * HTTP/1.1 is default
     */
    public static final HttpVersion DEFAULT = HTTP_1_1;

    /**
     * All HTTP versions known to HttpCore.
     */
    public static final HttpVersion[] ALL = {HTTP_0_9, HTTP_1_0, HTTP_1_1, HTTP_2_0};

    /**
     * Gets a specific instance or creates a new one.
     *
     * @param major the major version
     * @param minor the minor version
     * @return an instance of {@link HttpVersion} with the argument version, never null.
     * @throws IllegalArgumentException if either major or minor version number is negative
     * @since 5.0
     */
    public static HttpVersion get(final int major, final int minor) {
        for (int i = 0; i < ALL.length; i++) {
            if (ALL[i].equals(major, minor)) {
                return ALL[i];
            }
        }
        // argument checking is done in the constructor
        return new HttpVersion(major, minor);
    }

    /**
     * Creates an HTTP protocol version designator.
     *
     * @param major the major version number of the HTTP protocol
     * @param minor the minor version number of the HTTP protocol
     * @throws IllegalArgumentException if either major or minor version number is negative
     */
    public HttpVersion(final int major, final int minor) {
        super(HTTP, major, minor);
    }

}
