package io.qwertyowrmx.executor.core.task.command;

import io.qwertyowrmx.executor.core.task.command.exceptions.CommandExecutionException;
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
