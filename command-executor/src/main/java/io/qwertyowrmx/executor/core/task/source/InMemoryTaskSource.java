package io.qwertyowrmx.executor.core.task.source;

import io.qwertyowrmx.executor.core.task.Task;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class InMemoryTaskSource implements TaskSource {
    private List<Task> tasks;

    @Override
    public List<Task> readTasks() {
        return tasks;
    }
}
