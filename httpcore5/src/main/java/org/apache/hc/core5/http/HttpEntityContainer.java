package org.apache.hc.core5.http;

/**
 * Contains an {@link HttpEntity}.
 *
 * @since 5.0
 */
public interface HttpEntityContainer {

    /**
     * Obtains the message entity, if available.
     *
     * @return  the message entity, or {@code null} if not available
     */
    HttpEntity getEntity();

    /**
     * Sets an entity for this message.
     * <p>
     * Please note that if an entity has already been set it is responsibility of the caller
     * to ensure release of the resources that may be associated with that entity.
     * </p>
     *
     * @param entity    the entity to set of this message, or {@code null} to unset
     */
    void setEntity(HttpEntity entity);

}
