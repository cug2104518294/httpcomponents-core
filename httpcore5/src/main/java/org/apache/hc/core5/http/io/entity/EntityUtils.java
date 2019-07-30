package org.apache.hc.core5.http.io.entity;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.io.Closer;
import org.apache.hc.core5.net.URLEncodedUtils;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.ByteArrayBuffer;
import org.apache.hc.core5.util.CharArrayBuffer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.List;

/**
 * Support methods for {@link HttpEntity}.
 *
 * https://www.jianshu.com/p/128f75661ee2
 *
 * HttpClient推荐使用HttpEntity的getConent()方法或者HttpEntity的writeTo(OutputStream)方法来消耗掉Http实体内容。
 * HttpClient也提供了EntityUtils这个类，这个类提供一些静态方法可以更容易地读取Http实体的内容和信息。
 * 和以java.io.InputStream流读取内容的方式相比，EntityUtils提供的方法可以以字符串或者字节数组的形式读取Http实体。
 * 但是，强烈不推荐使用EntityUtils这个类，除非目标服务器发出的响应是可信任的，并且http响应实体的长度不会过大。
 * 有些情况下，我们希望可以重复读取Http实体的内容。这就需要把Http实体内容缓存在内存或者磁盘上。
 * 最简单的方法就是把Http Entity转化成BufferedHttpEntity，这样就把原Http实体的内容缓冲到了内存中。后面我们就可以重复读取BufferedHttpEntity中的内容。
 *
 * CloseableHttpResponse response = <...>
 * HttpEntity entity = response.getEntity();
 * if (entity != null) {
 *     entity = new BufferedHttpEntity(entity);
 *
 * @since 4.0
 */
public final class EntityUtils {

    private EntityUtils() {
    }

    /**
     * Ensures that the entity content is fully consumed and the content stream, if exists,
     * is closed. The process is done, <i>quietly</i> , without throwing any IOException.
     *
     * @param entity the entity to consume.
     * @since 4.2
     */
    public static void consumeQuietly(final HttpEntity entity) {
        try {
            consume(entity);
        } catch (final IOException ignore) {
        }
    }

    /**
     * Ensures that the entity content is fully consumed and the content stream, if exists,
     * is closed.
     *
     * @param entity the entity to consume.
     * @throws IOException if an error occurs reading the input stream
     * @since 4.1
     */
    public static void consume(final HttpEntity entity) throws IOException {
        if (entity == null) {
            return;
        }
        if (entity.isStreaming()) {
            Closer.close(entity.getContent());
        }
    }

    /**
     * Read the contents of an entity and return it as a byte array.
     *
     * @param entity the entity to read from=
     * @return byte array containing the entity content. May be null if
     * {@link HttpEntity#getContent()} is null.
     * @throws IOException              if an error occurs reading the input stream
     * @throws IllegalArgumentException if entity is null or if content length &gt; Integer.MAX_VALUE
     */
    public static byte[] toByteArray(final HttpEntity entity) throws IOException {
        Args.notNull(entity, "Entity");
        try (final InputStream inStream = entity.getContent()) {
            if (inStream == null) {
                return null;
            }
            int i = (int) Args.checkContentLength(entity);
            if (i < 0) {
                i = 4096;
            }
            final ByteArrayBuffer buffer = new ByteArrayBuffer(i);
            final byte[] tmp = new byte[4096];
            int l;
            while ((l = inStream.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
            return buffer.toByteArray();
        }
    }

    //将对应entity根据ContentType 以及对应编码 转换成字符串
    private static String toString(
            final HttpEntity entity,
            final ContentType contentType) throws IOException {
        try (final InputStream inStream = entity.getContent()) {
            if (inStream == null) {
                return null;
            }
            int contentLength = (int) Args.checkContentLength(entity);
            if (contentLength < 0) {
                contentLength = 4096;
            }
            Charset charset = null;
            if (contentType != null) {
                charset = contentType.getCharset();
                if (charset == null) {
                    final ContentType defaultContentType = ContentType.getByMimeType(contentType.getMimeType());
                    charset = defaultContentType != null ? defaultContentType.getCharset() : null;
                }
            }
            if (charset == null) {
                charset = StandardCharsets.ISO_8859_1;
            }
            final Reader reader = new InputStreamReader(inStream, charset);
            final CharArrayBuffer buffer = new CharArrayBuffer(contentLength);
            final char[] tmp = new char[1024];
            int chReadCount;
            while ((chReadCount = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, chReadCount);
            }
            return buffer.toString();
        }
    }

    /**
     * Get the entity content as a String, using the provided default character set
     * if none is found in the entity.
     * If defaultCharset is null, the default "ISO-8859-1" is used.
     *
     * @param entity         must not be null
     * @param defaultCharset character set to be applied if none found in the entity,
     *                       or if the entity provided charset is invalid or not available.
     * @return the entity content as a String. May be null if
     * {@link HttpEntity#getContent()} is null.
     * @throws ParseException                               if header elements cannot be parsed
     * @throws IllegalArgumentException                     if entity is null or if content length &gt; Integer.MAX_VALUE
     * @throws IOException                                  if an error occurs reading the input stream
     * @throws java.nio.charset.UnsupportedCharsetException Thrown when the named entity's charset is not available in
     *                                                      this instance of the Java virtual machine and no defaultCharset is provided.
     */
    public static String toString(
            final HttpEntity entity, final Charset defaultCharset) throws IOException, ParseException {
        Args.notNull(entity, "Entity");
        ContentType contentType = null;
        try {
            contentType = ContentType.parse(entity.getContentType());
        } catch (final UnsupportedCharsetException ex) {
            if (defaultCharset == null) {
                throw new UnsupportedEncodingException(ex.getMessage());
            }
        }
        if (contentType != null) {
            if (contentType.getCharset() == null) {
                contentType = contentType.withCharset(defaultCharset);
            }
        } else {
            contentType = ContentType.DEFAULT_TEXT.withCharset(defaultCharset);
        }
        return toString(entity, contentType);
    }

    /**
     * Get the entity content as a String, using the provided default character set
     * if none is found in the entity.
     * If defaultCharset is null, the default "ISO-8859-1" is used.
     *
     * @param entity         must not be null
     * @param defaultCharset character set to be applied if none found in the entity
     * @return the entity content as a String. May be null if
     * {@link HttpEntity#getContent()} is null.
     * @throws ParseException                               if header elements cannot be parsed
     * @throws IllegalArgumentException                     if entity is null or if content length &gt; Integer.MAX_VALUE
     * @throws IOException                                  if an error occurs reading the input stream
     * @throws java.nio.charset.UnsupportedCharsetException Thrown when the named charset is not available in
     *                                                      this instance of the Java virtual machine
     */
    public static String toString(
            final HttpEntity entity, final String defaultCharset) throws IOException, ParseException {
        return toString(entity, defaultCharset != null ? Charset.forName(defaultCharset) : null);
    }

    /**
     * Read the contents of an entity and return it as a String.
     * The content is converted using the character set from the entity (if any),
     * failing that, "ISO-8859-1" is used.
     *
     * @param entity the entity to convert to a string; must not be null
     * @return String containing the content.
     * @throws ParseException                               if header elements cannot be parsed
     * @throws IllegalArgumentException                     if entity is null or if content length &gt; Integer.MAX_VALUE
     * @throws IOException                                  if an error occurs reading the input stream
     * @throws java.nio.charset.UnsupportedCharsetException Thrown when the named charset is not available in
     *                                                      this instance of the Java virtual machine
     */
    public static String toString(final HttpEntity entity) throws IOException, ParseException {
        Args.notNull(entity, "Entity");
        return toString(entity, ContentType.parse(entity.getContentType()));
    }

    /**
     * Returns a list of {@link NameValuePair NameValuePairs} as parsed from an {@link HttpEntity}.
     * The encoding is taken from the entity's Content-Encoding header.
     * <p>
     * This is typically used while parsing an HTTP POST.
     *
     * @param entity The entity to parse
     * @return a list of {@link NameValuePair} as built from the URI's query portion.
     * @throws IOException If there was an exception getting the entity's data.
     */
    public static List<NameValuePair> parse(final HttpEntity entity) throws IOException {
        Args.notNull(entity, "HTTP entity");
        final ContentType contentType = ContentType.parse(entity.getContentType());
        if (!ContentType.APPLICATION_FORM_URLENCODED.isSameMimeType(contentType)) {
            return Collections.emptyList();
        }
        final long len = entity.getContentLength();
        Args.checkRange(len, 0, Integer.MAX_VALUE, "HTTP entity is too large");
        final Charset charset = contentType.getCharset() != null ? contentType.getCharset()
                : StandardCharsets.ISO_8859_1;
        final CharArrayBuffer buf;
        try (final InputStream inStream = entity.getContent()) {
            if (inStream == null) {
                return Collections.emptyList();
            }
            buf = new CharArrayBuffer(len > 0 ? (int) len : 1024);
            final Reader reader = new InputStreamReader(inStream, charset);
            final char[] tmp = new char[1024];
            int l;
            while ((l = reader.read(tmp)) != -1) {
                buf.append(tmp, 0, l);
            }

        }
        if (buf.isEmpty()) {
            return Collections.emptyList();
        }
        return URLEncodedUtils.parse(buf, charset, '&');
    }

}
