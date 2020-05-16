package com.wallet.wallet.topic;

import com.wallet.utils.SecurityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@RunWith(SpringRunner.class)
public class SecurityTests {

    @Test
    public void testGenkeys() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Key[] keys = SecurityUtils.generateRSAKeyPair();
        String publicKeyBase64 = Base64.getEncoder().encodeToString(keys[0].getEncoded());
        System.out.println("publicKeyBase64: " + publicKeyBase64);

        String privateKeyBase64 = Base64.getEncoder().encodeToString(keys[1].getEncoded());
        System.out.println("privateKeyBase64: " + privateKeyBase64);

        SecretKey secretKey = SecurityUtils.generateAESKey();
        String secretKeyBase64 = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("secretKeyBase64: " + secretKeyBase64);
    }


    @Test
    public void testRSA() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Key[] keys = SecurityUtils.generateRSAKeyPair();
        String publicKeyBase64 = Base64.getEncoder().encodeToString(keys[0].getEncoded());
        System.out.println("publicKeyBase64: " + publicKeyBase64);

        String password = "123456";

        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(X509publicKey);

        String encryptedData = SecurityUtils.encryptRSA(publicKey, password);
        System.out.println(encryptedData);

        PKCS8EncodedKeySpec X509privateKey = new PKCS8EncodedKeySpec(keys[1].getEncoded());
        PrivateKey privateKey = keyFactory.generatePrivate(X509privateKey);
        String rawData = SecurityUtils.decryptRSA(privateKey, encryptedData);
        System.out.println("Raw data: " + rawData);
    }

    @Test
    public void testHmac() {
        SecretKey shareKey = SecurityUtils.generateAESKey();
        String hmac1 =  SecurityUtils.encryptHmacSha256(shareKey, "123");
        System.out.println("hmac1: " + hmac1);

        String keyStr = Base64.getEncoder().encodeToString(shareKey.getEncoded());
        SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(keyStr), "AES");
        String hmac2 =  SecurityUtils.encryptHmacSha256(skeySpec, "123");
        System.out.println("hmac2: " + hmac2);
    }

    @Test
    public void testAES() {
        SecretKey shareKey = SecurityUtils.generateAESKey();
        String encryptedDara = SecurityUtils.encryptAES(shareKey, "123");


        String keyStr = Base64.getEncoder().encodeToString(shareKey.getEncoded());
        SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(keyStr), "AES");

        String rawData = SecurityUtils.decryptAES(skeySpec, encryptedDara);
        System.out.println("rawData: " + rawData);
    }

    @Test
    public void genHmacPasswordAndKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rawPassword = "123456";

        String shareKey = "u2A1qv5uSlMJXs0RJgArphLnDYtrVRF/lUg091gxFfU=";
        SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(shareKey), "AES");
        String passwordHmac = SecurityUtils.encryptHmacSha256(skeySpec, rawPassword);

        SecretKey clientKey = SecurityUtils.generateAESKey();
        String passwordBeSend = SecurityUtils.encryptAES(clientKey, passwordHmac); // SEND

        String clientKeyBase64 = Base64.getEncoder().encodeToString(clientKey.getEncoded());

        String publicKeyBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1sdnujl1b22C8enVeTC+bHYhkALy9ZgK90t63+9rxmQsv7lXQl66GrVP1kkl3IQ/OmAjvWj9B0T/teJrhkOmQL1DaF75oWii2EV5jh41K07I3/qKjvS+BG4Ujtr1ps33PIGP558Fg8go98HqZZYeYqahPGQE89lFB9PSvux1+BUxJoy55daVVrLeTIxczRIKAnplNHJClHJRiASYycFCJwpgm2xF9GZ3YStfq2wYO1WVPB8Dm4gvmkpbQj9JpHH0U9AfXuSRWfxm94zOkwyCk3e2QiRqj9WUaj2MUXb0fuSjnrZxQYVIQlLFvG64B1jHVaD1uZC8jycNZ7VhXrsoQQIDAQAB";
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(X509publicKey);

        String clientKeyBeSend = SecurityUtils.encryptRSA(publicKey, clientKeyBase64); // SEND



        System.out.println("passwordHmac: " + passwordHmac);
        System.out.println("passwordBeSend: " + passwordBeSend);
        System.out.println("clientKeyBeSend: " + clientKeyBeSend);
    }
}
