package com.gaoc.user.service;

import com.gaoc.core.model.R;

public interface OauthService {

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile
	 * @return
	 */
	R sendSmsCode(String mobile);

}
