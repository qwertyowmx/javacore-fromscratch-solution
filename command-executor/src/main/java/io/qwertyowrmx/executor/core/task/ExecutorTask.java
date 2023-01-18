package io.qwertyowrmx.executor.core.task;

import io.qwertyowrmx.executor.core.task.command.Command;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExecutorTask implements Task {

    protected List<Command> commands;
    private boolean isParallel;

    public ExecutorTask() {
        commands = new ArrayList<>();
        isParallel = false;
    }

    @Override
    public boolean hasCommands() {
        return !commands.isEmpty();
    }

    @Override
    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public boolean isParallel() {
        return isParallel;
    }

    @Override
    public void makeParallel() {
        isParallel = true;
    }

    @Override
    public void execute() {
        if (hasCommands()) {
            if (isParallel()) {
                LOG.info("Started execution {} commands in parallel mode", commands.size());
                commands.parallelStream().forEach(Command::execute);
            } else {
                LOG.info("Started execution {} commands in sequential mode", commands.size());
                commands.forEach(Command::execute);
            }
        } else {
            LOG.info("Nothing to execute");
        }
    }
}
