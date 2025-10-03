package com.demo.alexandino.clientes.utils;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AesGcmAlgorithm {
    private static final String AES_ALGORITHM_GCM = "AES/GCM/NoPadding";

    private static final Integer IV_LENGTH = 12;
    private static final Integer TAG_LENGTH = 16;

    @Value("${app.secret.key}")
    private String LOCAL_PASSPHRASE;

    private SecretKeySpec aesKey;

    public String encrypt(String plainText) throws Exception {
        byte[] iv = generateRandomIv();
        aesKey = generateAesKeyFromPassphrase();
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM_GCM);
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, new GCMParameterSpec(TAG_LENGTH * 8, iv));

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(combine(iv, encryptedBytes));
    }

    public String decrypt(String cipherText) throws Exception {
        byte[] decodedCipherText = Base64.getDecoder().decode(cipherText);

        byte[] iv = extractIv(decodedCipherText);
        byte[] encryptedText = extractEncryptedData(decodedCipherText);

        Cipher cipher = Cipher.getInstance(AES_ALGORITHM_GCM);
        cipher.init(Cipher.DECRYPT_MODE, aesKey, new GCMParameterSpec(TAG_LENGTH * 8, iv));

        byte[] decryptedBytes = cipher.doFinal(encryptedText);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private byte[] generateRandomIv() {
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    private byte[] combine(byte[] iv, byte[] encryptedBytes) {
        byte[] combined = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);
        return combined;
    }

    private byte[] extractIv(byte[] decodedCipherText) {
        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(decodedCipherText, 0, iv, 0, IV_LENGTH);
        return iv;
    }

    private byte[] extractEncryptedData(byte[] decodedCipherText) {
        byte[] encryptedData = new byte[decodedCipherText.length - IV_LENGTH];
        System.arraycopy(decodedCipherText, IV_LENGTH, encryptedData, 0, encryptedData.length);
        return encryptedData;
    }
    private SecretKeySpec generateAesKeyFromPassphrase() {
        byte[] keyBytes = LOCAL_PASSPHRASE.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, 0, 16, "AES");
    }
}
