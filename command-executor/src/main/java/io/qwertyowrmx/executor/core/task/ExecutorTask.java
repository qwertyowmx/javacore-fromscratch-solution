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
