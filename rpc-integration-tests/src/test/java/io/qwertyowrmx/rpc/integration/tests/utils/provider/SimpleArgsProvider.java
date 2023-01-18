package io.qwertyowrmx.rpc.integration.tests.utils.provider;

import io.qwertyowrmx.rpc.client.ByteBuddyStubBuilder;
import io.qwertyowrmx.rpc.client.DpgStubBuilder;
import io.qwertyowrmx.rpc.client.JdkStubBuilder;
import io.qwertyowrmx.rpc.integration.tests.utils.PortSequence;
import io.qwertyowrmx.rpc.integration.tests.utils.SimpleService;
import io.qwertyowrmx.rpc.integration.tests.utils.SimpleServiceContract;
import io.qwertyowrmx.rpc.server.RpcServer;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SimpleArgsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                generateByteBuddyArgs(),
                generateJDKArgs()
//                generateDpgArgs()
//                 TODO: fix return primitives in dynamic proxy generator
        );
    }

    public Arguments generateByteBuddyArgs() {
        int port = PortSequence.nextPort();
        RpcServer rpcServer = new RpcServer(port, SimpleService.class);

        var builder = new ByteBuddyStubBuilder<SimpleServiceContract>()
                .ip("127.0.0.1")
                .port(port)
                .stubClass(SimpleServiceContract.class);

        return arguments(rpcServer, builder);
    }


    public Arguments generateJDKArgs() {
        int port = PortSequence.nextPort();
        RpcServer rpcServer = new RpcServer(port, SimpleService.class);

        var builder = new JdkStubBuilder<SimpleServiceContract>()
                .ip("127.0.0.1")
                .port(port)
                .stubClass(SimpleServiceContract.class);
        return arguments(rpcServer, builder);
    }

    public Arguments generateDpgArgs() {
        int port = PortSequence.nextPort();
        RpcServer rpcServer = new RpcServer(port, SimpleService.class);

        var builder = new DpgStubBuilder<SimpleServiceContract>()
                .ip("127.0.0.1")
                .port(port)
                .stubClass(SimpleServiceContract.class);

        return arguments(rpcServer, builder);
    }
}
