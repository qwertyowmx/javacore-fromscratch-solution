/*
 * MIT License
 *
 * Copyright (c) 2023 qwertyowmx
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

package io.qwertyowmx.executor;

import io.qwertyowmx.executor.core.job.ExecutorJob;
import io.qwertyowmx.executor.core.job.Job;
import io.qwertyowmx.executor.core.task.ExecutorTask;
import io.qwertyowmx.executor.core.task.Task;
import io.qwertyowmx.executor.core.task.command.ShellCommand;
import io.qwertyowmx.executor.core.task.executor.ParallelTaskExecutor;
import io.qwertyowmx.executor.core.task.source.InMemoryTaskSource;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Job job = new ExecutorJob();
        ExecutorTask executorTask = new ExecutorTask();
        executorTask.makeParallel();
        executorTask.addCommand(new ShellCommand("echo 1232"));
        executorTask.addCommand(new ShellCommand("echo 1233"));
        executorTask.addCommand(new ShellCommand("echo 1234"));
        // coffeeTask.addCommand(new ShellCommand("git clone https://github.com/btrfs/fstests"));
        List<Task> tasks = List.of(executorTask);
        job.setTaskSource(new InMemoryTaskSource(tasks));
        job.setTaskExecutor(new ParallelTaskExecutor());
        job.execute();
    }
}