package com.gaoc.user.constant;

public class UserConstant {

	/** 短信验证码的短信文本内容 */
	public static final String META_SMS_CODE = "SMS_CODE";

	/** 订单超时前提醒-队列延迟code */
	public static final String ORDER_EXPIRE_TIP = "ORDER_EXPIRE_TIP";

	/** 订单超时关闭-队列延迟code */
	public static final String ORDER_CLOSE = "ORDER_CLOSE";

	/** 待付款：1，订单状态 */
	public static final int TO_PAY = 1;

	private UserConstant() {
	}

}
