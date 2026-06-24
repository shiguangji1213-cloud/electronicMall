package com.electronicmall.util;

import java.security.MessageDigest;

public class MD5Util {

    /**
     * MD5加密
     */
    public static String encrypt(String str) {
        if (str == null || str.trim().length() == 0) return "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) sb.append('0');
                sb.append(hex);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }

    /**
     * 校验密码（输入的明文 vs 数据库里的密文）
     */
    public static boolean verify(String plainText, String encrypted) {
        if (plainText == null || encrypted == null) return false;
        return encrypt(plainText).equals(encrypted);
    }
}