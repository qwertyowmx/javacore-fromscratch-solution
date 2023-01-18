package io.qwertyowrmx.serialization.jdk;


import java.io.*;

public class ObjectIOStreamSerializer implements Serializer {

    @Override
    public void serialize(Object object, OutputStream stream) {
        try {
            var objectOutputStream = new ObjectOutputStream(stream);
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            throw new SerializationException("Unable to open ObjectOutputStream", e);
        }
    }

    @Override
    public Object deserialize(InputStream inputStream, Class<?> aClass) {
        try {
            var objectOutputStream = new ObjectInputStream(inputStream);
            return objectOutputStream.readObject();
        } catch (IOException e) {
            throw new SerializationException("Unable to open ObjectInputStream", e);
        } catch (ClassNotFoundException e) {
            throw new SerializationException("Unable to locate class", e);
        }
    }
}
