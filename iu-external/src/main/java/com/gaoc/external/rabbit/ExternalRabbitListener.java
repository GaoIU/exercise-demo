//package com.gaoc.external.rabbit;
//
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//
//import com.alibaba.fastjson.JSON;
//import com.gaoc.common.constant.QueueConstant;
//import com.gaoc.common.vo.SmsCodeVO;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RequiredArgsConstructor
//@EnableBinding(ExternalProcessor.class)
//public class ExternalRabbitListener {
//
//	private final ExternalProcessor externalProcessor;
//
//	@ServiceActivator(inputChannel = QueueConstant.SMS_OUTPUT)
//	public void smsOutput(SmsCodeVO smsCodeVO) {
//		log.info("进入短信发送跨服务监听，该服务为：{}，监听消息为：{}", "iu-external", JSON.toJSONString(smsCodeVO));
//	}
//
//	@ServiceActivator(inputChannel = QueueConstant.DELAY_OUTPUT)
//	public void delayOutput(String message) {
//		log.info("进入过期监听，该服务为：{}，接收到的消息为：{}", "iu-external", message);
//		Message<String> build = MessageBuilder.withPayload(message).setHeader(AmqpHeaders.DELAY, 10000).build();
//		externalProcessor.delayOutput().send(build);
//	}
//
//}
