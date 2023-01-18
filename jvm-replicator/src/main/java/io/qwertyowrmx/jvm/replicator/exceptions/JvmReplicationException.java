package io.qwertyowrmx.jvm.replicator.exceptions;

public class JvmReplicationException extends JvmReplicatorException {
    public JvmReplicationException(String message) {
        super(message);
    }

    public JvmReplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
