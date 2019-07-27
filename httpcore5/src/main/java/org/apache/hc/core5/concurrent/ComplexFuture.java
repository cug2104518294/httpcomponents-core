package org.apache.hc.core5.concurrent;

import org.apache.hc.core5.util.Args;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

/**
 * {@link Future} whose result depends on another {@link Cancellable} process
 * or operation or another {@link Future}. Dependent process will get cancelled
 * if the future itself is cancelled.
 *
 * @param <T> the future result type of an asynchronous operation.
 * @since 5.0
 */
public final class ComplexFuture<T> extends BasicFuture<T> implements CancellableDependency {

    private final AtomicReference<Cancellable> dependencyRef;

    public ComplexFuture(final FutureCallback<T> callback) {
        super(callback);
        this.dependencyRef = new AtomicReference<>(null);
    }

    @Override
    public void setDependency(final Cancellable dependency) {
        Args.notNull(dependency, "dependency");
        if (isDone()) {
            dependency.cancel();
        } else {
            dependencyRef.set(dependency);
        }
    }

    public void setDependency(final Future<?> dependency) {
        Args.notNull(dependency, "dependency");
        if (dependency instanceof Cancellable) {
            setDependency((Cancellable) dependency);
        } else {
            setDependency(new Cancellable() {

                @Override
                public boolean cancel() {
                    return dependency.cancel(true);
                }

            });
        }
    }

    @Override
    public boolean completed(final T result) {
        final boolean completed = super.completed(result);
        dependencyRef.set(null);
        return completed;
    }

    @Override
    public boolean failed(final Exception exception) {
        final boolean failed = super.failed(exception);
        dependencyRef.set(null);
        return failed;
    }

    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        final boolean cancelled = super.cancel(mayInterruptIfRunning);
        final Cancellable dependency = dependencyRef.getAndSet(null);
        if (dependency != null) {
            dependency.cancel();
        }
        return cancelled;
    }

}
