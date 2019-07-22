package org.apache.hc.core5.pool;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;

import java.io.Serializable;

/**
 * Pool statistics.
 * <p>
 * The total number of connections in the pool is equal to {@code available} plus {@code leased}.
 * </p>
 *
 * @since 4.2
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class PoolStats implements Serializable {

    private static final long serialVersionUID = -2807686144795228544L;

    private final int leased;
    private final int pending;
    private final int available;
    private final int max;

    public PoolStats(final int leased, final int pending, final int free, final int max) {
        super();
        this.leased = leased;
        this.pending = pending;
        this.available = free;
        this.max = max;
    }

    /**
     * Gets the number of persistent connections tracked by the connection manager currently being used to execute
     * requests.
     * <p>
     * The total number of connections in the pool is equal to {@code available} plus {@code leased}.
     * </p>
     *
     * @return the number of persistent connections.
     */
    public int getLeased() {
        return this.leased;
    }

    /**
     * Gets the number of connection requests being blocked awaiting a free connection. This can happen only if there
     * are more worker threads contending for fewer connections.
     *
     * @return the number of connection requests being blocked awaiting a free connection.
     */
    public int getPending() {
        return this.pending;
    }

    /**
     * Gets the number idle persistent connections.
     * <p>
     * The total number of connections in the pool is equal to {@code available} plus {@code leased}.
     * </p>
     *
     * @return number idle persistent connections.
     */
    public int getAvailable() {
        return this.available;
    }

    /**
     * Gets the maximum number of allowed persistent connections.
     *
     * @return the maximum number of allowed persistent connections.
     */
    public int getMax() {
        return this.max;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("[leased: ");
        buffer.append(this.leased);
        buffer.append("; pending: ");
        buffer.append(this.pending);
        buffer.append("; available: ");
        buffer.append(this.available);
        buffer.append("; max: ");
        buffer.append(this.max);
        buffer.append("]");
        return buffer.toString();
    }

}
