package com.stone.saveimage2systemalbum.album;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author stone
 * @date 17/3/29
 */

public class MD5Utils {
    private static final String HASH_ALGORITHM = "MD5";
    private static final int RADIX = 10 + 26; // 10 digits + 26 letters

    private MD5Utils() {
        //no instance
    }

    /**
     * 根据给定的url, 计算其MD5值
     * @param imageUri
     * @return
     */
    public static String getMD5StringFrom(String imageUri) {
        byte[] md5 = calcMD5With(imageUri.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        return bi.toString(RADIX);
    }

    private static byte[] calcMD5With(byte[] src) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(src);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5FileNameGenerator", "getMD5(): " + (e == null ? "" : e.getMessage()));
        }
        return hash;
    }
}
