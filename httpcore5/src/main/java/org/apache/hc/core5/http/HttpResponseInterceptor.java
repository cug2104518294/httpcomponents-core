package org.apache.hc.core5.http;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.protocol.HttpContext;

import java.io.IOException;

/**
 * HTTP protocol interceptor is a routine that implements a specific aspect of
 * the HTTP protocol. Usually protocol interceptors are expected to act upon
 * one specific header or a group of related headers of the incoming message
 * or populate the outgoing message with one specific header or a group of
 * related headers. Protocol
 * <p>
 * Interceptors can also manipulate content entities enclosed with messages.
 * Usually this is accomplished by using the 'Decorator' pattern where a wrapper
 * entity class is used to decorate the original entity.
 * <p>
 * Protocol interceptors must be implemented as thread-safe. Similarly to
 * servlets, protocol interceptors should not use instance variables unless
 * access to those variables is synchronized.
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.STATELESS)
public interface HttpResponseInterceptor {

    /**
     * Processes a response.
     * On the server side, this step is performed before the response is
     * sent to the client. On the client side, this step is performed
     * on incoming messages before the message body is evaluated.
     *
     * @param response the response to process
     * @param entity   the request entity details or {@code null} if not available
     * @param context  the context for the request
     * @throws HttpException in case of an HTTP protocol violation
     * @throws IOException   in case of an I/O error
     */
    void process(HttpResponse response, EntityDetails entity, HttpContext context) throws HttpException, IOException;

}
