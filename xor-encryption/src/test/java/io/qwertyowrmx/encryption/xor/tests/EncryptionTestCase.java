package io.qwertyowrmx.encryption.xor.tests;

import io.qwertyowrmx.encryption.xor.XorEncryptionInputStream;
import io.qwertyowrmx.encryption.xor.XorEncryptionOutputStream;
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
