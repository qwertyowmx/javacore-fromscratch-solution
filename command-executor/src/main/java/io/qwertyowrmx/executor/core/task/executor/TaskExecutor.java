package io.qwertyowrmx.executor.core.task.executor;

import io.qwertyowrmx.executor.core.task.Task;

import java.util.List;

public interface TaskExecutor {
    void execute(List<Task> tasks);
}
