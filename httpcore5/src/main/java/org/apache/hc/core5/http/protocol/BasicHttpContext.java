package org.apache.hc.core5.http.protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.HttpVersion;
import org.apache.hc.core5.http.ProtocolVersion;
import org.apache.hc.core5.util.Args;

/**
 * Default implementation of {@link HttpContext}.
 * <p>
 * Please note instances of this class can be thread unsafe if the
 * parent context is not thread safe.
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.SAFE_CONDITIONAL)
public class BasicHttpContext implements HttpContext {

    private final HttpContext parentContext;
    private final Map<String, Object> map;

    private ProtocolVersion version;

    public BasicHttpContext() {
        this(null);
    }

    public BasicHttpContext(final HttpContext parentContext) {
        super();
        this.map = new ConcurrentHashMap<>();
        this.parentContext = parentContext;
    }

    @Override
    public Object getAttribute(final String id) {
        Args.notNull(id, "Id");
        Object obj = this.map.get(id);
        if (obj == null && this.parentContext != null) {
            obj = this.parentContext.getAttribute(id);
        }
        return obj;
    }

    @Override
    public Object setAttribute(final String id, final Object obj) {
        Args.notNull(id, "Id");
        if (obj != null) {
            return this.map.put(id, obj);
        }
        return this.map.remove(id);
    }

    @Override
    public Object removeAttribute(final String id) {
        Args.notNull(id, "Id");
        return this.map.remove(id);
    }

    /**
     * @since 5.0
     */
    @Override
    public ProtocolVersion getProtocolVersion() {
        return this.version != null ? this.version : HttpVersion.DEFAULT;
    }

    /**
     * @since 5.0
     */
    @Override
    public void setProtocolVersion(final ProtocolVersion version) {
        this.version = version;
    }

    /**
     * @since 4.2
     */
    public void clear() {
        this.map.clear();
    }

    @Override
    public String toString() {
        return this.map.toString();
    }

}
