package io.qwertyowrmx.jvm.replicator.exceptions;

public class JvmReplicatorException extends RuntimeException {
    public JvmReplicatorException() {
    }

    public JvmReplicatorException(String message) {
        super(message);
    }

    public JvmReplicatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
