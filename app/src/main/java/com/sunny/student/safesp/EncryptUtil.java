package com.sunny.student.safesp;

import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * created by sunshuo
 * on 2020/6/2
 * 加密工具类
 */
class EncryptUtil {
    private String mKey; //秘钥
    private EncryptUtil() {
        //生成秘钥
        mKey = SHA();
    }

    private static class EncryptUtilHolder{
        private static final EncryptUtil instance = new EncryptUtil();

    }
    public static EncryptUtil getInstance() {
        return EncryptUtilHolder.instance;
    }

    /**
     * 生成秘钥
     */

    private String SHA() {
        String key = "bjhl";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update("bjhl".getBytes());
            byte[] byteBuffer = messageDigest.digest();
            StringBuilder strHexString = new StringBuilder();
            for (byte b : byteBuffer) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    strHexString.append('0');
                }
                strHexString.append(hex);
            }
            key = strHexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * AES 加密
     * @param plainText 明文
     * @return 加密后
     */
    @Nullable
    public String encrypt(@NonNull String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(mKey.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            return Base64.encodeToString(encrypted, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * AES128解密
     * @param cipherText 密文
     * @return
     */
    public String decrypt(String cipherText) {
        try {
            byte[] encrypted1 = Base64.decode(cipherText, Base64.NO_WRAP);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(mKey.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
