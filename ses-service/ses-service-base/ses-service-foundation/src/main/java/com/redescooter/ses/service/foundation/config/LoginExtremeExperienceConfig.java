package com.redescooter.ses.service.foundation.config;

import lombok.Data;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName:LoginExtremeExperienceConfig
 * @description: LoginExtremeExperienceConfig
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/21 11:47
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "saas-login-extreme-experience", ignoreUnknownFields = true)
public class LoginExtremeExperienceConfig {
    private Boolean web;
    
    private Boolean appIos;
    
    private Boolean appAndroid;
}
