package org.apache.hc.core5.pool;

import org.apache.hc.core5.util.TimeValue;

import java.util.Set;

/**
 * Interface to control runtime properties of a {@link ConnPool} such as
 * maximum total number of connections or maximum connections per route
 * allowed.
 *
 * @param <T> the route type that represents the opposite endpoint of a pooled
 *            connection.
 * @since 4.2
 */
public interface ConnPoolControl<T> extends ConnPoolStats<T> {

    void setMaxTotal(int max);

    int getMaxTotal();

    void setDefaultMaxPerRoute(int max);

    int getDefaultMaxPerRoute();

    void setMaxPerRoute(final T route, int max);

    int getMaxPerRoute(final T route);

    void closeIdle(TimeValue idleTime);

    void closeExpired();

    Set<T> getRoutes();

}
