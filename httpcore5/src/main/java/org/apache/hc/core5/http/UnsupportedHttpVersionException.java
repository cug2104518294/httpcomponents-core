package org.apache.hc.core5.http;

/**
 * Signals an unsupported version of the HTTP protocol.
 *
 * @since 4.0
 */
public class UnsupportedHttpVersionException extends ProtocolException {

    private static final long serialVersionUID = -1348448090193107031L;

    /**
     * Creates an exception without a detail message.
     */
    public UnsupportedHttpVersionException() {
        super();
    }

    /**
     * Creates an exception with a detail message for the given ProtocolVersion.
     *
     * @param protocolVersion The unsupported ProtocolVersion.
     */
    public UnsupportedHttpVersionException(final ProtocolVersion protocolVersion) {
        super("Unsupported version: " + protocolVersion);
    }

    /**
     * Creates an exception with the specified detail message.
     *
     * @param message The exception detail message
     */
    public UnsupportedHttpVersionException(final String message) {
        super(message);
    }

}
