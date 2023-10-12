package org.example;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.GOST28147Engine;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public class GOST28147 {
    public static void main(String[] args) {
        try {
            byte[] keyData = generateRandomKey(32);
            byte[] ivData = generateRandomIV(8);

            GOST28147Engine engine = new GOST28147Engine();
            KeyParameter key = new KeyParameter(keyData);
            ParametersWithIV parameters = new ParametersWithIV(key, ivData);
            BufferedBlockCipher cipher = new BufferedBlockCipher(new CFBBlockCipher(engine, engine.getBlockSize()));

            encryptFile("input.txt", "output.txt", cipher, parameters);

            decryptFile("output.txt", "decrypted.txt", cipher, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void encryptFile(String inputFile,
                                    String outputFile,
                                    BufferedBlockCipher cipher,
                                    ParametersWithIV parameters) throws Exception {
        cipher.init(true, parameters);
        applyCipherToFile(inputFile, outputFile, cipher);
    }

    private static void decryptFile(String inputFile,
                                    String outputFile,
                                    BufferedBlockCipher cipher,
                                    ParametersWithIV parameters) throws Exception {
        cipher.init(false, parameters);
        applyCipherToFile(inputFile, outputFile, cipher);
    }

    private static void applyCipherToFile(String inputFile,
                                          String outputFile,
                                          BufferedBlockCipher cipher) throws IOException, InvalidCipherTextException {
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            int length = cipher.processBytes(buffer, 0, bytesRead, buffer, 0);
            outputStream.write(buffer, 0, length);
        }

        int length = cipher.doFinal(buffer, 0);
        outputStream.write(buffer, 0, length);
    }

    private static byte[] generateRandomKey(int keyLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyData = new byte[keyLength];
        secureRandom.nextBytes(keyData);
        return keyData;
    }

    private static byte[] generateRandomIV(int ivLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] ivData = new byte[ivLength];
        secureRandom.nextBytes(ivData);
        return ivData;
    }
}