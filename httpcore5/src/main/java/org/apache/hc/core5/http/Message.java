package org.apache.hc.core5.http;

import org.apache.hc.core5.util.Args;

/**
 * Generic message consisting of a message head and a message body.
 *
 * @param <H> message head type.
 * @param <B> message body type.
 * @since 5.0
 */
public final class Message<H extends MessageHeaders, B> {

    private final H head;
    private final B body;

    public Message(final H head, final B body) {
        this.head = Args.notNull(head, "Message head");
        this.body = body;
    }

    public H getHead() {
        return head;
    }

    public B getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "[" +
                "head=" + head +
                ", body=" + body +
                ']';
    }

}
