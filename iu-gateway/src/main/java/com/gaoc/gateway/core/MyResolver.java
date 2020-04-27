package com.gaoc.gateway.core;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.gaoc.common.util.JwtUtil;

import cn.hutool.core.util.StrUtil;
import reactor.core.publisher.Mono;

@Component
public class MyResolver implements KeyResolver {

	@Override
	public Mono<String> resolve(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		String key = request.getURI().getPath();
		String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		String hostAddress = getIp(request);
		key += hostAddress;

		if (StrUtil.isNotBlank(authorization) && JwtUtil.checkSign(authorization)) {
			key += JwtUtil.getUserId(authorization);
		}

		return Mono.just(key);
	}

	private static final String UNKNOWN = "unknown";

	private String getIp(ServerHttpRequest request) {
		HttpHeaders headers = request.getHeaders();
		String ip = headers.getFirst("X-Requested-For");
		if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = headers.getFirst("X-Forwarded-For");
		}
		if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = headers.getFirst("Proxy-Client-IP");
		}
		if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = headers.getFirst("WL-Proxy-Client-IP");
		}
		if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = headers.getFirst("HTTP_CLIENT_IP");
		}
		if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
		}
		if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddress().getAddress().getHostAddress();
		}
		return StrUtil.isBlank(ip) ? null : ip.split(",")[0];
	}

}
