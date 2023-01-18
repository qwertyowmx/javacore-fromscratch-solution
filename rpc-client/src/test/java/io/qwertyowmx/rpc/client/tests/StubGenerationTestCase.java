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

package io.qwertyowmx.rpc.client.tests;


import io.qwertyowmx.rpc.client.StubBuilder;
import io.qwertyowmx.rpc.client.exceptions.StubCreationException;
import io.qwertyowmx.rpc.client.tests.parameters.SimpleStubBuilderProvider;
import io.qwertyowmx.rpc.client.tests.service.SampleService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StubGenerationTestCase {

    @ParameterizedTest
    @ArgumentsSource(SimpleStubBuilderProvider.class)
    public void testCreatedStubIsNotNull(StubBuilder<SampleService> builder) {

        SampleService stub = builder
                .ip("127.0.0.1")
                .stubClass(SampleService.class)
                .createStub();

        assertNotNull(stub);
    }

    @ParameterizedTest
    @ArgumentsSource(SimpleStubBuilderProvider.class)
    public void testStubClassMissingLeadsException(StubBuilder<SampleService> builder) {
        assertThrows(StubCreationException.class, () -> {
            SampleService stub = builder
                    .ip("127.0.0.1")
                    .createStub();
        });
    }

    @ParameterizedTest
    @ArgumentsSource(SimpleStubBuilderProvider.class)
    public void testIpMissingLeadsException(StubBuilder<SampleService> builder) {
        assertThrows(StubCreationException.class, () -> {
            SampleService stub = builder
                    .stubClass(SampleService.class)
                    .createStub();
        });
    }
}
