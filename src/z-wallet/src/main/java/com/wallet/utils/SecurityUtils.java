package com.wallet.utils;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class SecurityUtils {

    public static Key[] generateRSAKeyPair() {

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);

            KeyPair pair = generator.generateKeyPair();

            PublicKey publicKey = pair.getPublic();
            PrivateKey privateKey = pair.getPrivate();

            Key[] result = new Key[2];
            result[0] = publicKey;
            result[1] = privateKey;

            return result;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static SecretKey generateAESKey() {

        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256);
            //generator.init(SecureRandom.getInstance("NativePRNG"));

            SecretKey secretKey = generator.generateKey();

            return secretKey;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String encryptHmacSha256(SecretKey secretKey, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            byte[] encryptedData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(encryptedData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String encryptRSA(PublicKey publicKey, String data) {

        try {
            byte[] rawData = data.getBytes(StandardCharsets.UTF_8);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedData = cipher.doFinal(rawData);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(encryptedData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decryptRSA(PrivateKey privateKey, String encryptedDataBase64) {

        try {
            byte[] encryptedData = Base64.getDecoder().decode(encryptedDataBase64);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] rawOutput = cipher.doFinal(encryptedData);
            return new String(rawOutput, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String encryptAES(SecretKey secretKey, String data) {
        try {
            byte[] rawData = data.getBytes(StandardCharsets.UTF_8);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedData = cipher.doFinal(rawData);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(encryptedData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decryptAES(SecretKey secretKey, String encryptedDataBase64) {
        try {
            byte[] encryptedData = Base64.getDecoder().decode(encryptedDataBase64);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] rawOutput = cipher.doFinal(encryptedData);
            return new String(rawOutput, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
