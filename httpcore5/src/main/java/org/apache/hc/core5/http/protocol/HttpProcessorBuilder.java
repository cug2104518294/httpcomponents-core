package org.apache.hc.core5.http.protocol;

import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.HttpResponseInterceptor;

/**
 * Builder for {@link HttpProcessor} instances.
 *
 * @since 4.3
 */
public class HttpProcessorBuilder {

    private ChainBuilder<HttpRequestInterceptor> requestChainBuilder;
    private ChainBuilder<HttpResponseInterceptor> responseChainBuilder;

    public static HttpProcessorBuilder create() {
        return new HttpProcessorBuilder();
    }

    HttpProcessorBuilder() {
        //默认的父类就是object
        super();
    }

    private ChainBuilder<HttpRequestInterceptor> getRequestChainBuilder() {
        if (requestChainBuilder == null) {
            requestChainBuilder = new ChainBuilder<>();
        }
        return requestChainBuilder;
    }

    private ChainBuilder<HttpResponseInterceptor> getResponseChainBuilder() {
        if (responseChainBuilder == null) {
            responseChainBuilder = new ChainBuilder<>();
        }
        return responseChainBuilder;
    }

    public HttpProcessorBuilder addFirst(final HttpRequestInterceptor e) {
        if (e == null) {
            return this;
        }
        getRequestChainBuilder().addFirst(e);
        return this;
    }

    public HttpProcessorBuilder addLast(final HttpRequestInterceptor e) {
        if (e == null) {
            return this;
        }
        getRequestChainBuilder().addLast(e);
        return this;
    }

    public HttpProcessorBuilder add(final HttpRequestInterceptor e) {
        return addLast(e);
    }

    public HttpProcessorBuilder addAllFirst(final HttpRequestInterceptor... e) {
        if (e == null) {
            return this;
        }
        getRequestChainBuilder().addAllFirst(e);
        return this;
    }

    public HttpProcessorBuilder addAllLast(final HttpRequestInterceptor... e) {
        if (e == null) {
            return this;
        }
        getRequestChainBuilder().addAllLast(e);
        return this;
    }

    public HttpProcessorBuilder addAll(final HttpRequestInterceptor... e) {
        return addAllLast(e);
    }

    public HttpProcessorBuilder addFirst(final HttpResponseInterceptor e) {
        if (e == null) {
            return this;
        }
        getResponseChainBuilder().addFirst(e);
        return this;
    }

    public HttpProcessorBuilder addLast(final HttpResponseInterceptor e) {
        if (e == null) {
            return this;
        }
        getResponseChainBuilder().addLast(e);
        return this;
    }

    public HttpProcessorBuilder add(final HttpResponseInterceptor e) {
        return addLast(e);
    }

    public HttpProcessorBuilder addAllFirst(final HttpResponseInterceptor... e) {
        if (e == null) {
            return this;
        }
        getResponseChainBuilder().addAllFirst(e);
        return this;
    }

    public HttpProcessorBuilder addAllLast(final HttpResponseInterceptor... e) {
        if (e == null) {
            return this;
        }
        getResponseChainBuilder().addAllLast(e);
        return this;
    }

    public HttpProcessorBuilder addAll(final HttpResponseInterceptor... e) {
        return addAllLast(e);
    }

    public HttpProcessor build() {
        return new DefaultHttpProcessor(
                requestChainBuilder != null ? requestChainBuilder.build() : null,
                responseChainBuilder != null ? responseChainBuilder.build() : null);
    }

}
