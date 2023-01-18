package io.qwertyowrmx.executor.core.job;

import io.qwertyowrmx.executor.core.task.Task;
import io.qwertyowrmx.executor.core.task.executor.ParallelTaskExecutor;
import io.qwertyowrmx.executor.core.task.executor.TaskExecutor;
import io.qwertyowrmx.executor.core.task.source.InMemoryTaskSource;
import io.qwertyowrmx.executor.core.task.source.TaskSource;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ExecutorJob implements Job {
    protected List<Task> tasks;
    protected TaskSource taskSource;

    protected TaskExecutor taskExecutor;

    public ExecutorJob() {
        this.tasks = new ArrayList<>();
        this.taskSource = new InMemoryTaskSource(Collections.emptyList());
    }

    @Override
    public boolean hasTasks() {
        return !tasks.isEmpty();
    }

    @Override
    public void setTaskSource(TaskSource taskSource) {
        this.taskSource = taskSource;
    }

    @Override
    public void setTaskExecutor(TaskExecutor executor) {
        this.taskExecutor = executor;
    }

    @Override
    public boolean isParallel() {
        return taskExecutor instanceof ParallelTaskExecutor;
    }

    @Override
    public void execute() {
        this.tasks = taskSource.readTasks();
        LOG.info("Started job execution, got {} tasks", tasks.size());
        taskExecutor.execute(tasks);
    }
}
