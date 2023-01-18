package io.qwertyowrmx.jvm.replicator;

import io.qwertyowrmx.jvm.replicator.exceptions.JvmReplicationException;
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
