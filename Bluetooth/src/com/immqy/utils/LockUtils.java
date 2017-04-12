package com.immqy.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 开关锁
 * @author KARL-Dujinyang
 * @author https://github.com/sheep0704
 */
public class LockUtils {
    private int time;
    private String key;

    public LockUtils() {
        time = 0;
    }

    public void createKey(String path) {
        File file = new File(path);
        int len;
        byte[] keyByte = new byte[128];
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            FileInputStream inputStream = new FileInputStream(file);
            while ((len = inputStream.read(keyByte, 0, 64)) != -1) {
                digest.update(keyByte, 0, len);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BigInteger bigInteger = new BigInteger(1, digest.digest());
        key = bigInteger.toString(32);
        while (key.getBytes().length != 64) {
            key += '0';
        }
    }

    public void sendKey() {
        //TODO Send key to PasswordLock

    }

    public void sendKey(int time) {
        //TODO Send key to PasswordLock after time

    }

    public String getKey() {
        return this.key;
    }
}
