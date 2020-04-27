package com.gaoc.user.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gaoc.common.constant.BaseConstant;
import com.gaoc.common.vo.SmsCodeVO;
import com.gaoc.core.enums.ConditionEnum;
import com.gaoc.core.model.R;
import com.gaoc.core.util.SpringContextUtil;
import com.gaoc.user.constant.UserConstant;
import com.gaoc.user.model.CommonMeta;
import com.gaoc.user.model.LogSendSms;
import com.gaoc.user.properties.UserProperties;
import com.gaoc.user.rabbit.UserRabbitProcessor;
import com.gaoc.user.service.ICommonMetaService;
import com.gaoc.user.service.ILogSendSmsService;
import com.gaoc.user.service.OauthService;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OauthServiceImpl implements OauthService {

	private final RedisTemplate<String, Object> redisTemplate;

	private final ICommonMetaService commonMetaService;

	private final UserProperties userProperties;

	private final ILogSendSmsService logSendSmsService;

	private final UserRabbitProcessor userRabbitProcess;

	/**
	 * 发送短信验证码
	 * 
	 * @param <T>
	 * 
	 * @param mobile
	 * @param type
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public R sendSmsCode(String mobile) {
		Boolean hasKey = redisTemplate.hasKey(mobile);
		if (hasKey) {
			return R.fail("短信发送频繁，请稍后再试");
		}

		QueryWrapper<LogSendSms> query = new QueryWrapper<LogSendSms>();
		String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		query.eq("mobile", mobile).eq("status", BaseConstant.INT_TRUE).apply("DATE(create_time) = {0}", nowDate);
		int count = logSendSmsService.count(query);
		if (count >= userProperties.getSmsCode().getMaxSendNum()) {
			return R.fail("该号码今日发送短信验证码次数已达上限");
		}

		String smsCode = RandomUtil.randomNumbers(userProperties.getSmsCode().getLength());
		CommonMeta commonMeta = commonMetaService.getOne("code", ConditionEnum.EQ, UserConstant.META_SMS_CODE);
		if (commonMeta == null) {
			return R.fail("获取短信模板失败");
		}

		String content = commonMeta.getValue().replace("${code}", smsCode).replace("${expireTime}",
				String.valueOf(userProperties.getSmsCode().getExpireTime()));
		LogSendSms logSendSms = new LogSendSms(mobile, content, BaseConstant.INT_FALSE);
		boolean save = logSendSmsService.save(logSendSms);
		if (save) {
			SmsCodeVO smsCodeVO = new SmsCodeVO(logSendSms.getId(), mobile, content);
			Message<SmsCodeVO> message = MessageBuilder.withPayload(smsCodeVO).build();
			log.info("===============异步发送短信===============");
			userRabbitProcess.smsInput().send(message);

			redisTemplate.opsForValue().set(mobile, mobile, userProperties.getSmsCode().getSendTime(),
					TimeUnit.SECONDS);
			redisTemplate.opsForValue().set(mobile + "-" + smsCode, smsCode,
					Duration.ofMinutes(userProperties.getSmsCode().getExpireTime()));

			String profile = SpringContextUtil.getActiveProfile();
			if (StrUtil.equalsIgnoreCase(profile, BaseConstant.PROFILE_DEV)) {
				return R.ok(smsCode);
			}
			return R.ok();
		}

		return R.fail("发送失败");
	}

}
