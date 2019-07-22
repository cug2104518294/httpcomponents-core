package org.apache.hc.core5.http.io;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Classic I/O network socket configuration.
 *
 * @since 4.3
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class SocketConfig {

    public static final SocketConfig DEFAULT = new Builder().build();

    private final Timeout soTimeout;
    private final boolean soReuseAddress;
    private final TimeValue soLinger;
    private final boolean soKeepAlive;
    private final boolean tcpNoDelay;
    private final int sndBufSize;
    private final int rcvBufSize;
    private final int backlogSize;
    private final SocketAddress socksProxyAddress;

    SocketConfig(
            final Timeout soTimeout,
            final boolean soReuseAddress,
            final TimeValue soLinger,
            final boolean soKeepAlive,
            final boolean tcpNoDelay,
            final int sndBufSize,
            final int rcvBufSize,
            final int backlogSize,
            final SocketAddress socksProxyAddress) {
        super();
        this.soTimeout = soTimeout;
        this.soReuseAddress = soReuseAddress;
        this.soLinger = soLinger;
        this.soKeepAlive = soKeepAlive;
        this.tcpNoDelay = tcpNoDelay;
        this.sndBufSize = sndBufSize;
        this.rcvBufSize = rcvBufSize;
        this.backlogSize = backlogSize;
        this.socksProxyAddress = socksProxyAddress;
    }

    /**
     * Determines the default socket timeout value for blocking I/O operations.
     * <p>
     * Default: {@code 0} (no timeout)
     * </p>
     *
     * @return the default socket timeout value for blocking I/O operations.
     * @see java.net.SocketOptions#SO_TIMEOUT
     */
    public Timeout getSoTimeout() {
        return soTimeout;
    }

    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_REUSEADDR} parameter
     * for newly created sockets.
     * <p>
     * Default: {@code false}
     * </p>
     *
     * @return the default value of the {@link java.net.SocketOptions#SO_REUSEADDR} parameter.
     * @see java.net.SocketOptions#SO_REUSEADDR
     */
    public boolean isSoReuseAddress() {
        return soReuseAddress;
    }

    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_LINGER} parameter
     * for newly created sockets.
     * <p>
     * Default: {@code -1}
     * </p>
     *
     * @return the default value of the {@link java.net.SocketOptions#SO_LINGER} parameter.
     * @see java.net.SocketOptions#SO_LINGER
     */
    public TimeValue getSoLinger() {
        return soLinger;
    }

    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_KEEPALIVE} parameter
     * for newly created sockets.
     * <p>
     * Default: {@code false}
     * </p>
     *
     * @return the default value of the {@link java.net.SocketOptions#SO_KEEPALIVE} parameter.
     * @see java.net.SocketOptions#SO_KEEPALIVE
     */
    public boolean isSoKeepAlive() {
        return soKeepAlive;
    }

    /**
     * Determines the default value of the {@link java.net.SocketOptions#TCP_NODELAY} parameter
     * for newly created sockets.
     * <p>
     * Default: {@code false}
     * </p>
     *
     * @return the default value of the {@link java.net.SocketOptions#TCP_NODELAY} parameter.
     * @see java.net.SocketOptions#TCP_NODELAY
     */
    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_SNDBUF} parameter
     * for newly created sockets.
     * <p>
     * Default: {@code 0} (system default)
     * </p>
     *
     * @return the default value of the {@link java.net.SocketOptions#SO_SNDBUF} parameter.
     * @see java.net.SocketOptions#SO_SNDBUF
     * @since 4.4
     */
    public int getSndBufSize() {
        return sndBufSize;
    }

    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_RCVBUF} parameter
     * for newly created sockets.
     * <p>
     * Default: {@code 0} (system default)
     * </p>
     *
     * @return the default value of the {@link java.net.SocketOptions#SO_RCVBUF} parameter.
     * @see java.net.SocketOptions#SO_RCVBUF
     * @since 4.4
     */
    public int getRcvBufSize() {
        return rcvBufSize;
    }

    /**
     * Determines the maximum queue length for incoming connection indications
     * (a request to connect) also known as server socket backlog.
     * <p>
     * Default: {@code 0} (system default)
     * </p>
     *
     * @return the maximum queue length for incoming connection indications
     * @since 4.4
     */
    public int getBacklogSize() {
        return backlogSize;
    }

    /**
     * The address of the SOCKS proxy to use.
     */
    public SocketAddress getSocksProxyAddress() {
        return this.socksProxyAddress;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[soTimeout=").append(this.soTimeout)
                .append(", soReuseAddress=").append(this.soReuseAddress)
                .append(", soLinger=").append(this.soLinger)
                .append(", soKeepAlive=").append(this.soKeepAlive)
                .append(", tcpNoDelay=").append(this.tcpNoDelay)
                .append(", sndBufSize=").append(this.sndBufSize)
                .append(", rcvBufSize=").append(this.rcvBufSize)
                .append(", backlogSize=").append(this.backlogSize)
                .append(", socksProxyAddress=").append(this.socksProxyAddress)
                .append("]");
        return builder.toString();
    }

    public static SocketConfig.Builder custom() {
        return new Builder();
    }

    public static SocketConfig.Builder copy(final SocketConfig config) {
        Args.notNull(config, "Socket config");
        return new Builder()
                .setSoTimeout(config.getSoTimeout())
                .setSoReuseAddress(config.isSoReuseAddress())
                .setSoLinger(config.getSoLinger())
                .setSoKeepAlive(config.isSoKeepAlive())
                .setTcpNoDelay(config.isTcpNoDelay())
                .setSndBufSize(config.getSndBufSize())
                .setRcvBufSize(config.getRcvBufSize())
                .setBacklogSize(config.getBacklogSize())
                .setSocksProxyAddress(config.getSocksProxyAddress());
    }

    public static class Builder {

        private Timeout soTimeout;
        private boolean soReuseAddress;
        private TimeValue soLinger;
        private boolean soKeepAlive;
        private boolean tcpNoDelay;
        private int sndBufSize;
        private int rcvBufSize;
        private int backlogSize;
        private SocketAddress socksProxyAddress;

        Builder() {
            this.soTimeout = Timeout.ZERO_MILLISECONDS;
            this.soReuseAddress = false;
            this.soLinger = TimeValue.NEG_ONE_SECONDS;
            this.soKeepAlive = false;
            this.tcpNoDelay = true;
            this.sndBufSize = 0;
            this.rcvBufSize = 0;
            this.backlogSize = 0;
            this.socksProxyAddress = null;
        }

        public Builder setSoTimeout(final int soTimeout, final TimeUnit timeUnit) {
            this.soTimeout = Timeout.of(soTimeout, timeUnit);
            return this;
        }

        public Builder setSoTimeout(final Timeout soTimeout) {
            this.soTimeout = soTimeout;
            return this;
        }

        public Builder setSoReuseAddress(final boolean soReuseAddress) {
            this.soReuseAddress = soReuseAddress;
            return this;
        }

        public Builder setSoLinger(final int soLinger, final TimeUnit timeUnit) {
            this.soLinger = Timeout.of(soLinger, timeUnit);
            return this;
        }

        public Builder setSoLinger(final TimeValue soLinger) {
            this.soLinger = soLinger;
            return this;
        }

        public Builder setSoKeepAlive(final boolean soKeepAlive) {
            this.soKeepAlive = soKeepAlive;
            return this;
        }

        public Builder setTcpNoDelay(final boolean tcpNoDelay) {
            this.tcpNoDelay = tcpNoDelay;
            return this;
        }

        /**
         * @since 4.4
         */
        public Builder setSndBufSize(final int sndBufSize) {
            this.sndBufSize = sndBufSize;
            return this;
        }

        /**
         * @since 4.4
         */
        public Builder setRcvBufSize(final int rcvBufSize) {
            this.rcvBufSize = rcvBufSize;
            return this;
        }

        /**
         * @since 4.4
         */
        public Builder setBacklogSize(final int backlogSize) {
            this.backlogSize = backlogSize;
            return this;
        }

        public Builder setSocksProxyAddress(final SocketAddress socksProxyAddress) {
            this.socksProxyAddress = socksProxyAddress;
            return this;
        }

        public SocketConfig build() {
            return new SocketConfig(
                    Timeout.defaultsToDisabled(soTimeout),
                    soReuseAddress,
                    soLinger != null ? soLinger : TimeValue.NEG_ONE_SECONDS,
                    soKeepAlive, tcpNoDelay, sndBufSize, rcvBufSize, backlogSize,
                    socksProxyAddress);
        }

    }

}
