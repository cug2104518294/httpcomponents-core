package org.apache.hc.core5.concurrent;

import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.TimeoutValueException;

import java.util.concurrent.*;

/**
 * Basic implementation of the {@link Future} interface. {@code BasicFuture}
 * can be put into a completed state by invoking any of the following methods:
 * {@link #cancel()}, {@link #failed(Exception)}, or {@link #completed(Object)}.
 *
 * @param <T> the future result type of an asynchronous operation.
 * @since 4.2
 */
public class BasicFuture<T> implements Future<T>, Cancellable {

    private final FutureCallback<T> callback;

    private volatile boolean completed;
    private volatile boolean cancelled;
    private volatile T result;
    private volatile Exception ex;

    public BasicFuture(final FutureCallback<T> callback) {
        super();
        this.callback = callback;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public boolean isDone() {
        return this.completed;
    }

    private T getResult() throws ExecutionException {
        if (this.ex != null) {
            throw new ExecutionException(this.ex);
        }
        if (cancelled) {
            throw new CancellationException();
        }
        return this.result;
    }

    @Override
    public synchronized T get() throws InterruptedException, ExecutionException {
        while (!this.completed) {
            wait();
        }
        return getResult();
    }

    @Override
    public synchronized T get(final long timeout, final TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        Args.notNull(unit, "Time unit");
        final long msecs = unit.toMillis(timeout);
        final long startTime = (msecs <= 0) ? 0 : System.currentTimeMillis();
        long waitTime = msecs;
        if (this.completed) {
            return getResult();
        } else if (waitTime <= 0) {
            throw TimeoutValueException.fromMillis(msecs, msecs + Math.abs(waitTime));
        } else {
            for (; ; ) {
                wait(waitTime);
                if (this.completed) {
                    return getResult();
                }
                waitTime = msecs - (System.currentTimeMillis() - startTime);
                if (waitTime <= 0) {
                    throw TimeoutValueException.fromMillis(msecs, msecs + Math.abs(waitTime));
                }
            }
        }
    }

    public boolean completed(final T result) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.result = result;
            notifyAll();
        }
        if (this.callback != null) {
            this.callback.completed(result);
        }
        return true;
    }

    public boolean failed(final Exception exception) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.ex = exception;
            notifyAll();
        }
        if (this.callback != null) {
            this.callback.failed(exception);
        }
        return true;
    }

    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.cancelled = true;
            notifyAll();
        }
        if (this.callback != null) {
            this.callback.cancelled();
        }
        return true;
    }

    @Override
    public boolean cancel() {
        return cancel(true);
    }

}
