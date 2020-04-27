package com.gaoc.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.gaoc.common.constant.BaseConstant;
import com.gaoc.core.annotation.Anonymous;
import com.gaoc.core.model.R;
import com.gaoc.core.util.SessionUtil;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("========接收到请求=========：{}", request.getRequestURI());

		String userId = request.getHeader(BaseConstant.USER_ID);
		if (StrUtil.isNotBlank(userId)) {
			SessionUtil.setUserId(userId);
			return true;
		}

		Anonymous anonymous = null;
		if (handler instanceof HandlerMethod) {
			anonymous = ((HandlerMethod) handler).getMethodAnnotation(Anonymous.class);
		}
		if (anonymous != null) {
			return true;
		}

		log.warn("找不到用户信息，请求：{} 需要身份认证", request.getRequestURI());
		R fail = R.fail(HttpStatus.HTTP_UNAUTHORIZED, "未进行身份认证");
		R.printWriter(fail, response, MediaType.APPLICATION_JSON_VALUE);

		return false;
	}

}
