package org.apache.hc.core5.net;

import org.apache.hc.core5.util.Args;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * A collection of utilities relating to InetAddresses.
 *
 * @since 4.0
 */
public class InetAddressUtils {

    private InetAddressUtils() {
    }

    private static final String IPV4_BASIC_PATTERN_STRING =
            "(([1-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){1}" + // initial first field, 1-255
                    "(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){2}" + // following 2 fields, 0-255 followed by .
                    "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])"; // final field, 0-255

    private static final Pattern IPV4_PATTERN =
            Pattern.compile("^" + IPV4_BASIC_PATTERN_STRING + "$");

    private static final Pattern IPV4_MAPPED_IPV6_PATTERN = // TODO does not allow for redundant leading zeros
            Pattern.compile("^::[fF]{4}:" + IPV4_BASIC_PATTERN_STRING + "$");

    private static final Pattern IPV6_STD_PATTERN =
            Pattern.compile(
                    "^[0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){7}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN =
            Pattern.compile(
                    "^(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)" + // 0-6 hex fields
                            "::" +
                            "(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)$"); // 0-6 hex fields

    /*
     *  The above pattern is not totally rigorous as it allows for more than 7 hex fields in total
     */
    private static final char COLON_CHAR = ':';

    // Must not have more than 7 colons (i.e. 8 fields)
    private static final int MAX_COLON_COUNT = 7;

    /**
     * Checks whether the parameter is a valid IPv4 address
     *
     * @param input the address string to check for validity
     * @return true if the input parameter is a valid IPv4 address
     */
    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv4MappedIPv64Address(final String input) {
        return IPV4_MAPPED_IPV6_PATTERN.matcher(input).matches();
    }

    /**
     * Checks whether the parameter is a valid standard (non-compressed) IPv6 address
     *
     * @param input the address string to check for validity
     * @return true if the input parameter is a valid standard (non-compressed) IPv6 address
     */
    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    /**
     * Checks whether the parameter is a valid compressed IPv6 address
     *
     * @param input the address string to check for validity
     * @return true if the input parameter is a valid compressed IPv6 address
     */
    public static boolean isIPv6HexCompressedAddress(final String input) {
        int colonCount = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == COLON_CHAR) {
                colonCount++;
            }
        }
        return colonCount <= MAX_COLON_COUNT && IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    /**
     * Checks whether the parameter is a valid IPv6 address (including compressed).
     *
     * @param input the address string to check for validity
     * @return true if the input parameter is a valid standard or compressed IPv6 address
     */
    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }

    /**
     * Formats {@link SocketAddress} as text.
     *
     * @since 5.0
     */
    public static void formatAddress(
            final StringBuilder buffer,
            final SocketAddress socketAddress) {
        Args.notNull(buffer, "buffer");
        if (socketAddress instanceof InetSocketAddress) {
            final InetSocketAddress socketaddr = (InetSocketAddress) socketAddress;
            final InetAddress inetaddr = socketaddr.getAddress();
            if (inetaddr != null) {
                buffer.append(inetaddr.getHostAddress()).append(':').append(socketaddr.getPort());
            } else {
                buffer.append(socketAddress);
            }
        } else {
            buffer.append(socketAddress);
        }
    }

    /**
     * Returns canonical name (fully qualified domain name) of the localhost.
     *
     * @since 5.0
     */
    public static String getCanonicalLocalHostName() {
        try {
            final InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getCanonicalHostName();
        } catch (final UnknownHostException ex) {
            return "localhost";
        }
    }

}
