package org.apache.hc.core5.http.io;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;

import java.io.IOException;

/**
 * A client-side HTTP connection, which can be used for sending
 * requests and receiving responses.
 *
 * @since 4.0
 */
public interface HttpClientConnection extends BHttpConnection {

    /**
     * Checks whether this connection is in a consistent state.
     *
     * @return {@code true} if the connection is known to be
     * in a inconsistent state and cannot be re-used.
     * @see #terminateRequest(ClassicHttpRequest)
     * @since 5.0
     */
    boolean isConsistent();

    /**
     * Sends the request line and all headers over the connection.
     *
     * @param request the request whose headers to send.
     * @throws HttpException in case of HTTP protocol violation
     * @throws IOException   in case of an I/O error
     */
    void sendRequestHeader(ClassicHttpRequest request)
            throws HttpException, IOException;

    /**
     * Terminates request prematurely potentially leaving
     * the connection in a inconsistent state.
     *
     * @param request the request to be terminated prematurely.
     * @throws HttpException
     * @throws IOException
     * @see #isConsistent()
     * @since 5.0
     */
    void terminateRequest(ClassicHttpRequest request)
            throws HttpException, IOException;

    /**
     * Sends the request entity over the connection.
     *
     * @param request the request whose entity to send.
     * @throws HttpException in case of HTTP protocol violation
     * @throws IOException   in case of an I/O error
     */
    void sendRequestEntity(ClassicHttpRequest request)
            throws HttpException, IOException;

    /**
     * Receives the request line and headers of the next response available from
     * this connection. The caller should examine the HttpResponse object to
     * find out if it should try to receive a response entity as well.
     *
     * @return a new HttpResponse object with status line and headers
     * initialized.
     * @throws HttpException in case of HTTP protocol violation
     * @throws IOException   in case of an I/O error
     */
    ClassicHttpResponse receiveResponseHeader()
            throws HttpException, IOException;

    /**
     * Receives the next response entity available from this connection and
     * attaches it to an existing HttpResponse object.
     *
     * @param response the response to attach the entity to
     * @throws HttpException in case of HTTP protocol violation
     * @throws IOException   in case of an I/O error
     */
    void receiveResponseEntity(ClassicHttpResponse response)
            throws HttpException, IOException;

}
