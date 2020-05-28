package com.redescooter.ses.tool.utils.accountType;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
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

    public  static final int DEFAULT_RSA_KEY_SIZE = 2048;

    public static final String KEY_ALGORITHM = "RSA";

    public static final String PUBLIC_KEY ="publicKey";

    public static final String PRIVATE_KEY ="privateKey";

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
//
//        //64位解码加密后的字符串
//        byte[] inputByte;
//        String outStr = "";
//        try {
//            inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
//            //base64编码的私钥
//            byte[] decoded = Base64.decodeBase64(privateKey);
//            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
//            //RSA解密
//            Cipher cipher = Cipher.getInstance("RSA");
//            cipher.init(Cipher.DECRYPT_MODE, priKey);
//            outStr = new String(cipher.doFinal(inputByte));
//        } catch (UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return outStr;
    }
}
