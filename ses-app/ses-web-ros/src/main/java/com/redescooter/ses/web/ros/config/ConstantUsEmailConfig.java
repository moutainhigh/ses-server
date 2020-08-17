package com.redescooter.ses.web.ros.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ClassName:ConstantUsEmailConfig
 * @description: ConstantUsEmailConfig
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/14 18:44
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "constant-us-email", ignoreUnknownFields = true)
public class ConstantUsEmailConfig {

    private List<String> salePrincipal;
}
