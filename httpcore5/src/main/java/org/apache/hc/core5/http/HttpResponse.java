package org.apache.hc.core5.http;

import java.util.Locale;

/**
 * After receiving and interpreting a request message, a server responds
 * with an HTTP response message.
 */
public interface HttpResponse extends HttpMessage {

    /**
     * Obtains the code of this response message.
     *
     * @return the status code.
     */
    int getCode();

    /**
     * Updates status code of this response message.
     *
     * @param code the HTTP status code.
     * @see HttpStatus
     */
    void setCode(int code);

    /**
     * Obtains the reason phrase of this response if available.
     *
     * @return the reason phrase.
     */
    String getReasonPhrase();

    /**
     * Updates the status line of this response with a new reason phrase.
     *
     * @param reason the new reason phrase as a single-line string, or
     *               {@code null} to unset the reason phrase
     */
    void setReasonPhrase(String reason);

    /**
     * Obtains the locale of this response.
     * The locale is used to determine the reason phrase
     * for the {@link #setCode status code}.
     * It can be changed using {@link #setLocale setLocale}.
     *
     * @return the locale of this response, never {@code null}
     */
    Locale getLocale();

    /**
     * Changes the locale of this response.
     *
     * @param loc the new locale
     */
    void setLocale(Locale loc);

}
