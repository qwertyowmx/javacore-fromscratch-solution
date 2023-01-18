package io.qwertyowrmx.rpc.client.tests.parameters;

import io.qwertyowrmx.rpc.client.ByteBuddyStubBuilder;
import io.qwertyowrmx.rpc.client.DpgStubBuilder;
import io.qwertyowrmx.rpc.client.JdkStubBuilder;
import io.qwertyowrmx.rpc.client.tests.service.SampleService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SimpleStubBuilderProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                arguments(new ByteBuddyStubBuilder<SampleService>()),
                arguments(new JdkStubBuilder<SampleService>()),
                arguments(new DpgStubBuilder<SampleService>())
        );
    }
}
