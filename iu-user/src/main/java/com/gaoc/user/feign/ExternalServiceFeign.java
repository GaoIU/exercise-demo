package com.gaoc.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gaoc.core.model.R;

@FeignClient("iu-external")
@Service
public interface ExternalServiceFeign {

	/**
	 * 发送短信
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 */
	@PostMapping("/external/sendSms")
	R sendSms(@RequestParam("mobile") String mobile, @RequestParam("content") String content);

}
