package io.qwertyowrmx.executor.core.task.executor;

import io.qwertyowrmx.executor.core.task.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SequentialTaskExecutor implements TaskExecutor {
    @Override
    public void execute(List<Task> tasks) {
        if (tasks.isEmpty()) {
            LOG.info("Task list is empty, nothing to execute");
            return;
        }
        LOG.info("Executing {} tasks in sequential mode...", tasks.size());
        tasks.forEach(Task::execute);
    }
}
