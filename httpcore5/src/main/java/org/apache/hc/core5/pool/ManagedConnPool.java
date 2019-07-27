
package org.apache.hc.core5.pool;

import org.apache.hc.core5.io.ModalCloseable;

/**
 * {@link ConnPool} that also implements {@link ConnPoolControl} and {@link AutoCloseable}.
 *
 * @param <T> the route type that represents the opposite endpoint of a pooled
 *   connection.
 * @param <C> the type of pooled connections.
 * @since 4.2
 */
public interface ManagedConnPool<T, C extends ModalCloseable> extends ConnPool<T, C>, ConnPoolControl<T>, ModalCloseable {

}
