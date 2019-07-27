package org.apache.hc.core5.http2.hpack;

import org.apache.hc.core5.http.Header;

/**
 * Internal HPack header representation that also contains binary length of
 * header name and header value.
 */
final class HPackHeader implements Header {

    static private final int ENTRY_SIZE_OVERHEAD = 32;

    private final String name;
    private final int nameLen;
    private final String value;
    private final int valueLen;
    private final boolean sensitive;

    HPackHeader(final String name, final int nameLen, final String value, final int valueLen, final boolean sensitive) {
        this.name = name;
        this.nameLen = nameLen;
        this.value = value;
        this.valueLen = valueLen;
        this.sensitive = sensitive;
    }

    HPackHeader(final String name, final String value, final boolean sensitive) {
        this(name, name.length(), value, value.length(), sensitive);
    }

    HPackHeader(final String name, final String value) {
        this(name, value, false);
    }

    HPackHeader(final Header header) {
        this(header.getName(), header.getValue(), header.isSensitive());
    }

    @Override
    public String getName() {
        return name;
    }

    public int getNameLen() {
        return nameLen;
    }

    @Override
    public String getValue() {
        return value;
    }

    public int getValueLen() {
        return valueLen;
    }

    @Override
    public boolean isSensitive() {
        return sensitive;
    }

    public int getTotalSize() {
        return nameLen + valueLen + ENTRY_SIZE_OVERHEAD;
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.name).append(": ");
        if (this.value != null) {
            buf.append(this.value);
        }
        return buf.toString();
    }

}
