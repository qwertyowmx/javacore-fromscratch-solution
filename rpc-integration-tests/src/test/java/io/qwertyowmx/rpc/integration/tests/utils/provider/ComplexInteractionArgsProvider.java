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

package io.qwertyowmx.rpc.integration.tests.utils.provider;

import io.qwertyowmx.rpc.client.ByteBuddyStubBuilder;
import io.qwertyowmx.rpc.client.DpgStubBuilder;
import io.qwertyowmx.rpc.client.JdkStubBuilder;
import io.qwertyowmx.rpc.integration.tests.utils.OrderService;
import io.qwertyowmx.rpc.integration.tests.utils.OrderServiceContract;
import io.qwertyowmx.rpc.integration.tests.utils.PortSequence;
import io.qwertyowmx.rpc.server.RpcServer;
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
