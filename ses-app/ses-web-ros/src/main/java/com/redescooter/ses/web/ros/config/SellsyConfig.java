package com.redescooter.ses.web.ros.config;

import lombok.Data;
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
    private String consumerToken;
    //
    private String consumerSecret;

    //指用户识别 OAuth 所需的唯一标识符
    private String userToken;

    //Secret-指允许通过 OAuth 进行安全交换的秘密密钥
    private String userSecret;

    //保留小数位
    private String decimalPlaces;

    //税务类型
    private String rateCategory;

    //货币单位
    private Integer currencyUnit;

    //语言
    private String lang;

    //发票布局
    private String docLayout;

    //主题
    private String subject;

    //计量单位
    private Integer unit;

    //增值税税率
    private Integer taxId;
}
