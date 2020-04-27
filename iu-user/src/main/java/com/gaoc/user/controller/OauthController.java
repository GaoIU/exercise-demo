package com.gaoc.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaoc.core.model.R;
import com.gaoc.user.service.OauthService;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@RequestMapping("/oauth")
@RequiredArgsConstructor
@RestController
public class OauthController {

	private final OauthService oauthService;

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile
	 * @return
	 */
	@PostMapping("/sendSmsCode")
	public R sendSmsCode(String mobile) {
		if (StrUtil.isBlank(mobile)) {
			return R.fail("手机号码不能为空");
		}

		if (!ReUtil.isMatch("0?(13|14|15|18|17|19)[0-9]{9}", mobile)) {
			return R.fail("手机号码不合法");
		}

		return oauthService.sendSmsCode(mobile);
	}

}
