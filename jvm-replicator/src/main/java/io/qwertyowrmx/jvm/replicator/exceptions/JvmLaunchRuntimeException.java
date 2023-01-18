package io.qwertyowrmx.jvm.replicator.exceptions;

public class JvmLaunchRuntimeException extends JvmReplicatorException {
    public JvmLaunchRuntimeException(String message) {
        super(message);
    }

    public JvmLaunchRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
