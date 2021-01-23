package com.redescooter.ses.web.website.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author jerry
 * @Date 2021/1/24 12:11 上午
 * @Description stripe配置
 **/

@Data
@Component
@ConfigurationProperties(prefix = "rede.stripe-config")
public class StripeConfigProperties {

    private String currency;

    private String paymentMethodTypes;

    private String paymentEvent;

    private String publishableKey;

    private String secretkey;

    private String receiptEmail;


}
