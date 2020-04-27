package com.gaoc.external.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaoc.core.model.R;
import com.gaoc.external.sms.service.ILogSendSmsService;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@RequestMapping("/external")
@RequiredArgsConstructor
@RestController
public class ExternalController {

	private final ILogSendSmsService logSendSmsService;

	/**
	 * 发送短信
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 */
	@PostMapping("/sendSms")
	public R sendSms(String mobile, String content) {
		if (StrUtil.isBlank(mobile)) {
			return R.fail("手机号码不能为空");
		}

		if (!ReUtil.isMatch("0?(13|14|15|18|17|19)[0-9]{9}", mobile)) {
			return R.fail("手机号码不合法");
		}

		if (StrUtil.isBlank(content)) {
			return R.fail("短信内容不能为空");
		}

		return logSendSmsService.sendSms(mobile, content);
	}
	
	@PostMapping("/wechatPay")
	public R wechatPay() {
		return null;
	}

}
