package com.redescooter.ses.tool.crypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

/**
 * 加密解密工具包
 */
public class DES {

    private static final byte[] IV_PARAMS_BYTES = "jeesuite".getBytes();
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception
     */
    public static String encrypt(String key, String data) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV_PARAMS_BYTES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return byte2hex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decrypt(String key, String data) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV_PARAMS_BYTES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return new String(cipher.doFinal(hex2byte(data.getBytes())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 二行制转字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

	public static void main(String[] args) {

		String key = "RedEScooter20190";
		String data = "{\\\"scooterModel\\\":3,\\\"specificDefGroupList\\\":[{\\\"controllerUndervoltage\\\":\\\"58\\\",\\\"controllerUndervoltageRecovery\\\":\\\"62\\\",\\\"limitSpeedBos\\\":\\\"75\\\",\\\"limiting\\\":\\\"50\\\",\\\"orangeWarning\\\":\\\"5%\\\",\\\"setSOCTo0AtStallUndervoltage\\\":\\\" 62\\\",\\\"socRedWarning\\\":\\\"20%\\\",\\\"speedLimit\\\":\\\"80\\\",\\\"speedRatio\\\":\\\"0.169230\\\",\\\"stallSOC\\\":\\\"0%\\\",\\\"stallVoltageUndervoltage\\\":\\\"58\\\",\\\"voltageLegalRecognitionMax\\\":\\\"50\\\",\\\"voltageLegalRecognitionMin\\\":\\\"84\\\",\\\"wheelDiameter\\\":\\\"52\\\"}],\\\"tabletSn\\\":\\\"2012018100000000122\\\"}";

		System.out.println("原始字符串是" + data);

		String encrypt = encrypt(key, data);
		System.out.println("加密后的字符串是" + encrypt);

		String decrypt = decrypt(key, encrypt);
		System.out.println("解密后的字符串是" + decrypt);
	}

}