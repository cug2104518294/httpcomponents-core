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
 * Component that holds all details needed to describe a network connection
 * to a host. This includes remote host name and port.
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public final class Host implements NamedEndpoint, Serializable {

    private static final long serialVersionUID = 1L;
    private final String name;
    private final String lcName;
    private final int port;

    public Host(final String name, final int port) {
        super();
        this.name = Args.containsNoBlanks(name, "Host name");
        this.port = Ports.check(port);
        this.lcName = this.name.toLowerCase(Locale.ROOT);
    }

    public static Host create(final String s) throws URISyntaxException {
        Args.notEmpty(s, "HTTP Host");
        final int portIdx = s.lastIndexOf(":");
        final int port;
        if (portIdx > 0) {
            try {
                port = Integer.parseInt(s.substring(portIdx + 1));
            } catch (final NumberFormatException ex) {
                throw new URISyntaxException(s, "invalid port");
            }
            final String hostname = s.substring(0, portIdx);
            if (TextUtils.containsBlanks(hostname)) {
                throw new URISyntaxException(s, "hostname contains blanks");
            }
            return new Host(hostname, port);
        }
        throw new URISyntaxException(s, "port not found");
    }

    @Override
    public String getHostName() {
        return name;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Host) {
            final Host that = (Host) o;
            return this.lcName.equals(that.lcName) && this.port == that.port;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        hash = LangUtils.hashCode(hash, this.lcName);
        hash = LangUtils.hashCode(hash, this.port);
        return hash;
    }

    @Override
    public String toString() {
        return name + ":" + port;
    }

}
