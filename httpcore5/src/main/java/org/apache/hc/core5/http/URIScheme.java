package org.apache.hc.core5.http;

import org.apache.hc.core5.util.Args;

/**
 * Enumerates supported URI schemes.
 * <p>
 * 连接的方式
 */
public enum URIScheme {

    HTTP("http"), HTTPS("https");

    public final String id;

    URIScheme(final String id) {
        this.id = Args.notBlank(id, "id");
    }

    public String getId() {
        return id;
    }

    public boolean same(final String scheme) {
        return id.equalsIgnoreCase(scheme);
    }

    @Override
    public String toString() {
        return id;
    }

}
