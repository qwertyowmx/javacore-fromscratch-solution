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

package io.qwertyowmx.rpc.server.handler;

import io.qwertyowmx.rpc.common.descriptor.RemoteMethodDescriptor;
import io.qwertyowmx.rpc.server.RPCServerException;
import io.qwertyowmx.serialization.jdk.ObjectIOStreamSerializer;
import io.qwertyowmx.serialization.jdk.Serializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.io.IOException;
import java.net.Socket;

@Slf4j
public class RpcRequestHandler implements Runnable {
    private final Serializer serializer;
    private final Socket socket;
    private final Object serviceInstance;

    public RpcRequestHandler(Socket socket, Object serviceInstance) {
        this.serviceInstance = serviceInstance;
        this.serializer = new ObjectIOStreamSerializer();
        this.socket = socket;
    }

    public void run() {
        try {
            LOG.info("Handling socket {}", socket);
            RemoteMethodDescriptor descriptor = deserializeDescriptor();
            LOG.info("Received descriptor {}", descriptor);
            var ret = MethodUtils.invokeMethod(serviceInstance, descriptor.getMethodName(), descriptor.getArgs());
            serializer.serialize(ret, socket.getOutputStream());
        } catch (Exception e) {
            throw new RPCServerException("Unable to execute request", e);
        } finally {
            closeSocket();
        }
    }

    private RemoteMethodDescriptor deserializeDescriptor() throws IOException {
        return (RemoteMethodDescriptor) serializer.deserialize(socket.getInputStream(), RemoteMethodDescriptor.class);
    }

    private void closeSocket() {
        try {
            LOG.info("Closing socket {}", socket);
            socket.close();
            LOG.info("Socket closed");
        } catch (IOException e) {
            LOG.error("Unable to close socket {}", socket, e);
        }
    }
}
