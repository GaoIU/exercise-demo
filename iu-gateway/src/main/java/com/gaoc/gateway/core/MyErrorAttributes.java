package com.gaoc.gateway.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import cn.hutool.core.map.MapUtil;

@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
		Map<String, Object> errorAttributes = super.getErrorAttributes(request, includeStackTrace);
		Map<String, Object> data = new HashMap<>(2);
		Integer status = MapUtil.getInt(errorAttributes, "status");

		if (status.equals(HttpStatus.GATEWAY_TIMEOUT.value())) {
			data.put("code", HttpStatus.GATEWAY_TIMEOUT.value());
			data.put("status", HttpStatus.GATEWAY_TIMEOUT.value());
			data.put("message", "网络不佳，请稍后重试");
		} else if (status.equals(HttpStatus.TOO_MANY_REQUESTS.value())) {
			data.put("code", HttpStatus.TOO_MANY_REQUESTS.value());
			data.put("status", HttpStatus.TOO_MANY_REQUESTS.value());
			data.put("message", "操作频繁，请稍后重试");
		} else if (status.equals(HttpStatus.NOT_FOUND.value())) {
			data.put("code", HttpStatus.NOT_FOUND.value());
			data.put("status", HttpStatus.NOT_FOUND.value());
			data.put("message", "该服务不存在");
		} else if (status.equals(HttpStatus.SERVICE_UNAVAILABLE.value())) {
			data.put("code", HttpStatus.SERVICE_UNAVAILABLE.value());
			data.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
			data.put("message", "服务器繁忙，请稍后重试");
		} else {
			return super.getErrorAttributes(request, includeStackTrace);
		}

		return data;
	}

}
