package org.apache.hc.core5.http.config;

import org.apache.hc.core5.util.Args;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Builder for {@link Registry} instances.
 *
 * @since 4.3
 */
public final class RegistryBuilder<I> {

    private final Map<String, I> items;

    public static <I> RegistryBuilder<I> create() {
        return new RegistryBuilder<>();
    }

    RegistryBuilder() {
        super();
        this.items = new HashMap<>();
    }

    public RegistryBuilder<I> register(final String id, final I item) {
        Args.notEmpty(id, "ID");
        Args.notNull(item, "Item");
        items.put(id.toLowerCase(Locale.ROOT), item);
        return this;
    }

    public Registry<I> build() {
        return new Registry<>(items);
    }

    @Override
    public String toString() {
        return items.toString();
    }

}
