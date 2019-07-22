package org.apache.hc.core5.concurrent;

/**
 * This interface represents {@link Cancellable} object dependent on another
 * ongoing process or operation.
 *
 * @since 5.0
 */
public interface CancellableDependency extends Cancellable {

    /**
     * Sets {@link Cancellable} dependency on another ongoing process or
     * operation represented by {@link Cancellable}.
     */
    void setDependency(Cancellable cancellable);

    /**
     * Determines whether the process or operation has been cancelled.
     *
     * @return cancelled flag.
     */
    boolean isCancelled();

}
