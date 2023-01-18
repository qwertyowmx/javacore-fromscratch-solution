package io.qwertyowrmx.executor.core.task.source;

import io.qwertyowrmx.executor.core.task.Task;

import java.util.List;

public interface TaskSource {
    List<Task> readTasks();
}
