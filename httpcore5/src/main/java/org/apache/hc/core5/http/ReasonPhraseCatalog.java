package org.apache.hc.core5.http;

import java.util.Locale;

/**
 * Interface for obtaining reason phrases for HTTP status codes.
 *
 * @since 4.0
 */
public interface ReasonPhraseCatalog {

    /**
     * Obtains the reason phrase for a status code.
     * The optional context allows for catalogs that detect
     * the language for the reason phrase.
     *
     * @param status the status code, in the range 100-599
     * @param loc    the preferred locale for the reason phrase
     * @return the reason phrase, or {@code null} if unknown
     */
    String getReason(int status, Locale loc);

}
