package org.example;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String input1 = "Example";
        String input2 = "Example.";
        String sha256Hash1 = sha256(input1);
        String sha256Hash2 = sha256(input2);

        System.out.println("Входная строка 1: " + input1);
        System.out.println("Входная строка 2: " + input2);
        System.out.println("SHA-256 Хэш 1: " + sha256Hash1);
        System.out.println("SHA-256 Хэш 2: " + sha256Hash2);
    }

    public static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes());
        byte[] digest = md.digest();

        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder hashText = new StringBuilder(bigInt.toString(16));

        while (hashText.length() < 64) {
            hashText.insert(0, "0");
        }

        return hashText.toString();
    }
}
