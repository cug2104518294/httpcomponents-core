package org.apache.hc.core5.concurrent;

import org.apache.hc.core5.util.Args;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * {@link Cancellable} that has a dependency on another {@link Cancellable}
 * process or operation. Dependent process or operation will get cancelled
 * if this {@link Cancellable} itself is cancelled.
 *
 * @since 5.0
 */
public final class ComplexCancellable implements CancellableDependency {

    private final AtomicReference<Cancellable> dependencyRef;
    private final AtomicBoolean cancelled;

    public ComplexCancellable() {
        this.dependencyRef = new AtomicReference<>(null);
        this.cancelled = new AtomicBoolean(false);
    }

    @Override
    public boolean isCancelled() {
        return cancelled.get();
    }

    @Override
    public void setDependency(final Cancellable dependency) {
        Args.notNull(dependency, "dependency");
        if (!cancelled.get()) {
            dependencyRef.set(dependency);
        } else {
            dependency.cancel();
        }
    }

    @Override
    public boolean cancel() {
        if (cancelled.compareAndSet(false, true)) {
            final Cancellable dependency = dependencyRef.getAndSet(null);
            if (dependency != null) {
                dependency.cancel();
            }
            return true;
        }
        return false;
    }

}
