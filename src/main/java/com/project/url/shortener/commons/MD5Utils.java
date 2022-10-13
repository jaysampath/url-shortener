package com.project.url.shortener.commons;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    private static final String MD5_ALGORITHM = "MD5";

    public static String getMd5Hash(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(MD5_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(result);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
