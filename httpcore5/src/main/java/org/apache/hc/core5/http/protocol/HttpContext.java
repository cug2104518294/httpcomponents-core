package org.apache.hc.core5.http.protocol;

import org.apache.hc.core5.http.ProtocolVersion;

/**
 * HttpContext represents execution state of an HTTP process. It is a structure
 * that can be used to map an attribute name to an attribute value.
 * <p>
 * The primary purpose of the HTTP context is to facilitate information sharing
 * among various  logically related components. HTTP context can be used
 * to store a processing state for one message or several consecutive messages.
 * Multiple logically related messages can participate in a logical session
 * if the same context is reused between consecutive messages.
 * </p>
 * <p>
 * IMPORTANT: Please note HTTP context implementation, even when thread safe,
 * may not be used concurrently by multiple threads, as the context may contain
 * thread unsafe attributes.
 * </p>
 *
 * @since 4.0
 */
public interface HttpContext {

    /**
     * The prefix reserved for use by HTTP components. "http."
     */
    public static final String RESERVED_PREFIX = "http.";

    /**
     * Returns protocol version used in this context.
     *
     * @since 5.0
     */
    ProtocolVersion getProtocolVersion();

    /**
     * Sets protocol version used in this context.
     *
     * @since 5.0
     */
    void setProtocolVersion(ProtocolVersion version);

    /**
     * Obtains attribute with the given name.
     *
     * @param id the attribute name.
     * @return attribute value, or {@code null} if not set.
     */
    Object getAttribute(String id);

    /**
     * Sets value of the attribute with the given name.
     *
     * @param id  the attribute name.
     * @param obj the attribute value.
     * @return the previous value associated with <code>id</code>, or
     * {@code null} if there was no mapping for <code>id</code>.
     */
    Object setAttribute(String id, Object obj);

    /**
     * Removes attribute with the given name from the context.
     *
     * @param id the attribute name.
     * @return attribute value, or {@code null} if not set.
     */
    Object removeAttribute(String id);

}
