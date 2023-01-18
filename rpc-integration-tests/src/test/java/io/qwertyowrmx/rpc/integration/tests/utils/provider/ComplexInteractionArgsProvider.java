package io.qwertyowrmx.rpc.integration.tests.utils.provider;

import io.qwertyowrmx.rpc.client.ByteBuddyStubBuilder;
import io.qwertyowrmx.rpc.client.DpgStubBuilder;
import io.qwertyowrmx.rpc.client.JdkStubBuilder;
import io.qwertyowrmx.rpc.integration.tests.utils.OrderService;
import io.qwertyowrmx.rpc.integration.tests.utils.OrderServiceContract;
import io.qwertyowrmx.rpc.integration.tests.utils.PortSequence;
import io.qwertyowrmx.rpc.server.RpcServer;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ComplexInteractionArgsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                generateJdkArgs(),
                generateByteBuddyArgs(),
                generateDpgArgs()
        );
    }

    public Arguments generateJdkArgs() {
        int port = PortSequence.nextPort();
        RpcServer rpcServer = new RpcServer(port, OrderService.class);

        var builder = new JdkStubBuilder<OrderServiceContract>()
                .ip("127.0.0.1")
                .port(port)
                .stubClass(OrderServiceContract.class);
        return arguments(rpcServer, builder);
    }

    public Arguments generateByteBuddyArgs() {
        int port = PortSequence.nextPort();
        RpcServer rpcServer = new RpcServer(port, OrderService.class);

        var builder = new ByteBuddyStubBuilder<OrderServiceContract>()
                .ip("127.0.0.1")
                .port(port)
                .stubClass(OrderServiceContract.class);

        return arguments(rpcServer, builder);
    }

    public Arguments generateDpgArgs() {
        int port = PortSequence.nextPort();
        RpcServer rpcServer = new RpcServer(port, OrderService.class);

        var builder = new DpgStubBuilder<OrderServiceContract>()
                .ip("127.0.0.1")
                .port(port)
                .stubClass(OrderServiceContract.class);

        return arguments(rpcServer, builder);
    }
}
