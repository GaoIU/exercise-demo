package com.gaoc.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsCodeVO {

	/** 短信记录ID */
	private Long logSendSmsId;

	/** 手机号码 */
	private String mobile;

	/** 短信内容 */
	private String content;

}
