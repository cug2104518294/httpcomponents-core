package org.apache.hc.core5.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Default {@link ThreadFactory} implementation.
 *
 * @since 5.0
 */
public class DefaultThreadFactory implements ThreadFactory {

    private final String namePrefix;
    private final ThreadGroup group;
    private final AtomicLong count;
    private final boolean daemon;

    public DefaultThreadFactory(final String namePrefix, final ThreadGroup group, final boolean daemon) {
        this.namePrefix = namePrefix;
        this.group = group;
        this.daemon = daemon;
        this.count = new AtomicLong();
    }

    public DefaultThreadFactory(final String namePrefix, final boolean daemon) {
        this(namePrefix, null, daemon);
    }

    public DefaultThreadFactory(final String namePrefix) {
        this(namePrefix, null, false);
    }

    @Override
    public Thread newThread(final Runnable target) {
        final Thread thread = new Thread(this.group, target, this.namePrefix + "-" + this.count.incrementAndGet());
        thread.setDaemon(daemon);
        return thread;
    }

}
