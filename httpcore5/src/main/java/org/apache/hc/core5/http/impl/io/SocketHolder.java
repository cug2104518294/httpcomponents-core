package org.apache.hc.core5.http.impl.io;

import org.apache.hc.core5.util.Args;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Utility class that holds a {@link Socket} along with copies of its {@link InputStream}
 * and {@link OutputStream}.
 *
 * @since 5.0
 */
public class SocketHolder {

    private final Socket socket;
    private final AtomicReference<InputStream> inputStreamRef;
    private final AtomicReference<OutputStream> outputStreamRef;

    public SocketHolder(final Socket socket) {
        this.socket = Args.notNull(socket, "Socket");
        this.inputStreamRef = new AtomicReference<>(null);
        this.outputStreamRef = new AtomicReference<>(null);
    }

    public final Socket getSocket() {
        return socket;
    }

    public final InputStream getInputStream() throws IOException {
        InputStream local = inputStreamRef.get();
        if (local != null) {
            return local;
        }
        local = getInputStream(socket);
        if (inputStreamRef.compareAndSet(null, local)) {
            return local;
        }
        return inputStreamRef.get();
    }

    protected InputStream getInputStream(final Socket socket) throws IOException {
        return socket.getInputStream();
    }

    protected OutputStream getOutputStream(final Socket socket) throws IOException {
        return socket.getOutputStream();
    }

    public final OutputStream getOutputStream() throws IOException {
        OutputStream local = outputStreamRef.get();
        if (local != null) {
            return local;
        }
        local = getOutputStream(socket);
        if (outputStreamRef.compareAndSet(null, local)) {
            return local;
        }
        return outputStreamRef.get();
    }

    @Override
    public String toString() {
        return socket.toString();
    }

}
