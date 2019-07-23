package org.apache.hc.core5.http.io;

import org.apache.hc.core5.http.HttpConnection;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;

/**
 * Informational (1xx) HTTP response callback.
 *
 * @since 5.0
 */
public interface HttpResponseInformationCallback {

    void execute(HttpResponse response, HttpConnection connection, HttpContext context) throws HttpException;

}
