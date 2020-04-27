package com.gaoc.external.sms.service;

import com.gaoc.core.model.R;
import com.gaoc.core.service.BaseService;
import com.gaoc.external.sms.model.LogSendSms;

/**
 * <p>
 * 发送短信记录表 服务类
 * </p>
 *
 * @author Gaoc
 * @since 2020-03-31
 */
public interface ILogSendSmsService extends BaseService<LogSendSms> {

	/**
	 * 发送短信
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 */
	R sendSms(String mobile, String content);

}
