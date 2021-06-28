package com.redescooter.ses.service.scooter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description app端请求加密公钥私钥
 * @Author Chris
 * @Date 2021/4/12 16:40
 */
@Data
@Component
@ConfigurationProperties(prefix = "request-key")
public class RequestKeyProperties {

    /**
     * 加密公钥
     */
    private String publicKey;

    /**
     * 解密私钥
     */
    private String privateKey;

    /**
     * AES算法的key
     */
    private String aesKey;

}
