package org.apache.hc.core5.pool;

/**
 * Interface to obtain connection pool statistics.
 *
 * @param <T> the route type that represents the opposite endpoint of a pooled
 *            connection.
 * @since 4.2
 */
public interface ConnPoolStats<T> {

    PoolStats getTotalStats();

    PoolStats getStats(final T route);

}
