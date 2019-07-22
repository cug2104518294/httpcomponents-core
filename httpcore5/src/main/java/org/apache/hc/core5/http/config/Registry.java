package org.apache.hc.core5.http.config;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Generic registry of items keyed by low-case string ID.
 *
 * @since 4.3
 */
@Contract(threading = ThreadingBehavior.SAFE)
public final class Registry<I> implements Lookup<I> {

    private final Map<String, I> map;

    Registry(final Map<String, I> map) {
        super();
        this.map = new ConcurrentHashMap<>(map);
    }

    @Override
    public I lookup(final String key) {
        if (key == null) {
            return null;
        }
        return map.get(key.toLowerCase(Locale.ROOT));
    }

    @Override
    public String toString() {
        return map.toString();
    }

}
