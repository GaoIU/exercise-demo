package com.gaoc.gateway.filter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.gaoc.common.constant.BaseConstant;
import com.gaoc.common.util.JwtUtil;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class OauthFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		return -5;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (StrUtil.isBlank(authorization)) {
			return chain.filter(exchange);
		}

		ServerHttpResponse response = exchange.getResponse();
		if (!JwtUtil.checkSign(authorization)) {
			log.warn("===========请求路径：{} 签名不合法==========", request.getURI().getPath());
			return response.writeAndFlushWith(error(response, HttpStatus.HTTP_FORBIDDEN, "非法访问").map(Mono::just));
		}

		String userId = JwtUtil.getUserId(authorization);
		ServerHttpRequest requestHeader = buildRequestHeader(request, userId);
		return chain.filter(exchange.mutate().request(requestHeader).response(response).build());
	}

	private Mono<DataBuffer> error(ServerHttpResponse response, int code, String message) {
		Map<String, Object> res = new HashMap<>(2);
		res.put("code", code);
		res.put("message", message);

		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		Mono<DataBuffer> resMono = Mono.just(response.bufferFactory().wrap(JSONUtil.toJsonStr(res).getBytes()));

		return resMono;
	}

	private ServerHttpRequest buildRequestHeader(ServerHttpRequest request, String userId) {
		ServerHttpRequest decorator = new ServerHttpRequestDecorator(request) {

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.putAll(request.getHeaders());

				if (StrUtil.isNotBlank(userId)) {
					httpHeaders.remove(HttpHeaders.AUTHORIZATION);
					httpHeaders.add(BaseConstant.USER_ID, userId);
				}

				return httpHeaders;
			}

		};

		return decorator;
	}

//	private ServerHttpResponse buildResponse(ServerWebExchange exchange) {
//		ServerHttpResponse response = exchange.getResponse();
//		ServerHttpResponse decorator = new ServerHttpResponseDecorator(response) {
//
//			@Override
//			public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//				org.springframework.http.HttpStatus statusCode = response.getStatusCode();
//				if (statusCode != org.springframework.http.HttpStatus.TOO_MANY_REQUESTS) {
//					return super.writeWith(body);
//				}
//
//				log.info("==============检查到返回状态码为：429，返回限流响应体==============");
//				HttpHeaders httpHeaders = response.getHeaders();
//				httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//				ClientResponse clientResponse = ClientResponse.create(org.springframework.http.HttpStatus.OK)
//						.headers(headers -> headers.putAll(httpHeaders)).body(Flux.from(body)).build();
//				Mono<String> modifiedBody = clientResponse.bodyToMono(String.class).flatMap(originalBody -> {
//					Dict dict = new Dict(2);
//					dict.put("code", HttpStatus.HTTP_BAD_GATEWAY);
//					dict.put("message", "操作频繁，请稍后重试");
//					return Mono.just(JSONUtil.toJsonStr(dict));
//				});
//
//				BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters
//						.fromPublisher(modifiedBody, String.class);
//				CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, httpHeaders);
//				bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
//					Flux<DataBuffer> messageBody = outputMessage.getBody();
//					HttpHeaders headers = getDelegate().getHeaders();
//					if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
//						messageBody = messageBody.doOnNext(data -> headers.setContentLength(data.readableByteCount()));
//					}
//					return getDelegate().writeWith(messageBody);
//				}));
//
//				return super.writeWith(body);
//			}
//
//			@Override
//			public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
//				return writeWith(Flux.from(body).flatMapSequential(p -> p));
//			}
//
//		};
//
//		return decorator;
//	}

}
