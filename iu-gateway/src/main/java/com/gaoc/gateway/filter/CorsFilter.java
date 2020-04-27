package com.gaoc.gateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.gaoc.gateway.properties.BaseProperties;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class CorsFilter implements WebFilter {

	private final BaseProperties baseProperties;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		if (baseProperties.getEnableCors()) {
			ServerHttpRequest request = exchange.getRequest();
			String path = request.getPath().value();
			ServerHttpResponse response = exchange.getResponse();

			if ("/favicon.ico".equals(path)) {
				response.setStatusCode(HttpStatus.OK);
				return Mono.empty();
			}

			if (!CorsUtils.isCorsRequest(request)) {
				return chain.filter(exchange);
			}

			HttpHeaders requestHeaders = request.getHeaders();
			HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
			HttpHeaders headers = response.getHeaders();
			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
			headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
			if (requestMethod != null) {
				headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
			}

			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
			headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
			headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600L");

			if (request.getMethod() == HttpMethod.OPTIONS) {
				response.setStatusCode(HttpStatus.OK);
				return Mono.empty();
			}
		}

		return chain.filter(exchange);
	}

}
