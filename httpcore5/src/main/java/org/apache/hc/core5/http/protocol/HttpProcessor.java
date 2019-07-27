package org.apache.hc.core5.http.protocol;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.HttpResponseInterceptor;

/**
 * HTTP protocol processor is a collection of protocol interceptors that
 * implements the 'Chain of Responsibility' pattern, where each individual
 * protocol interceptor is expected to work on a particular aspect of the HTTP
 * protocol the interceptor is responsible for.
 * <p>
 * Usually the order in which interceptors are executed should not matter as
 * long as they do not depend on a particular state of the execution context.
 * If protocol interceptors have interdependencies and therefore must be
 * executed in a particular order, they should be added to the protocol
 * processor in the same sequence as their expected execution order.
 * <p>
 * Protocol interceptors must be implemented as thread-safe. Similarly to
 * servlets, protocol interceptors should not use instance variables unless
 * access to those variables is synchronized.
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.STATELESS)
public interface HttpProcessor extends HttpRequestInterceptor, HttpResponseInterceptor {
}
