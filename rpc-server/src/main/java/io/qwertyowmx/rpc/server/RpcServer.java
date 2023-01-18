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

package io.qwertyowmx.rpc.server;

import io.qwertyowmx.rpc.server.handler.RpcRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class RpcServer {
    private final int port;
    private final Class<?> serviceClass;
    private final AtomicBoolean serverRunning = new AtomicBoolean(false);
    private ExecutorService executor;

    public RpcServer(int port, Class<?> serviceClass) {
        this.port = port;
        this.serviceClass = serviceClass;
        this.executor = Executors.newCachedThreadPool();
    }

    private static Object constructServiceInstance(Class<?> serviceClass) {
        try {
            return ConstructorUtils.invokeConstructor(serviceClass);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException |
                 NoSuchMethodException e) {
            throw new RPCServerException("Unable to instantiate service", e);
        }
    }

    public void start() {
        String serverName = this.getClass().getSimpleName();
        LOG.info("Starting {} on port {}", serverName, port);
        ServerSocket serverSocket = null;
        try {
            serverSocket = createServerSocket(port);
            serverRunning.set(true);

            var instance = constructServiceInstance(serviceClass);
            while (serverRunning.get()) {
                Socket socket = serverSocket.accept();
                executor.execute(new RpcRequestHandler(socket, instance));
            }

            LOG.info("Server successfully stopped");
        } catch (IOException e) {
            throw new RPCServerException("Error occurred on server side", e);
        } finally {
            IOUtils.closeQuietly(serverSocket);
        }
    }

    private ServerSocket createServerSocket(int port) {
        try {
            return new ServerSocket(port);
        } catch (IOException e) {
            throw new RPCServerException("Unable to create server on port " + port, e);
        }
    }

    public boolean isRunning() {
        return serverRunning.get();
    }

    public RpcServer threadPool(ExecutorService service) {
        this.executor = service;
        return this;
    }

    public void shutdown() {
        LOG.info("Shutting down server...");
        serverRunning.set(false);
        LOG.info("Shutting down executor...");
        executor.shutdownNow();
    }
}
