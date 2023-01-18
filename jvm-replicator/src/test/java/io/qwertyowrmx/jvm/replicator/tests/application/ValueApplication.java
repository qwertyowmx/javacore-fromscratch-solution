package io.qwertyowrmx.jvm.replicator.tests.application;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class ValueApplication {
    public static void main(String[] args) {
        LOG.info("Program arguments: {}", Arrays.deepToString(args));
        Runtime.getRuntime().exit(Integer.parseInt(args[0]));
    }
}
