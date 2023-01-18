package io.qwertyowrmx.executor.core.task.command.exceptions;

import io.qwertyowrmx.executor.core.exceptions.ExecutionException;

public class CommandExecutionException extends ExecutionException {
    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
