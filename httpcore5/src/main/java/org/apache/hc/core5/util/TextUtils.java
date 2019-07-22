package org.apache.hc.core5.util;

/**
 * @since 4.3
 */
public final class TextUtils {

    private TextUtils() {
        // Do not allow utility class to be instantiated.
    }

    /**
     * Returns true if the parameter is null or of zero length
     */
    public static boolean isEmpty(final CharSequence s) {
        if (s == null) {
            return true;
        }
        return s.length() == 0;
    }

    /**
     * Returns true if the parameter is null or contains only whitespace
     */
    public static boolean isBlank(final CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @since 4.4
     */
    public static boolean containsBlanks(final CharSequence s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String toHexString(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            final byte b = bytes[i];
            if (b < 16) {
                buffer.append('0');
            }
            buffer.append(Integer.toHexString(b & 0xff));
        }
        return buffer.toString();
    }

}
