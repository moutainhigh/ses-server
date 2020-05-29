package com.redescooter.ses.tool.utils.accountType;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:RsaUtils
 * @description: RsaUtils
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/26 13:37
 */
public class RsaUtils {

    public static final int DEFAULT_RSA_KEY_SIZE = 2048;

    public final static String UTF8 = "utf-8";

    public static final String KEY_ALGORITHM = "RSA";

    public static final String PUBLIC_KEY = "publicKey";

    public static final String PRIVATE_KEY = "privateKey";

    private static final String publicKey_sa="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvu1hqArIwp8DGBYBwPmPMrOOt1iIb8yTVe8vFR+gwgHKZ7rsPGKFYtffbY5+hDKP3HToouM/d+BFrGDMtPjshNbZT4Ti04aK96LZBi6q" +
            "/VGhR8csyE0MjrGW62HpXtCRblTeumq6E8E+KVmJPYxuyGll9YrfN3k2mWNEQVNvhbHwLwxcQFmuQHB/wnEvs/ZvBFUpoaXrWZC5d7/bdAh/3qLC1AHFZITos82TpW9" +
            "/xDYMiIBoRH8uf5ymbWaJvATIyS7E7IgirwlHzTSZYRFGnRoOybpkYflDo41qq8UsjXvmErJ3y0GmJMVNzWRay8gFqO8vuKJQ3SWTDVnhPMO2TQIDAQAB\n";

    private static final String privateKey_sa="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC+7WGoCsjCnwMYFgHA+Y8ys463WIhvzJNV7y8VH6DCAcpnuuw8YoVi199tjn6EMo/cdOii4z934EWsYMy0" +
            "+OyE1tlPhOLThor3otkGLqr9UaFHxyzITQyOsZbrYele0JFuVN66aroTwT4pWYk9jG7IaWX1it83eTaZY0RBU2+FsfAvDFxAWa5AcH/CcS+z9m8EVSmhpetZkLl3v9t0CH/eosLUAcVkhOizzZOlb3/ENgyIgGhEfy5" +
            "/nKZtZom8BMjJLsTsiCKvCUfNNJlhEUadGg7JumRh+UOjjWqrxSyNe+YSsnfLQaYkxU3NZFrLyAWo7y" +
            "+4olDdJZMNWeE8w7ZNAgMBAAECggEBALDzyYK5HABk57U3EEEOCs3u5OLAxxL3A8ate3GUcqXciv9AmxoMJZvKf2VGn7NeOT8Vi1cMTxKHZYux24gO/QbRKW8tthxdogaJg6LbNda6BURFMRgjcPP/W" +
            "+SkRmp924vqVNNpfzQ38ajZNv04XqSYKss7/k2nphbbjbMN5Vp+SjQ8jHr4iN5z84NRLZbE8RRMn20s9rNorumdcJGFuWqUjryUMyFF/cwOtEwBIgLGmOiVsEqFOgIpWreUTibJqmN4M020B+fL+Cf9qGpxqWkdcPovMI8H889JN" +
            "/8jMvDmGR1KDP2qceQE3HbsBot2KwnbWgzAVG8sWxzyeY86CoECgYEA4ndk2Ky7RYWAsmzUeZpzD1ftdWOIWDGWfZw8q+qcc/MaJQqaLKwv0yvjng9xDdNK9EQjz1FAAOSulirvdPpDPUHVt923QRIT3dl6nD/GgbdRUpMw01N8ci" +
            "+2KCYRWEmE7tnEEUzfyeUZGjVei5ilPn5U17ULSdy1wBrRrq+i0ykCgYEA19OCn7/nBTejK7rHTz6kbzHz763R5X4g1J8oGwYqxiPzmKx6xCRXcsBJlKMAurh9VC" +
            "+7hn0tuiRqaO7EAA7is4NKdN2xP12FDPFKjQCLK44908QqvdL1CvGrkKTpUC1EugLI1FiwQV+59MvCll7/0bybPNqp68blBC+wWCw+MoUCgYEApP0a6YiePv4OTu9etOFDJlCG3VkQQkCORSEISq94IDliOM5KmHUxuoVlJQhW8x2Wo/Loq" +
            "+ej24K/dwP8xJp3Vi+ahoPvOGb1NlF1NFR6SJLHmoNFtRBDv+TmrsVdKZcWU/UvFPOOrsZLzX5ITSd+a9MZ+LUbbkpv128yiDPJSlECgYBl8AagQVlyQ2cUPnHo4eN0iZl3uReMV1hWVJ3ytaT6VPy3Pa/2eFu9XUjjpWGwz3aSjsD5sqIoaw6" +
            "/ZRa6GE3HD9nY/OS80wUGqejWVE1nUxWssdimM799Xcv0049HSTpOyQXmFcWZ+wavspXDxDb77yM7SOQ88oI0yfl2i98MqQKBgA+uhYSVt4sBW1kiiJshXKlZTeoM8TKSJqkXxE1oAa1aBJI+jwDWe" +
            "+JBOXs5ExeoMpzYDgTRZ5UwQM0HfwuWSgHF69asz3PAKST4eLUoHP9pvaqRxONHKO9fdRcLAH/0HVsTzk7tCOQa4/GB5T4SKXPDPn3M/JiCXZ00npMvmPeW\n";

    /**
     * 生成RSA 公私钥,可选长度为1025,2048位.
     */
    public static Map<String, String> generateRsaKey(int keySize) {
        if (keySize == 0) {
            keySize = DEFAULT_RSA_KEY_SIZE;
        }
        Map<String, String> result = new HashMap<>(2);
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

            // 初始化密钥对生成器，密钥大小为1024 2048位
            keyPairGen.initialize(keySize, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到公钥字符串
            result.put(PUBLIC_KEY, new String(Base64.encodeBase64(keyPair.getPublic().getEncoded())));
            // 得到私钥字符串
            result.put(PRIVATE_KEY, new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded())));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    @SneakyThrows
    public static String decrypt(String str, String privateKey) {

        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    /**
     * 私钥加密
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     */
    public static String encryptByPrivateKey(String data, String privateKey) {
        try {
            privateKey = privateKey.replaceAll("\\r", "").replaceAll("\\n", "");
            byte[] kb = Base64.decodeBase64(privateKey.getBytes(UTF8));
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(kb);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] b = data.getBytes(UTF8);
            byte[] encrypt = cipher.doFinal(b);
            return Base64.encodeBase64String(encrypt);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 公钥加密
     *
     * @param str
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        publicKey = publicKey.replaceAll("\\r", "").replaceAll("\\n", "");
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(UTF8)));
        return outStr;
    }

    /**
     * 公钥解密
     *
     * @param data      解密数据
     * @param publicKey 公钥
     * @return
     */
    public static String decryptByPublicKey(String data, String publicKey) {
        try {
            publicKey = publicKey.replaceAll("\\r", "").replaceAll("\\n", "");
            byte[] kb = Base64.decodeBase64(publicKey.getBytes(UTF8));
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(kb);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey key = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            //Cipher cipher = Cipher.getInstance(RSA_PADDING_KEY);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] b = data.getBytes(UTF8);
            byte[] decrypt = cipher.doFinal(Base64.decodeBase64(b));
            return new String(decrypt, UTF8);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @SneakyThrows
    public static void main(String[] args) {

        Map<String, String> stringStringMap = generateRsaKey(2048);
        System.out.println(stringStringMap.get(PRIVATE_KEY));

        System.out.println(stringStringMap.get(PUBLIC_KEY));

        String password = "RedeScooter2019";

        //公钥加密
        String encryptPwd = encrypt(password, stringStringMap.get(PUBLIC_KEY));
        System.out.println(encryptPwd);

        //私钥解密
        String decryptPwd = decrypt(encryptPwd, stringStringMap.get(PRIVATE_KEY));
        System.out.println(decryptPwd);

        //私钥加密
        String encryptByPrivateKeyPwd = encryptByPrivateKey(password, privateKey_sa);
        System.out.println(encryptByPrivateKeyPwd);

        //公钥解密
        String decryptByPublicKey = decryptByPublicKey(encryptByPrivateKeyPwd, publicKey_sa);
        System.out.println(decryptByPublicKey);
    }

}
