package org.apache.hc.core5.http;

/**
 * 'Classic' {@link HttpRequest} message that can enclose {@link HttpEntity}.
 *
 *
 *  其实就是头部信息 加上具体协议 请求方法 请求路径等等 构成一个可以被识别的request
 * @since 5.0
 */
public interface ClassicHttpRequest extends HttpRequest, HttpEntityContainer {
}
