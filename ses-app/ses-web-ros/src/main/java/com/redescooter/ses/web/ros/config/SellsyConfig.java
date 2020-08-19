package com.redescooter.ses.web.ros.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName:SellsyConfig
 * @description: SellsyConfig
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/19 16:35
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sellsy", ignoreUnknownFields = true)
public class SellsyConfig {
    
    //消费者——指您将开发的应用程序
    private static String consumerToken;
    //
    private static String consumerSecret;
    
    //指用户识别 OAuth 所需的唯一标识符
    private static String userToken;
    
    //Secret-指允许通过 OAuth 进行安全交换的秘密密钥
    private static String userSecret;
}
