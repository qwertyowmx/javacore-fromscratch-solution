package io.qwertyowrmx.compressor;

import java.io.FilterOutputStream;
import java.io.OutputStream;

public class HuffmanDecompressor extends FilterOutputStream {
    /**
     * Creates an output stream filter built on top of the specified
     * underlying output stream.
     *
     * @param out the underlying output stream to be assigned to
     *            the field {@code this.out} for later use, or
     *            {@code null} if this instance is to be
     *            created without an underlying stream.
     */
    public HuffmanDecompressor(OutputStream out) {
        super(out);
    }
}
