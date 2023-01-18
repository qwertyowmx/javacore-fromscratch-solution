package io.qwertyowrmx.encryption.xor;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;

public class XorEncryptionInputStream extends InputStream {
    private final InputStream in;
    @Getter
    private final byte key;

    public XorEncryptionInputStream(InputStream in, byte key) {
        this.in = in;
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int read = in.read();
        if (read == -1) {
            return read;
        }
        return read ^ key;
    }
}
