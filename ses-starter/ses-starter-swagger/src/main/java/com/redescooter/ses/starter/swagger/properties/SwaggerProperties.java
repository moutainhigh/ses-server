package com.redescooter.ses.starter.swagger.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Deprecated
@ConfigurationProperties(SwaggerProperties.prefix)
public class SwaggerProperties {
	public static final String prefix = "spring";

	/**
	 * 请使用spring.swagger.group
	 */
	private Map<String, Swagger2GroupProperties> swaggerGroup;
	
	public Map<String, Swagger2GroupProperties> getSwaggerGroup() {
		return swaggerGroup;
	}

	public void setSwaggerGroup(Map<String, Swagger2GroupProperties> swaggerGroup) {
		this.swaggerGroup = swaggerGroup;
	}
}
