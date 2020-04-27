package com.gaoc.core.config;

import java.util.List;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.gaoc.core.interceptor.AuthenticationInterceptor;
import com.gaoc.core.properties.BaseProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootConfiguration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	private final AuthenticationInterceptor authenticationInterceptor;

	private final BaseProperties baseProperties;

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		if (baseProperties.getEnableUserId()) {
			List<String> excludeUrl = baseProperties.getExcludeUrl();
			registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**").excludePathPatterns(excludeUrl);
		}
		super.addInterceptors(registry);
	}

	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		if (baseProperties.getEnableCors()) {
			registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("*")
					.allowCredentials(true).maxAge(3600);
		}
		super.addCorsMappings(registry);
	}

}
