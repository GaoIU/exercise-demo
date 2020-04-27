package com.gaoc.common.constant;

public class QueueConstant {

	/** 发送短信生产队列-值与配置文件中的值对应 */
	public static final String SMS_INPUT = "sms-input";

	/** 发送短信消费队列-值与配置文件中的值对应 */
	public static final String SMS_OUTPUT = "sms-output";

	/** 延迟生产队列-值与配置文件中的值对应 */
	public static final String DELAY_INPUT = "delay-input";

	/** 延迟消费队列-值与配置文件中的值对应 */
	public static final String DELAY_OUTPUT = "delay-output";

	private QueueConstant() {
	}

}
