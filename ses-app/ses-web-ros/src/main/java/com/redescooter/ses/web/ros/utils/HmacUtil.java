package com.redescooter.ses.web.ros.utils;

import com.google.common.collect.Maps;
import com.redescooter.ses.tool.utils.date.DateUtil;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;

/**
 * @ClassName HmacUtl
 * @Description sim 加密
 * @Author Charles
 * @Date 2021/05/26 17:25
 */
public class HmacUtil {

    /**
     * MAC 算法
     */
    static final String ALGORITHM_MAC = "HmacSHA1";

    public static final String SIM_HASH = "hash";

    public static final String SIM_TIMESTAMP = "timestamp";

    public static final String SIM_UUID = "uuid";

    public static final String COMMON_FILTER = "common_filter";

    /**
     * 密钥
     */
    static String API_SHARED_KEY = "3024547eba5d58a4af5b2209bd563d5e";

    static String ACCOUNT_UUID = "9c201f7882515413815be64ffe2217ec";

    public static String USER_UUID = "de485df22a9a5c9aa71faaf48c1bd57f";

    public static String API_LINK = "https://platform.keepgo.com/api/9c201f7882515413815be64ffe2217ec/";

    /**
     * @Title: generateKey
     * @Description: // 生成密钥
     * @Param: []
     * @Return: java.util.Map<java.lang.String, java.lang.String>
     * @Date: 2021/5/26 5:34 下午
     * @Author: Charles
     */
    public static Map<String, String> generateKey() {
        try {
            Map<String, String> map = Maps.newHashMap();
            String timestamp = DateUtil.getTimestamp();
            String hash = encodeBase64(encryptionHMAC(timestamp + ACCOUNT_UUID));
            map.put(SIM_TIMESTAMP, timestamp);
            map.put(SIM_HASH, hash);
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @Title: encryptionHMAC
     * @Description: // HMAC加密
     * @Param: [source]
     * @Return: byte[]
     * @Date: 2021/5/26 5:34 下午
     * @Author: Charles
     */
    static byte[] encryptionHMAC(String source) throws Exception {
        SecretKey secretKey = new SecretKeySpec(API_SHARED_KEY.getBytes("UTF-8"), ALGORITHM_MAC);
        Mac mac = Mac.getInstance(ALGORITHM_MAC);
        mac.init(secretKey);
        mac.update(source.getBytes("UTF-8"));
        return mac.doFinal();
    }

    /**
     * @Title: encodeBase64
     * @Description: // base64编码
     * @Param: [source]
     * @Return: java.lang.String
     * @Date: 2021/5/26 5:34 下午
     * @Author: Charles
     */
    static String encodeBase64(byte[] source) throws Exception {
        return new String(Base64.encodeBase64(source), "UTF-8");
    }
}
