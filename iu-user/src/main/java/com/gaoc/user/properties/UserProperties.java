package com.gaoc.user.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.gaoc.user")
public class UserProperties {

	/**
	 * 短信验证码配置
	 */
	private SmsCodeProperties smsCode = new SmsCodeProperties();

	/**
	 * 订单过期时间，单位：毫秒，默认：10000
	 */
	private int orderExpireTime = 10000;

	/**
	 * elasticsearch配置
	 */
	private EsProperties es = new EsProperties();

}
