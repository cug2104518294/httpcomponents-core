package org.apache.hc.core5.http;

import java.util.Set;

/**
 * Details of an entity transmitted by a message.
 *
 * @since 5.0
 */
public interface EntityDetails {

    /**
     * Returns length of the entity, if known.
     */
    long getContentLength();

    /**
     * Returns content type of the entity, if known.
     */
    String getContentType();

    /**
     * Returns content encoding of the entity, if known.
     */
    String getContentEncoding();

    /**
     * Returns chunked transfer hint for this entity.
     * <p>
     * The behavior of wrapping entities is implementation dependent,
     * but should respect the primary purpose.
     * </p>
     */
    boolean isChunked();

    /**
     * Preliminary declaration of trailing headers.
     */
    Set<String> getTrailerNames();

}
