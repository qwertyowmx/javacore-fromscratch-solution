package io.qwertyowrmx.executor.core.job;

import io.qwertyowrmx.executor.core.task.executor.TaskExecutor;
import io.qwertyowrmx.executor.core.task.source.TaskSource;

public interface Job {
    void execute();

    boolean hasTasks();

    boolean isParallel();

    void setTaskSource(TaskSource taskSource);

    void setTaskExecutor(TaskExecutor executor);
}
