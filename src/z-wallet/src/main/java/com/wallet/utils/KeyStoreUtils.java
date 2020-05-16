package com.wallet.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyStoreUtils {

    public static PrivateKey readPrivateKey() {
        try {
            File file = new File(KeyStoreUtils.class.getClassLoader().getResource("pv.key").getFile());
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String keyStr = bufferedReader.readLine();

            PKCS8EncodedKeySpec X509privateKey = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(keyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(X509privateKey);

            return privateKey;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static SecretKey readSecretKey() {
        try {
            File file = new File(KeyStoreUtils.class.getClassLoader().getResource("share.key").getFile());
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String keyStr = bufferedReader.readLine();

            SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(keyStr), "AES");
            return skeySpec;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
