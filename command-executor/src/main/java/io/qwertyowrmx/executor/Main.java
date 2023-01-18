package io.qwertyowrmx.executor;

import io.qwertyowrmx.executor.core.job.ExecutorJob;
import io.qwertyowrmx.executor.core.job.Job;
import io.qwertyowrmx.executor.core.task.ExecutorTask;
import io.qwertyowrmx.executor.core.task.Task;
import io.qwertyowrmx.executor.core.task.command.ShellCommand;
import io.qwertyowrmx.executor.core.task.executor.ParallelTaskExecutor;
import io.qwertyowrmx.executor.core.task.source.InMemoryTaskSource;

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