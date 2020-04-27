package com.gaoc.user.es.config;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import com.gaoc.user.properties.EsProperties;
import com.gaoc.user.properties.UserProperties;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SpringBootConfiguration
public class EsConfig {

	private final UserProperties userProperties;

	@Bean
	public RestHighLevelClient restHighLevelClient() {
		EsProperties es = userProperties.getEs();
		List<String> url = es.getUrl();
		if (CollUtil.isEmpty(url)) {
			log.error("===========es连接地址不能为空==========");
			throw new RuntimeException("===========es连接地址不能为空==========");
		}

		log.info("开始创建ES连接，连接地址为：{}", url.toString());
		List<HttpHost> collect = url.stream().map(s -> {
			String[] address = s.split(":");
			String ip = address[0];
			Integer port = Integer.parseInt(address[1]);
			String scheme = es.getScheme();
			return new HttpHost(ip, port, scheme);
		}).collect(Collectors.toList());
		HttpHost[] hosts = ArrayUtil.toArray(collect, HttpHost.class);
		RestClientBuilder restClientBuilder = RestClient.builder(hosts);

		restClientBuilder.setHttpClientConfigCallback(new HttpClientConfigCallback() {

			@Override
			public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
				httpClientBuilder.setMaxConnTotal(es.getMaxConnect());
				httpClientBuilder.setMaxConnPerRoute(es.getMaxRoute());
				String username = es.getUsername();
				String password = es.getPassword();
				if (StrUtil.isNotBlank(username) && StrUtil.isNotBlank(password)) {
					CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
					credentialsProvider.setCredentials(AuthScope.ANY,
							new UsernamePasswordCredentials(username, password));
					httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
				}
				return httpClientBuilder;
			}

		});

		restClientBuilder.setRequestConfigCallback(new RequestConfigCallback() {

			@Override
			public Builder customizeRequestConfig(Builder requestConfigBuilder) {
				requestConfigBuilder.setConnectTimeout(es.getConnectTimeout() * 1000);
				requestConfigBuilder.setSocketTimeout(es.getConnectTimeout() * 1000);
				requestConfigBuilder.setConnectionRequestTimeout(es.getConnectTimeout() * 1000);
				return requestConfigBuilder;
			}

		});

		return new RestHighLevelClient(restClientBuilder);
	}

}
