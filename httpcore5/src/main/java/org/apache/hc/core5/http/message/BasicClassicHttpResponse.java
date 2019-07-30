package org.apache.hc.core5.http.message;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ReasonPhraseCatalog;
import org.apache.hc.core5.io.Closer;

import java.io.IOException;
import java.util.Locale;

/**
 * Basic implementation of {@link ClassicHttpResponse}.
 *
 * @since 5.0
 */
public class BasicClassicHttpResponse extends BasicHttpResponse implements ClassicHttpResponse {

    private static final long serialVersionUID = 1L;
    private HttpEntity entity;

    /**
     * Creates a new response.
     *
     * @param code    the status code
     * @param catalog the reason phrase catalog, or
     *                {@code null} to disable automatic
     *                reason phrase lookup
     * @param locale  the locale for looking up reason phrases, or
     *                {@code null} for the system locale
     */
    public BasicClassicHttpResponse(final int code, final ReasonPhraseCatalog catalog, final Locale locale) {
        super(code, catalog, locale);
    }

    /**
     * Creates a new response.
     *
     * @param code         the status code of the response
     * @param reasonPhrase the reason phrase to the status code, or {@code null}
     */
    public BasicClassicHttpResponse(final int code, final String reasonPhrase) {
        super(code, reasonPhrase);
    }

    /**
     * Creates a new response.
     *
     * @param code the status code of the response
     */
    public BasicClassicHttpResponse(final int code) {
        super(code);
    }

    @Override
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override
    public void setEntity(final HttpEntity entity) {
        this.entity = entity;
    }

    @Override
    public void close() throws IOException {
        Closer.close(entity);
    }

}
