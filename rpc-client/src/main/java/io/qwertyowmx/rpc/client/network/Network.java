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

package io.qwertyowmx.rpc.client.network;


import io.qwertyowmx.rpc.common.descriptor.RemoteMethodDescriptor;
import io.qwertyowmx.rpc.common.exceptions.NetworkException;
import io.qwertyowmx.serialization.jdk.ObjectIOStreamSerializer;
import io.qwertyowmx.serialization.jdk.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Network {
    protected String ip;
    protected int port;
    protected Serializer serializer;

    public Network(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.serializer = new ObjectIOStreamSerializer();
    }

    public Object communicate(RemoteMethodDescriptor descriptor) {
        try (Socket socket = new Socket(ip, port)) {
            serializer.serialize(descriptor, createOutputStream(socket));
            return serializer.deserialize(createInputStream(socket), descriptor.getReturnType());
        } catch (IOException e) {
            throw new NetworkException("I/O error", e);
        }
    }

    private OutputStream createOutputStream(Socket socket) throws IOException {
        return socket.getOutputStream();
    }

    private InputStream createInputStream(Socket socket) throws IOException {
        return socket.getInputStream();
    }
}
