package org.example;

public class VernamCipher {

    public static final String secretKey = "фимсмисми";

    public static void main(String[] args) {
        String message = "Кириллица";
        System.out.println("Текст: " + message);
        System.out.println("Зашифрованный текст: " + apply(message));
        System.out.println("Расшифрованный текст: " + apply(apply(message)));
    }

    public static String apply(String message) {
        if (message.length() > secretKey.length()) {
            throw new IllegalArgumentException("Key can't be shorter than message");
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            var msgChar = message.charAt(i);
            var keyChar = secretKey.charAt(i);
            builder.append((char) (msgChar ^ keyChar));
        }

        return builder.toString();
    }
}
