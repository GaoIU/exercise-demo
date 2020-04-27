package com.gaoc.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.gaoc.base")
public class BaseProperties {

	/**
	 * 开启跨域，默认：false
	 */
	private Boolean enableCors = false;

}
