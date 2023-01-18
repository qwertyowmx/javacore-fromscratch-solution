package io.qwertyowrmx.rpc.client.exceptions;


import io.qwertyowrmx.rpc.common.exceptions.BaseRPCException;

public class StubCreationException extends BaseRPCException {
    public StubCreationException(String message) {
        super(message);
    }

    public StubCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
