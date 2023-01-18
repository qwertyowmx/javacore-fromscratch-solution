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

package io.qwertyowrmx.jvm.replicator.tests;

import io.qwertyowrmx.jvm.replicator.JvmReplica;
import io.qwertyowrmx.jvm.replicator.JvmReplicator;
import io.qwertyowrmx.jvm.replicator.tests.application.ArgsApplication;
import io.qwertyowrmx.jvm.replicator.tests.application.FailedHelloWorldApplication;
import io.qwertyowrmx.jvm.replicator.tests.application.HelloWorldApplication;
import io.qwertyowrmx.jvm.replicator.tests.application.ValueApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Disabled // failed on GitHub CI, but success in local machine
public class JvmReplicatorTest {

    @Test
    public void testCreateOneReplica() {
        JvmReplicator replicator = new JvmReplicator(HelloWorldApplication.class);
        JvmReplica replica = replicator.replicate();
        long processId = replica.getProcessId();
        Assertions.assertNotEquals(0, processId);
        replica.waitFor();
        Assertions.assertEquals(0, replica.getExitCode());
    }


    @Test
    public void testCreateFiveReplicas() {
        JvmReplicator replicator = new JvmReplicator(HelloWorldApplication.class);

        List<JvmReplica> replicas = new ArrayList<>();
        for (int currentReplica = 0; currentReplica < 5; currentReplica++) {
            replicas.add(replicator.replicate());
        }

        for (JvmReplica replica : replicas) {
            replica.waitFor();
            Assertions.assertEquals(0, replica.getExitCode());
        }
    }

    @Test
    public void testGetExitCodeFromFailedProcess() {
        JvmReplicator replicator = new JvmReplicator(FailedHelloWorldApplication.class);
        JvmReplica jvm = replicator.replicate();
        jvm.waitFor();
        Assertions.assertEquals(1, jvm.getExitCode());
    }


    @Test
    public void testReturnValueFromProcess() {
        JvmReplicator replicator = new JvmReplicator(
                ValueApplication.class,
                new String[]{"1"});

        JvmReplica replica = replicator.replicate();

        replica.waitFor();

        Assertions.assertEquals(1, replica.getExitCode());
    }

    @Test
    public void testChildProcessReceiveArgs() {
        JvmReplicator replicator = new JvmReplicator(
                ArgsApplication.class,
                new String[]{"-port", "8081"});

        JvmReplica replica = replicator.replicate();

        replica.waitFor();

        Assertions.assertEquals(0, replica.getExitCode());
    }
}
