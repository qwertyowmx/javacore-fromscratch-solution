package io.qwertyowrmx.executor.core.task;

import io.qwertyowrmx.executor.core.task.command.Command;

public interface Task {
    void execute();

    boolean isParallel();

    void makeParallel();

    boolean hasCommands();

    void addCommand(Command command);
}
