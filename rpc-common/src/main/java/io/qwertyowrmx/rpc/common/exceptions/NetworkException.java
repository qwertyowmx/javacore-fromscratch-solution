package io.qwertyowrmx.rpc.common.exceptions;

public class NetworkException extends BaseRPCException {
    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
