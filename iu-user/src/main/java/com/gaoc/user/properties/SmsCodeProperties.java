package com.gaoc.user.properties;

import lombok.Data;

@Data
public class SmsCodeProperties {

	/**
	 * 短信验证码长度，默认：4
	 */
	private int length = 4;

	/**
	 * 发送间隔时间，单位：秒，默认：60秒
	 */
	private long sendTime = 60L;

	/**
	 * 验证码有效时长，单位：分钟，默认：5分钟
	 */
	private long expireTime = 5L;

	/**
	 * 当日手机号最多发送验证码短信条数，默认：50
	 */
	private int maxSendNum = 50;

}
