package com.ryanyee.Crawer_bilibili.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class buvidUtils {

    // 生成随机的 MAC 地址
    public static String createRandomMac(String sep) {
        StringBuilder mac = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            // 随机选择两个字符作为 MAC 地址的一部分
            String part = String.format("%02X", random.nextInt(256));
            mac.append(part);

            if (i != 5) {
                mac.append(sep); // 在每个部分之间加上分隔符
            }
        }

        return mac.toString();
    }

    // 通过 MAC 地址生成 BUVID
    public static String getBuvidByWifiMac() {
        String mac = createRandomMac(":");
        String md5Hex = md5(mac);
        return String.format("XY%s%s%s%s", md5Hex.charAt(2), md5Hex.charAt(12), md5Hex.charAt(22), md5Hex.toUpperCase());
    }

    // 使用 MD5 哈希算法
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                hexString.append(String.format("%02X", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
}
