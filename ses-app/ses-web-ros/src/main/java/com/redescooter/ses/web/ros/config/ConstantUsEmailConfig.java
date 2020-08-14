package com.redescooter.ses.web.ros.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName:ConstantUsEmailConfig
 * @description: ConstantUsEmailConfig
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/14 18:44
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ConstantUsEmail", ignoreUnknownFields = true)
public class ConstantUsEmailConfig {
    
    private String salePrincipal;
    
    private List<String> salePrincipalList;
    
    private void changeEmail(String salePrincipal){
        salePrincipalList=Arrays.stream(salePrincipal.split(",")).collect(Collectors.toList());
    }
}
