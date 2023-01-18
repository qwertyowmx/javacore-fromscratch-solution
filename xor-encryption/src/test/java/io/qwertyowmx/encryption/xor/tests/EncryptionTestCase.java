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

package io.qwertyowmx.encryption.xor.tests;

import io.qwertyowmx.encryption.xor.XorEncryptionInputStream;
import io.qwertyowmx.encryption.xor.XorEncryptionOutputStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

@Slf4j
public class EncryptionTestCase {
    private static String encrypt(byte key, String message) throws IOException {
        try (var byteStream = new ByteArrayOutputStream();
             var writer = new BufferedWriter(
                     new OutputStreamWriter(new XorEncryptionOutputStream(byteStream, key)))) {
            writer.write(message);
            writer.flush();
            return byteStream.toString();
        }
    }

    private static String decrypt(byte key, String encrypted) throws IOException {
        try (var in = new ByteArrayInputStream(encrypted.getBytes());
             var streamReader = new XorEncryptionInputStream(in, key)) {
            return new String(streamReader.readAllBytes());
        }
    }

    @Test
    @SneakyThrows
    public void testEncryptMessage() {
        byte key = 0xA;
        String targetMessage = encrypt(key, "HELLO WORLD");
        Assertions.assertNotEquals("HELLO WORLD", targetMessage);
    }

    @Test
    @SneakyThrows
    public void testEncryptDecryptMessage() {
        byte key = 0x15;
        String secretMessage = "SECRET MESSAGE";
        LOG.info("Source string: {}", secretMessage);

        String encrypted = encrypt(key, secretMessage);
        LOG.info("Encrypted string: {}", encrypted);

        String decrypted = decrypt(key, encrypted);
        LOG.info("Decrypted string: {}", decrypted);

        Assertions.assertEquals(secretMessage, decrypted);
    }

}
