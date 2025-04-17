package com.cyperlo.util;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * RSA 加密解密工具类（基于 Hutool）
 */
public class RSAUtil {
    
    /**
     * 生成RSA密钥对
     *
     * @return 包含公钥和私钥的字符串数组，[0]为公钥，[1]为私钥
     */
    public static String[] generateKeyPair() {
        RSA rsa = new RSA();
        return new String[]{rsa.getPublicKeyBase64(), rsa.getPrivateKeyBase64()};
    }

    /**
     * 使用公钥加密
     *
     * @param content 要加密的内容
     * @param publicKey 公钥字符串
     * @return 加密后的内容
     */
    public static String encrypt(String content, String publicKey) {
        RSA rsa = new RSA(null, publicKey);
        return rsa.encryptBase64(content, KeyType.PublicKey);
    }

    /**
     * 使用私钥解密
     *
     * @param content 要解密的内容
     * @param privateKey 私钥字符串
     * @return 解密后的内容
     */
    public static String decrypt(String content, String privateKey) {
        RSA rsa = new RSA(privateKey, null);
        return rsa.decryptStr(content, KeyType.PrivateKey);
    }

    /**
     * 使用私钥签名
     *
     * @param content 要签名的内容
     * @param privateKey 私钥字符串
     * @return 签名后的内容
     */
    public static String sign(String content, String privateKey) {
        RSA rsa = new RSA(privateKey, null);
        return rsa.sign(content);
    }

    /**
     * 使用公钥验证签名
     *
     * @param content 原始内容
     * @param sign 签名内容
     * @param publicKey 公钥字符串
     * @return 验证是否通过
     */
    public static boolean verify(String content, String sign, String publicKey) {
        RSA rsa = new RSA(null, publicKey);
        return rsa.verify(content.getBytes(), sign.getBytes());
    }
} 