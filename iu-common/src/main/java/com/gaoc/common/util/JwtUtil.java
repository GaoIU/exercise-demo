package com.gaoc.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.gaoc.common.constant.BaseConstant;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class JwtUtil {

	private static final String DATA_KEY = "data";

	private static final String TIME_KEY = "time";

	private static final RSA rsa = new RSA(BaseConstant.PRIVATE_KEY, BaseConstant.PUBLIC_KEY);

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static String createSign(Object value, Long time, TimeUnit timeUnit) {
		if (value == null || time == null) {
			return null;
		}

		Map<String, String> signMap = new HashMap<>(2);
		signMap.put(DATA_KEY, value.toString());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expireTime = null;

		switch (timeUnit) {
		case DAYS:
			expireTime = now.plusDays(time);
			break;

		case HOURS:
			expireTime = now.plusHours(time);
			break;

		case MINUTES:
			expireTime = now.plusMinutes(time);
			break;

		case SECONDS:
			expireTime = now.plusSeconds(time);
			break;

		case MILLISECONDS:
			expireTime = now.plusSeconds(time / 1000);
			break;

		default:
			expireTime = now.plusHours(2);
			break;
		}

		String expire = expireTime.format(FORMATTER);
		signMap.put(TIME_KEY, expire);

		String sign = rsa.encryptBcd(JSONUtil.toJsonStr(signMap), KeyType.PublicKey);
		return sign;
	}

	public static boolean checkSign(String sign) {
		if (StrUtil.isBlank(sign)) {
			return false;
		}

		try {
			String signJson = rsa.decryptStrFromBcd(sign, KeyType.PrivateKey);
			JSONObject signObj = JSONUtil.parseObj(signJson);
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime expireTime = LocalDateTime.parse(signObj.getStr(TIME_KEY));

			if (expireTime.isBefore(now)) {
				return false;
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getUserId(String sign) {
		if (StrUtil.isBlank(sign)) {
			return null;
		}

		try {
			String signJson = rsa.decryptStrFromBcd(sign, KeyType.PrivateKey);
			JSONObject signObj = JSONUtil.parseObj(signJson);
			return signObj.getStr(DATA_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private JwtUtil() {
	}

}
