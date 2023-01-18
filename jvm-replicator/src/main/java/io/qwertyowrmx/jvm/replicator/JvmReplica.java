package io.qwertyowrmx.jvm.replicator;

public interface JvmReplica {

    long getProcessId();

    void stop();

    void forceStop();

    void waitFor();

    int getExitCode();
}
