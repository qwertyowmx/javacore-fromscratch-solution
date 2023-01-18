/*
 * MIT License
 *
 * Copyright (c) 2023 qwertyowrmx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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
