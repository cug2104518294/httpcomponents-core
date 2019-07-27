package org.apache.hc.core5.pool;

import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.io.ModalCloseable;
import org.apache.hc.core5.util.Timeout;

import java.util.concurrent.Future;

/**
 * {@code ConnPool} represents a shared pool connections can be leased from
 * and released back to.
 *
 * @param <T> the route type that represents the opposite endpoint of a pooled
 *            connection.
 * @param <C> the type of pooled connections.
 * @since 4.2
 */
public interface ConnPool<T, C extends ModalCloseable> {

    /**
     * Attempts to lease a connection for the given route and with the given
     * state from the pool.
     *
     * @param route          route of the connection.
     * @param state          arbitrary object that represents a particular state
     *                       (usually a security principal or a unique token identifying
     *                       the user whose credentials have been used while establishing the connection).
     *                       May be {@code null}.
     * @param requestTimeout request timeout.
     * @param callback       operation completion callback.
     * @return future for a leased pool entry.
     */
    Future<PoolEntry<T, C>> lease(T route, Object state, Timeout requestTimeout, FutureCallback<PoolEntry<T, C>> callback);

    /**
     * Releases the pool entry back to the pool.
     *
     * @param entry    pool entry leased from the pool
     * @param reusable flag indicating whether or not the released connection
     *                 is in a consistent state and is safe for further use.
     */
    void release(PoolEntry<T, C> entry, boolean reusable);

}
