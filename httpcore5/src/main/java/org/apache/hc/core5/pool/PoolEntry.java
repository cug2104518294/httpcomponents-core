package org.apache.hc.core5.pool;

import org.apache.hc.core5.function.Supplier;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.io.ModalCloseable;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.Deadline;
import org.apache.hc.core5.util.TimeValue;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Pool entry containing a pool connection object along with its route.
 * <p>
 * The connection assigned to this pool entry may have an expiration time and also have an object
 * representing a connection state (usually a security principal or a unique token identifying
 * the user whose credentials have been used while establishing the connection).
 *
 * @param <T> the route type that represents the opposite endpoint of a pooled
 *            connection.
 * @param <C> the connection type.
 * @since 4.2
 */
public final class PoolEntry<T, C extends ModalCloseable> {

    private final T route;
    private final TimeValue timeToLive;
    private final AtomicReference<C> connRef;
    private final Supplier<Long> currentTimeSupplier;

    private volatile Object state;
    private volatile long created;
    private volatile long updated;
    private volatile Deadline expiryDeadline = Deadline.MIN_VALUE;
    private volatile Deadline validityDeadline = Deadline.MIN_VALUE;

    PoolEntry(final T route, final TimeValue timeToLive, final Supplier<Long> currentTimeSupplier) {
        super();
        this.route = Args.notNull(route, "Route");
        this.timeToLive = TimeValue.defaultsToNegativeOneMillisecond(timeToLive);
        this.connRef = new AtomicReference<>(null);
        this.currentTimeSupplier = currentTimeSupplier;
    }

    long getCurrentTime() {
        return currentTimeSupplier != null ? currentTimeSupplier.get() : System.currentTimeMillis();
    }

    /**
     * Creates new {@code PoolEntry} instance.
     *
     * @param route      route to the opposite endpoint.
     * @param timeToLive maximum time to live. May be zero if the connection
     *                   does not have an expiry deadline.
     */
    public PoolEntry(final T route, final TimeValue timeToLive) {
        this(route, timeToLive, null);
    }

    public PoolEntry(final T route) {
        this(route, null);
    }

    public T getRoute() {
        return this.route;
    }

    public C getConnection() {
        return this.connRef.get();
    }

    /**
     * @since 5.0
     */
    public Deadline getValidityDeadline() {
        return this.validityDeadline;
    }

    public Object getState() {
        return this.state;
    }

    public long getUpdated() {
        return this.updated;
    }

    public Deadline getExpiryDeadline() {
        return this.expiryDeadline;
    }

    /**
     * @since 5.0
     */
    public boolean hasConnection() {
        return this.connRef.get() != null;
    }

    /**
     * @since 5.0
     */
    public void assignConnection(final C conn) {
        Args.notNull(conn, "connection");
        if (this.connRef.compareAndSet(null, conn)) {
            this.created = getCurrentTime();
            this.updated = this.created;
            this.validityDeadline = Deadline.calculate(this.created, this.timeToLive);
            this.expiryDeadline = this.validityDeadline;
            this.state = null;
        } else {
            throw new IllegalStateException("Connection already assigned");
        }
    }

    /**
     * @since 5.0
     */
    public void discardConnection(final CloseMode closeMode) {
        final C connection = this.connRef.getAndSet(null);
        if (connection != null) {
            this.state = null;
            this.created = 0;
            this.updated = 0;
            this.expiryDeadline = Deadline.MIN_VALUE;
            this.validityDeadline = Deadline.MIN_VALUE;
            connection.close(closeMode);
        }
    }

    /**
     * @since 5.0
     */
    public void updateExpiry(final TimeValue expiryTime) {
        Args.notNull(expiryTime, "Expiry time");
        final long currentTime = getCurrentTime();
        final Deadline newExpiry = Deadline.calculate(currentTime, expiryTime);
        this.expiryDeadline = newExpiry.min(this.validityDeadline);
        this.updated = currentTime;
    }

    /**
     * @since 5.0
     */
    public void updateState(final Object state) {
        this.state = state;
        this.updated = getCurrentTime();
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("[route:");
        buffer.append(this.route);
        buffer.append("][state:");
        buffer.append(this.state);
        buffer.append("]");
        return buffer.toString();
    }

}
