package org.apache.hc.core5.http.message;

import java.io.Serializable;
import java.util.Objects;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.util.Args;

/**
 * Immutable {@link Header}.
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class BasicHeader implements Header, Cloneable, Serializable {

    private static final long serialVersionUID = -5427236326487562174L;

    private final String name;
    private final boolean sensitive;
    private final String value;

    /**
     * Default constructor
     *
     * @param name the header name
     * @param value the header value, taken as the value's {@link #toString()}.
     */
    public BasicHeader(final String name, final Object value) {
        this(name, value, false);
    }

    /**
     * Constructor with sensitivity flag
     *
     * @param name the header name
     * @param value the header value, taken as the value's {@link #toString()}.
     * @param sensitive sensitive flag
     *
     * @since 5.0
     */
    public BasicHeader(final String name, final Object value, final boolean sensitive) {
        super();
        this.name = Args.notNull(name, "Name");
        this.value = Objects.toString(value, null);
        this.sensitive = sensitive;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isSensitive() {
        return this.sensitive;
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.getName()).append(": ");
        if (this.getValue() != null) {
            buf.append(this.getValue());
        }
        return buf.toString();
    }

}
