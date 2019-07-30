package org.apache.hc.core5.http.io;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;

import java.io.IOException;

/**
 * Handler that encapsulates the process of generating a response object
 * from a {@link ClassicHttpResponse}.
 * <p>
 * 处理程序，它封装了从{@link ClassicHttpResponse}生成响应对象的过程。
 *
 * 具体将response的结果封装的 是由对应client处理的 因此对应的实现类都是放在client包中
 *
 * @since 4.0
 */
public interface HttpClientResponseHandler<T> {

    /**
     * Processes an {@link ClassicHttpResponse} and returns some value
     * corresponding to that response.
     *
     * @param response The response to process
     * @return A value determined by the response
     * @throws IOException in case of a problem or the connection was aborted
     */
    T handleResponse(ClassicHttpResponse response) throws HttpException, IOException;

}
