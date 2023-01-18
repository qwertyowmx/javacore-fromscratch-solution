package io.qwertyowrmx.compressor;

import java.io.FilterInputStream;
import java.io.InputStream;

public class HuffmanCompressor extends FilterInputStream {

    /**
     * Creates a {@code FilterInputStream}
     * by assigning the  argument {@code in}
     * to the field {@code this.in} so as
     * to remember it for later use.
     *
     * @param in the underlying input stream, or {@code null} if
     *           this instance is to be created without an underlying stream.
     */
    protected HuffmanCompressor(InputStream in) {
        super(in);
    }
}
