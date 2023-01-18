package io.qwertyowrmx.executor.core.task.executor;

import io.qwertyowrmx.executor.core.task.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ParallelTaskExecutor implements TaskExecutor {
    @Override
    public void execute(List<Task> tasks) {
        if (tasks.isEmpty()) {
            LOG.info("Task list is empty, nothing to execute");
            return;
        }
        LOG.info("Executing {} tasks in parallel mode...", tasks.size());
        tasks.stream().parallel().forEach(Task::execute);
    }
}
