package io.qwertyowrmx.rpc.server.handler;

import io.qwertyowrmx.rpc.common.descriptor.RemoteMethodDescriptor;
import io.qwertyowrmx.rpc.server.RPCServerException;
import io.qwertyowrmx.serialization.jdk.ObjectIOStreamSerializer;
import io.qwertyowrmx.serialization.jdk.Serializer;
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
