package io.qwertyowrmx.rpc.common.exceptions;

public class BaseRPCException extends RuntimeException {
    public BaseRPCException(String message) {
        super(message);
    }

    public BaseRPCException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRPCException(Throwable cause) {
        super(cause);
    }
}
