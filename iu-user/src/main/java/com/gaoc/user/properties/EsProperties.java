package com.gaoc.user.properties;

import java.util.List;

import lombok.Data;

@Data
public class EsProperties {

	/**
	 * es连接地址
	 */
	private List<String> url;

	/**
	 * es连接类型，http或者https。默认：http
	 */
	private String scheme = "http";

	/**
	 * 账号
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 连接超时时间，单位：秒。默认：5秒
	 */
	private int connectTimeout = 5;

	/**
	 * 最大连接数
	 */
	private int maxConnect = 100;

	/**
	 * 最大路有数
	 */
	private int maxRoute = 100;

}
