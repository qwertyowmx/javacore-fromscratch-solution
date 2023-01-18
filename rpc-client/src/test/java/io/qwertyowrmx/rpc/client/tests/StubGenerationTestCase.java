package io.qwertyowrmx.rpc.client.tests;


import io.qwertyowrmx.rpc.client.StubBuilder;
import io.qwertyowrmx.rpc.client.exceptions.StubCreationException;
import io.qwertyowrmx.rpc.client.tests.parameters.SimpleStubBuilderProvider;
import io.qwertyowrmx.rpc.client.tests.service.SampleService;
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
