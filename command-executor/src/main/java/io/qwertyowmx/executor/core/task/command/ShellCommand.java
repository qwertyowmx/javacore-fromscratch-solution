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

package io.qwertyowmx.executor.core.task.command;

import io.qwertyowmx.executor.core.task.command.exceptions.CommandExecutionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@AllArgsConstructor
public class ShellCommand implements Command {
    private String shellCommand;

    @Override
    public void execute() {
        try {
            LOG.info("Executing shell command, \"{}\"", shellCommand.trim());
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("cmd", "/c", shellCommand.trim());
            String processStdout = getProcessStdout(builder.start());
            LOG.info("Process stdout: \n{}", processStdout);
        } catch (IOException e) {
            throw new CommandExecutionException("Unable to execute shell command", e);
        }
    }

    private String getProcessStdout(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder outcome = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            outcome.append(line);
            outcome.append(System.getProperty("line.separator"));
        }
        return outcome.toString();
    }
}
