package io.qwertyowrmx.encryption.xor;

import java.io.IOException;
import java.io.OutputStream;

public class XorEncryptionOutputStream extends OutputStream {
    private final OutputStream out;
    private final byte key;

    public XorEncryptionOutputStream(OutputStream out, byte key) {
        this.out = out;
        this.key = key;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b ^ key);
    }
}
