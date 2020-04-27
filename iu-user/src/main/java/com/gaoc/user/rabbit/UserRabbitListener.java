package com.gaoc.user.rabbit;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.alibaba.fastjson.JSON;
import com.gaoc.common.constant.BaseConstant;
import com.gaoc.common.constant.QueueConstant;
import com.gaoc.common.vo.SmsCodeVO;
import com.gaoc.core.model.R;
import com.gaoc.user.constant.UserConstant;
import com.gaoc.user.feign.ExternalServiceFeign;
import com.gaoc.user.model.LogSendSms;
import com.gaoc.user.service.ILogSendSmsService;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableBinding(UserRabbitProcessor.class)
public class UserRabbitListener {

	private final ExternalServiceFeign externalServiceFeign;

	private final ILogSendSmsService logSendSmsService;

	/**
	 * 异步发送短信监听
	 * 
	 * @param smsCodeVO
	 */
	@StreamListener(QueueConstant.SMS_INPUT)
	public void smsInput(SmsCodeVO smsCodeVO) {
		log.info("进入短信发送监听，监听消息为：{}", JSON.toJSONString(smsCodeVO));
		String mobile = smsCodeVO.getMobile();
		String content = smsCodeVO.getContent();
		R sendSmsData = externalServiceFeign.sendSms(mobile, content);
		if (R.isFail(sendSmsData)) {
			log.error("短信发送失败，手机号码为：{}，短信内容为：{}，失败原因为：{}", mobile, content, sendSmsData.getMessage());
			return;
		}

		log.info("短信发送成功，手机号码为：{}，短信内容为：{}", mobile, content);
		LogSendSms logSendSms = new LogSendSms();
		logSendSms.setId(smsCodeVO.getLogSendSmsId());
		logSendSms.setStatus(BaseConstant.INT_TRUE);
		logSendSmsService.updateById(logSendSms);
	}

	@StreamListener(QueueConstant.DELAY_INPUT)
	public void delayOutput(Dict dict) {
		log.info("进入过期监听，该服务Input为：{}，接收到的消息为：{}", "iu-user", JSON.toJSONString(dict));

		String code = dict.getStr("code");
		Integer status = dict.getInt("status");
		String message = dict.getStr("message");

		if (StrUtil.equals(code, UserConstant.ORDER_EXPIRE_TIP) && UserConstant.TO_PAY == status) {
			// 订单尚未支付，进入超时前提醒
			log.info("您的订单：{}将在{}分钟后超时自动关闭，请尽快处理！", message, 10);
		} else if (StrUtil.equals(code, UserConstant.ORDER_CLOSE) && UserConstant.TO_PAY == status) {
			log.info(message);
		}
	}

}
