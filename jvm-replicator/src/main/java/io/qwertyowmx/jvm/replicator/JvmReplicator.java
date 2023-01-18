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

package io.qwertyowmx.jvm.replicator;

import io.qwertyowmx.jvm.replicator.exceptions.JvmReplicationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.lang.ProcessBuilder.Redirect.INHERIT;

@Slf4j
@AllArgsConstructor
public class JvmReplicator {

    private Class<?> mainClass;
    private String[] args;

    public JvmReplicator(Class<?> mainClass) {
        this(mainClass, new String[]{});
    }

    public JvmReplica replicate() {

        ProcessHandle.Info currentProcessInfo = ProcessHandle.current().info();
        List<String> newProcessCommandLine = new LinkedList<>();

        Optional<String> command = currentProcessInfo.command();

        if (command.isEmpty()) {
            throw new RuntimeException("Unable to start new JVM");
        }

        newProcessCommandLine.add(command.get());
        Optional<String[]> currentProcessArgs = currentProcessInfo.arguments();
        currentProcessArgs.ifPresent(arguments -> newProcessCommandLine.addAll(Arrays.asList(arguments)));
        newProcessCommandLine.add("-classpath");
        newProcessCommandLine.add(ManagementFactory.getRuntimeMXBean().getClassPath());
        newProcessCommandLine.add(mainClass.getName());
        newProcessCommandLine.addAll(List.of(args));

        ProcessBuilder newProcessBuilder = new ProcessBuilder(newProcessCommandLine)
                .redirectOutput(INHERIT)
                .redirectError(INHERIT);

        Process process = startProcess(newProcessBuilder);

        return new DefaultJvmReplica(process);
    }

    private Process startProcess(ProcessBuilder newProcessBuilder) {
        try {
            Process createdProcess = newProcessBuilder.start();
            LOG.info("New JVM process pid: {}", createdProcess.pid());
            LOG.info("JVM process command: {}", newProcessBuilder.command());
            return createdProcess;
        } catch (IOException e) {
            throw new JvmReplicationException("Unable to start new JVM instance", e);
        }
    }
}
