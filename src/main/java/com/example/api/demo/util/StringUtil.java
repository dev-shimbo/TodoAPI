package com.example.api.demo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class StringUtil {

    public static String generateAccessTokenByUserName(String username) {
        // ユーザーネームをSHA-256でハッシュ化
        String hashedUsername = hashSHA256(username);

        // ランダムなバイト列を生成
        byte[] randomBytes = generateRandomBytes(16); // 16バイト = 128ビット

        // ハッシュしたユーザーネームとランダムなバイト列を結合
        String combinedString = hashedUsername + bytesToHex(randomBytes);

        return combinedString;
    }

    public static String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] generateRandomBytes(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
