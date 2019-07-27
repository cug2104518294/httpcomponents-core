package org.apache.hc.core5.http;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.protocol.HttpContext;

/**
 * Interface for deciding whether a connection can be re-used for
 * subsequent requests and should be kept alive.
 *
 * 连接重用策略
 *
 * <p>
 * Implementations of this interface must be thread-safe. Access to shared
 * data must be synchronized as methods of this interface may be executed
 * from multiple threads.
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.STATELESS)
public interface ConnectionReuseStrategy {

    /**
     * Decides whether a connection can be kept open after a request.
     * If this method returns {@code false}, the caller MUST
     * close the connection to correctly comply with the HTTP protocol.
     * If it returns {@code true}, the caller SHOULD attempt to
     * keep the connection open for reuse with another request.
     * <p>
     * One can use the HTTP context to retrieve additional objects that
     * may be relevant for the keep-alive strategy: the actual HTTP
     * connection, the original HTTP request, target host if known,
     * number of times the connection has been reused already and so on.
     * </p>
     * <p>
     * If the connection is already closed, {@code false} is returned.
     * The stale connection check MUST NOT be triggered by a
     * connection reuse strategy.
     * </p>
     *
     * @param request  The last request transmitted over that connection.
     * @param response The last response transmitted over that connection.
     * @param context  the context in which the connection is being
     *                 used.
     * @return {@code true} if the connection is allowed to be reused, or
     * {@code false} if it MUST NOT be reused
     */
    boolean keepAlive(HttpRequest request, HttpResponse response, HttpContext context);

}
