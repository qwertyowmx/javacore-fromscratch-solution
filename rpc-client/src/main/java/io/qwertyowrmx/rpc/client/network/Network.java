package io.qwertyowrmx.rpc.client.network;


import io.qwertyowrmx.rpc.common.descriptor.RemoteMethodDescriptor;
import io.qwertyowrmx.rpc.common.exceptions.NetworkException;
import io.qwertyowrmx.serialization.jdk.ObjectIOStreamSerializer;
import io.qwertyowrmx.serialization.jdk.Serializer;

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
