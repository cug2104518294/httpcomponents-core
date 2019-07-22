package org.apache.hc.core5.concurrent;

/**
 * A {@code Cancellable} represents a process or an operation that can be
 * canceled.
 *
 * @since 4.2
 */
public interface Cancellable {

    /**
     * Cancels the ongoing operation or process.
     *
     * @return {@code true} if the operation or process has been cancelled as a result of
     * this method call or {@code false} if it has already been cancelled or not started.
     */
    boolean cancel();

}
