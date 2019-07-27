package org.apache.hc.core5.testing.classic;

import org.apache.hc.core5.http.impl.io.SocketHolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class LoggingSocketHolder extends SocketHolder {

    private final Wire wire;

    LoggingSocketHolder(final Socket socket, final Wire wire) {
        super(socket);
        this.wire = wire;
    }

    @Override
    protected InputStream getInputStream(final Socket socket) throws IOException {
        return new LoggingInputStream(super.getInputStream(socket), wire);
    }

    @Override
    protected OutputStream getOutputStream(final Socket socket) throws IOException {
        return new LoggingOutputStream(super.getOutputStream(socket), wire);
    }
}
