package org.apache.hc.core5.http.message;

import org.apache.hc.core5.http.HttpResponse;

import java.util.Locale;

/**
 * {@link HttpResponse} wrapper.
 */
public class HttpResponseWrapper extends AbstractMessageWrapper implements HttpResponse {

    private final HttpResponse message;

    public HttpResponseWrapper(final HttpResponse message) {
        super(message);
        this.message = message;
    }

    @Override
    public int getCode() {
        return message.getCode();
    }

    @Override
    public void setCode(final int code) {
        message.setCode(code);
    }

    @Override
    public String getReasonPhrase() {
        return message.getReasonPhrase();
    }

    @Override
    public void setReasonPhrase(final String reason) {
        message.setReasonPhrase(reason);
    }

    @Override
    public Locale getLocale() {
        return message.getLocale();
    }

    @Override
    public void setLocale(final Locale loc) {
        message.setLocale(loc);
    }

}
