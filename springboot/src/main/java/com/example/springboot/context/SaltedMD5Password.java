package com.example.springboot.context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class SaltedMD5Password {
//    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
//    String passwordToHash = "user";
//    String salt = getSalt();
//
//    String securePassword = getSecurePassword(passwordToHash, salt);
//
//    String regeneratedPassowrdToVerify = getSecurePassword(passwordToHash, salt);
//    System.out.println(securePassword);
//        System.out.println(regeneratedPassowrdToVerify);
//        System.out.println(salt);
//    }

    public String getSecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(salt.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());

            // This bytes[] has bytes in decimal format;
            // Convert it to hexadecimal format
            generatedPassword = byteToHex(bytes);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // Add salt
    public String getSalt() {
        // Always use a SecureRandom generator
        SecureRandom sr = new SecureRandom();

        // Create array for salt
        byte[] salt = new byte[16];

        // Get a random salt
        sr.nextBytes(salt);

        // return salt
        return byteToHex(salt);
    }

    public String byteToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j];
            hexChars[j * 2] = Character.forDigit((v >> 4) & 0xF, 16);
            hexChars[j * 2 + 1] = Character.forDigit((v & 0xF), 16);
        }
        return new String(hexChars);
    }
}
