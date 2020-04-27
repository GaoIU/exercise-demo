package com.gaoc.user.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

import com.gaoc.common.constant.QueueConstant;

@Component
public interface UserRabbitProcessor {

	@Input(QueueConstant.SMS_INPUT)
	SubscribableChannel smsInput();

	@Output(QueueConstant.SMS_OUTPUT)
	MessageChannel smsOutput();

	@Input(QueueConstant.DELAY_INPUT)
	SubscribableChannel delayInput();

	@Output(QueueConstant.DELAY_OUTPUT)
	MessageChannel delayOutput();

}
