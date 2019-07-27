package org.apache.hc.core5.http2.protocol;

import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.ProtocolVersion;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.http.protocol.RequestValidateHost;
import org.apache.hc.core5.util.Args;

import java.io.IOException;

/**
 * HTTP/2 compatible extension of {@link RequestValidateHost}.
 *
 * @since 5.0
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class H2RequestValidateHost extends RequestValidateHost {

    @Override
    public void process(
            final HttpRequest request,
            final EntityDetails entity,
            final HttpContext context) throws HttpException, IOException {
        Args.notNull(context, "HTTP context");
        final ProtocolVersion ver = context.getProtocolVersion();
        if (ver.getMajor() < 2) {
            super.process(request, entity, context);
        }
    }

}
