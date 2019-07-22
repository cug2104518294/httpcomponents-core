package org.apache.hc.core5.net;

import org.apache.hc.core5.util.Args;

/**
 * Port helper methods.
 */
public class Ports {

    /**
     * The scheme default port.
     */
    public final static int SCHEME_DEFAULT = -1;

    /**
     * The minimum port value per https://tools.ietf.org/html/rfc6335.
     */
    public final static int MIN_VALUE = 0;

    /**
     * The maxium port value per https://tools.ietf.org/html/rfc6335.
     */
    public final static int MAX_VALUE = 65535;

    /**
     * Checks a port number where {@code -1} indicates the scheme default port.
     *
     * @param port The port to check where {@code -1} indicates the scheme default port.
     * @return the port
     * @throws IllegalArgumentException If the port parameter is outside the specified range of valid port values, which is between 0 and
     *                                  65535, inclusive. {@code -1} indicates the scheme default port.
     */
    public static int checkWithDefault(final int port) {
        return Args.checkRange(port, SCHEME_DEFAULT, MAX_VALUE,
                "Port number(Use -1 to specify the scheme default port)");
    }

    /**
     * Checks a port number.
     *
     * @param port The port to check.
     * @return the port
     * @throws IllegalArgumentException If the port parameter is outside the specified range of valid port values, which is between 0 and
     *                                  65535, inclusive.
     */
    public static int check(final int port) {
        return Args.checkRange(port, MIN_VALUE, MAX_VALUE, "Port number");
    }

}
