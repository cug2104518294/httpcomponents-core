package org.apache.hc.core5.http.protocol;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Default immutable implementation of {@link HttpProcessor}.
 * <p>
 * 请求还是响应的请求的过滤器 放在数组中  然后循环执行
 *
 * @since 5.0
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public final class DefaultHttpProcessor implements HttpProcessor {

    private final HttpRequestInterceptor[] requestInterceptors;
    private final HttpResponseInterceptor[] responseInterceptors;

    public DefaultHttpProcessor(
            final HttpRequestInterceptor[] requestInterceptors,
            final HttpResponseInterceptor[] responseInterceptors) {
        super();
        if (requestInterceptors != null) {
            final int l = requestInterceptors.length;
            this.requestInterceptors = new HttpRequestInterceptor[l];
            System.arraycopy(requestInterceptors, 0, this.requestInterceptors, 0, l);
        } else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (responseInterceptors != null) {
            final int l = responseInterceptors.length;
            this.responseInterceptors = new HttpResponseInterceptor[l];
            System.arraycopy(responseInterceptors, 0, this.responseInterceptors, 0, l);
        } else {
            this.responseInterceptors = new HttpResponseInterceptor[0];
        }
    }

    /**
     * @since 4.3
     */
    public DefaultHttpProcessor(
            final List<HttpRequestInterceptor> requestInterceptors,
            final List<HttpResponseInterceptor> responseInterceptors) {
        super();
        if (requestInterceptors != null) {
            final int l = requestInterceptors.size();
            this.requestInterceptors = requestInterceptors.toArray(new HttpRequestInterceptor[l]);
        } else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (responseInterceptors != null) {
            final int l = responseInterceptors.size();
            this.responseInterceptors = responseInterceptors.toArray(new HttpResponseInterceptor[l]);
        } else {
            this.responseInterceptors = new HttpResponseInterceptor[0];
        }
    }

    public DefaultHttpProcessor(final HttpRequestInterceptor... requestInterceptors) {
        this(requestInterceptors, null);
    }

    public DefaultHttpProcessor(final HttpResponseInterceptor... responseInterceptors) {
        this(null, responseInterceptors);
    }

    @Override
    public void process(
            final HttpRequest request,
            final EntityDetails entity,
            final HttpContext context) throws IOException, HttpException {
        for (final HttpRequestInterceptor requestInterceptor : this.requestInterceptors) {
            requestInterceptor.process(request, entity, context);
        }
    }

    @Override
    public void process(
            final HttpResponse response,
            final EntityDetails entity,
            final HttpContext context) throws IOException, HttpException {
        for (final HttpResponseInterceptor responseInterceptor : this.responseInterceptors) {
            responseInterceptor.process(response, entity, context);
        }
    }

}
