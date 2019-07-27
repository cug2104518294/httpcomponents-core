package org.apache.hc.core5.http.io;

import org.apache.hc.core5.http.HttpConnection;

import java.io.IOException;
import java.net.Socket;

/**
 * Factory for {@link HttpConnection} instances.
 *
 * @since 4.3
 */
public interface HttpConnectionFactory<T extends HttpConnection> {

    T createConnection(Socket socket) throws IOException;

}
