package com.redescooter.ses.web.website.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author jerry
 * @Date 2021/1/24 12:43 上午
 * @Description 前端请求加密私钥公钥
 **/
@Data
@Component
@ConfigurationProperties(prefix = "requests-key",ignoreInvalidFields=false)
public class RequestsKeyProperties {

    /**
     * 解密私钥
     */
    private String privateKey;

    /**
     * 加密公钥
     */
    private String publicKey;

}
