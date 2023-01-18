package io.qwertyowrmx.jvm.replicator.tests.application;

import java.util.Arrays;

public class ArgsApplication {
    public static void main(String[] args) {
        if (Arrays.equals(args, new String[]{"-port", "8081"})) {
            System.exit(0);
        } else System.exit(-1);
    }
}
