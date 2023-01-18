package io.qwertyowrmx.jvm.replicator;

import io.qwertyowrmx.jvm.replicator.exceptions.JvmLaunchRuntimeException;

public class DefaultJvmReplica implements JvmReplica {

    private final Process process;

    public DefaultJvmReplica(Process process) {
        this.process = process;
    }

    @Override
    public long getProcessId() {
        return process.pid();
    }

    @Override
    public void stop() {
        process.destroy();
    }

    @Override
    public void forceStop() {
        this.process.destroyForcibly();
    }

    @Override
    public void waitFor() {
        try {
            this.process.waitFor();
        } catch (InterruptedException e) {
            throw new JvmLaunchRuntimeException(
                    "Unable to wait for process with PID: "
                            + getProcessId() + ", it was interrupted", e);
        }
    }

    @Override
    public int getExitCode() {
        return process.exitValue();
    }
}
