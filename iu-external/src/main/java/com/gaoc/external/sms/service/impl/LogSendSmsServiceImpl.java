package com.gaoc.external.sms.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaoc.core.model.R;
import com.gaoc.core.service.BaseServiceImpl;
import com.gaoc.external.sms.mapper.LogSendSmsMapper;
import com.gaoc.external.sms.model.LogSendSms;
import com.gaoc.external.sms.service.ILogSendSmsService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 发送短信记录表 服务实现类
 * </p>
 *
 * @author Gaoc
 * @since 2020-03-31
 */
@Slf4j
@Service
public class LogSendSmsServiceImpl extends BaseServiceImpl<LogSendSmsMapper, LogSendSms> implements ILogSendSmsService {

	/**
	 * 发送短信
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public R sendSms(String mobile, String content) {
		log.info("=======================调用短信服务商=====================");
		boolean isSend = false;
		try {
			// 模拟调用短信服务商发送短信
			TimeUnit.SECONDS.sleep(1);
			isSend = true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (!isSend) {
			log.error("调用短信服务商发送短信失败，手机号码为：{}，短信内容为：{}", mobile, content);
			return R.fail("调用短信服务商发送短信失败");
		}

		log.info("短信发送成功，手机号码为：{}，短信内容为：{}", mobile, content);
		LogSendSms logSendSms = new LogSendSms(mobile, content);
		boolean save = save(logSendSms);
		if (save) {
			return R.ok();
		}

		return R.fail("发送失败");
	}

}
