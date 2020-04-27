package com.gaoc.user.service.impl;

import org.springframework.stereotype.Service;

import com.gaoc.core.service.BaseServiceImpl;
import com.gaoc.user.mapper.LogSendSmsMapper;
import com.gaoc.user.model.LogSendSms;
import com.gaoc.user.service.ILogSendSmsService;

/**
 * <p>
 * 发送短信记录表 服务实现类
 * </p>
 *
 * @author Gaoc
 * @since 2020-04-01
 */
@Service
public class LogSendSmsServiceImpl extends BaseServiceImpl<LogSendSmsMapper, LogSendSms> implements ILogSendSmsService {

}
