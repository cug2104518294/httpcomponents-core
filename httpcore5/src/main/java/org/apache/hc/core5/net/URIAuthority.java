package org.apache.hc.core5.net;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.LangUtils;
import org.apache.hc.core5.util.TextUtils;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Locale;

/**
 * Represents authority component of request {@link java.net.URI}.
 * 权限模块
 * @since 5.0
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public final class URIAuthority implements NamedEndpoint, Serializable {

    private static final long serialVersionUID = 1L;
    private final String userInfo;
    private final String hostname;
    private final int port;

    /**
     * @throws IllegalArgumentException If the port parameter is outside the specified range of valid port values, which is between 0 and
     *                                  65535, inclusive. {@code -1} indicates the scheme default port.
     */
    private URIAuthority(final String userInfo, final String hostname, final int port, final boolean internal) {
        super();
        this.userInfo = userInfo;
        this.hostname = hostname;
        this.port = Ports.checkWithDefault(port);
    }

    /**
     * @throws IllegalArgumentException If the port parameter is outside the specified range of valid port values, which is between 0 and
     *                                  65535, inclusive. {@code -1} indicates the scheme default port.
     */
    public URIAuthority(final String userInfo, final String hostname, final int port) {
        super();
        Args.containsNoBlanks(hostname, "Host name");
        if (userInfo != null) {
            Args.containsNoBlanks(userInfo, "User info");
        }
        this.userInfo = userInfo;
        this.hostname = hostname.toLowerCase(Locale.ROOT);
        this.port = Ports.checkWithDefault(port);
    }

    public URIAuthority(final String hostname, final int port) {
        this(null, hostname, port);
    }

    public URIAuthority(final NamedEndpoint namedEndpoint) {
        this(null, namedEndpoint.getHostName(), namedEndpoint.getPort());
    }

    /**
     * Creates {@code URIHost} instance from string.
     * Text may not contain any blanks.
     */
    public static URIAuthority create(final String s) throws URISyntaxException {
        if (s == null) {
            return null;
        }
        String userInfo = null;
        String hostname = s;
        int port = -1;
        final int portIdx = hostname.lastIndexOf(":");
        if (portIdx > 0) {
            try {
                port = Integer.parseInt(hostname.substring(portIdx + 1));
            } catch (final NumberFormatException ex) {
                throw new URISyntaxException(s, "invalid port");
            }
            hostname = hostname.substring(0, portIdx);
        }
        final int atIdx = hostname.lastIndexOf("@");
        if (atIdx > 0) {
            userInfo = hostname.substring(0, atIdx);
            if (TextUtils.containsBlanks(userInfo)) {
                throw new URISyntaxException(s, "user info contains blanks");
            }
            hostname = hostname.substring(atIdx + 1);
        }
        if (TextUtils.containsBlanks(hostname)) {
            throw new URISyntaxException(s, "hostname contains blanks");
        }
        return new URIAuthority(userInfo, hostname.toLowerCase(Locale.ROOT), port, true);
    }

    public URIAuthority(final String hostname) {
        this(null, hostname, -1);
    }

    public String getUserInfo() {
        return userInfo;
    }

    @Override
    public String getHostName() {
        return hostname;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        if (userInfo != null) {
            buffer.append(userInfo);
            buffer.append("@");
        }
        buffer.append(hostname);
        if (port != -1) {
            buffer.append(":");
            buffer.append(Integer.toString(port));
        }
        return buffer.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof URIAuthority) {
            final URIAuthority that = (URIAuthority) obj;
            return LangUtils.equals(this.userInfo, that.userInfo) &&
                    LangUtils.equals(this.hostname, that.hostname) &&
                    this.port == that.port;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        hash = LangUtils.hashCode(hash, userInfo);
        hash = LangUtils.hashCode(hash, hostname);
        hash = LangUtils.hashCode(hash, port);
        return hash;
    }

}
