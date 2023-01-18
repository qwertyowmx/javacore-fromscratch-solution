package io.qwertyowrmx.proxygen.exceptions;

public class ProxyGenerationException extends RuntimeException {
    public ProxyGenerationException(String message) {
        super(message);
    }

    public ProxyGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
