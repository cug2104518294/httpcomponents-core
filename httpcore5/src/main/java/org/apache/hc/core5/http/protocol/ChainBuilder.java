package org.apache.hc.core5.http.protocol;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Builder class to build a linked list (chain) of unique class instances.
 * Each class can have only one instance in the list.
 * Useful for building lists of protocol interceptors.
 * <p>
 * 构建链式关系网
 *
 * @see DefaultHttpProcessor
 * @since 4.3
 */
final class ChainBuilder<E> {

    private final LinkedList<E> list;
    private final Map<Class<?>, E> uniqueClasses;

    public ChainBuilder() {
        this.list = new LinkedList<>();
        this.uniqueClasses = new HashMap<>();
    }

    private void ensureUnique(final E e) {
        final E previous = this.uniqueClasses.remove(e.getClass());
        if (previous != null) {
            this.list.remove(previous);
        }
        this.uniqueClasses.put(e.getClass(), e);
    }

    public ChainBuilder<E> addFirst(final E e) {
        if (e == null) {
            return this;
        }
        ensureUnique(e);
        this.list.addFirst(e);
        return this;
    }

    public ChainBuilder<E> addLast(final E e) {
        if (e == null) {
            return this;
        }
        ensureUnique(e);
        this.list.addLast(e);
        return this;
    }

    public ChainBuilder<E> addAllFirst(final Collection<E> c) {
        if (c == null) {
            return this;
        }
        for (final E e : c) {
            addFirst(e);
        }
        return this;
    }

    @SafeVarargs
    public final ChainBuilder<E> addAllFirst(final E... c) {
        if (c == null) {
            return this;
        }
        for (final E e : c) {
            addFirst(e);
        }
        return this;
    }

    public ChainBuilder<E> addAllLast(final Collection<E> c) {
        if (c == null) {
            return this;
        }
        for (final E e : c) {
            addLast(e);
        }
        return this;
    }

    @SafeVarargs
    public final ChainBuilder<E> addAllLast(final E... c) {
        if (c == null) {
            return this;
        }
        for (final E e : c) {
            addLast(e);
        }
        return this;
    }

    public LinkedList<E> build() {
        return new LinkedList<>(this.list);
    }

}
