package com.gaoc.gateway.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.lang.Dict;
import cn.hutool.http.HttpStatus;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {

	@GetMapping("/defaultFallback")
	public Mono<Dict> defaultFallback() {
		Dict dict = new Dict(2);
		dict.put("code", HttpStatus.HTTP_BAD_GATEWAY);
		dict.put("message", "网络不佳，请稍后重试");
		return Mono.just(dict);
	}

}
