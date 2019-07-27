package org.apache.hc.core5.http.protocol;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.message.MessageSupport;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.http.protocol.HttpCoreContext;
import org.apache.hc.core5.util.Args;

import java.io.IOException;
import java.util.Iterator;

/**
 * ResponseConnControl is responsible for adding {@code Connection} header
 * to the outgoing responses, which is essential for managing persistence of
 * {@code HTTP/1.0} connections. This interceptor is recommended for
 * server side protocol processors.
 *
 * @since 4.0
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class ResponseConnControl implements HttpResponseInterceptor {

    public ResponseConnControl() {
        super();
    }

    @Override
    public void process(final HttpResponse response, final EntityDetails entity, final HttpContext context)
            throws HttpException, IOException {
        Args.notNull(response, "HTTP response");
        Args.notNull(context, "HTTP context");

        // Always drop connection after certain type of responses
        final int status = response.getCode();
        if (status == HttpStatus.SC_BAD_REQUEST ||
                status == HttpStatus.SC_REQUEST_TIMEOUT ||
                status == HttpStatus.SC_LENGTH_REQUIRED ||
                status == HttpStatus.SC_REQUEST_TOO_LONG ||
                status == HttpStatus.SC_REQUEST_URI_TOO_LONG ||
                status == HttpStatus.SC_SERVICE_UNAVAILABLE ||
                status == HttpStatus.SC_NOT_IMPLEMENTED) {
            response.setHeader(HttpHeaders.CONNECTION, HeaderElements.CLOSE);
            return;
        }
        if (!response.containsHeader(HttpHeaders.CONNECTION)) {
            // Always drop connection for HTTP/1.0 responses and below
            // if the content body cannot be correctly delimited
            final ProtocolVersion ver = context.getProtocolVersion();
            if (entity != null && entity.getContentLength() < 0 && ver.lessEquals(HttpVersion.HTTP_1_0)) {
                response.setHeader(HttpHeaders.CONNECTION, HeaderElements.CLOSE);
            } else {
                final HttpCoreContext coreContext = HttpCoreContext.adapt(context);
                final HttpRequest request = coreContext.getRequest();
                boolean closeRequested = false;
                boolean keepAliveRequested = false;
                if (request != null) {
                    final Iterator<HeaderElement> it = MessageSupport.iterate(request, HttpHeaders.CONNECTION);
                    while (it.hasNext()) {
                        final HeaderElement he = it.next();
                        if (he.getName().equalsIgnoreCase(HeaderElements.CLOSE)) {
                            closeRequested = true;
                            break;
                        } else if (he.getName().equalsIgnoreCase(HeaderElements.KEEP_ALIVE)) {
                            keepAliveRequested = true;
                        }
                    }
                }
                if (closeRequested) {
                    response.addHeader(HttpHeaders.CONNECTION, HeaderElements.CLOSE);
                } else {
                    if (response.containsHeader(HttpHeaders.UPGRADE)) {
                        response.addHeader(HttpHeaders.CONNECTION, HeaderElements.UPGRADE);
                    } else {
                        if (keepAliveRequested) {
                            response.addHeader(HttpHeaders.CONNECTION, HeaderElements.KEEP_ALIVE);
                        } else {
                            if (ver.lessEquals(HttpVersion.HTTP_1_0)) {
                                response.addHeader(HttpHeaders.CONNECTION, HeaderElements.CLOSE);
                            }
                        }
                    }
                }
            }
        }
    }

}
