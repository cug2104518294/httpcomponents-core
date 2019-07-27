package org.apache.hc.core5.http2.impl.nio.bootstrap;

import org.apache.hc.core5.concurrent.Cancellable;
import org.apache.hc.core5.concurrent.CancellableDependency;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

final class CancellableExecution implements CancellableDependency {

    private final AtomicBoolean cancelled;
    private final AtomicReference<Cancellable> dependencyRef;

    CancellableExecution() {
        this.cancelled = new AtomicBoolean(false);
        this.dependencyRef = new AtomicReference<>(null);
    }

    @Override
    public void setDependency(final Cancellable cancellable) {
        dependencyRef.set(cancellable);
        if (cancelled.get()) {
            final Cancellable dependency = dependencyRef.getAndSet(null);
            if (dependency != null) {
                dependency.cancel();
            }
        }
    }

    @Override
    public boolean isCancelled() {
        return cancelled.get();
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
