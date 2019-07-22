package org.apache.hc.core5.http.io;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;

import java.io.IOException;

/**
 * A server-side HTTP connection, which can be used for receiving
 * requests and sending responses.
 *
 * @since 4.0
 */
public interface HttpServerConnection extends BHttpConnection {

    /**
     * Receives the request line and all headers available from this connection.
     * The caller should examine the returned request and decide if to receive a
     * request entity as well.
     *
     * @return a new HttpRequest object whose request line and headers are
     * initialized.
     * @throws HttpException in case of HTTP protocol violation
     * @throws IOException   in case of an I/O error
     */
    ClassicHttpRequest receiveRequestHeader()
            throws HttpException, IOException;

    /**
     * Receives the next request entity available from this connection and attaches it to
     * an existing request.
     *
     * @param request the request to attach the entity to.
     * @throws HttpException in case of HTTP protocol violation
     * @throws IOException   in case of an I/O error
     */
    void receiveRequestEntity(ClassicHttpRequest request)
            throws HttpException, IOException;

    /**
     * Sends the response line and headers of a response over this connection.
     *
     * @param response the response whose headers to send.
     * @throws HttpException in case of HTTP protocol violation
     * @throws IOException   in case of an I/O error
     */
    void sendResponseHeader(ClassicHttpResponse response)
            throws HttpException, IOException;

    /**
     * Sends the response entity of a response over this connection.
     *
     * @param response the response whose entity to send.
     * @throws HttpException in case of HTTP protocol violation
     * @throws IOException   in case of an I/O error
     */
    void sendResponseEntity(ClassicHttpResponse response)
            throws HttpException, IOException;

}
