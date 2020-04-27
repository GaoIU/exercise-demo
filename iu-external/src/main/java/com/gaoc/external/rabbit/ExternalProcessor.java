//package com.gaoc.external.rabbit;
//
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.SubscribableChannel;
//import org.springframework.stereotype.Component;
//
//import com.gaoc.common.constant.QueueConstant;
//
//@Component
//public interface ExternalProcessor {
//
//	@Input(QueueConstant.SMS_OUTPUT)
//	SubscribableChannel smsInput();
//
//	@Output(QueueConstant.SMS_INPUT)
//	MessageChannel smsOutput();
//
//	@Input(QueueConstant.DELAY_OUTPUT)
//	SubscribableChannel delayInput();
//
//	@Output(QueueConstant.DELAY_INPUT)
//	MessageChannel delayOutput();
//
//}
