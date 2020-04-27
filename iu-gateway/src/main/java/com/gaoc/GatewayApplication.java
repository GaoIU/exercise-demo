package com.gaoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.gaoc.common.constant.BaseConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableDiscoveryClient
@ConfigurationPropertiesScan("com.gaoc.gateway.properties")
@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
		log.info(BaseConstant.BASE_LOGGER);
	}

}
