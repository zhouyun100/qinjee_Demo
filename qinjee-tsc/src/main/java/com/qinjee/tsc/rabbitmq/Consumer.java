package com.qinjee.tsc.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.qinejee.consts.MQConsts;
import com.qinjee.tsc.model.UserInfoModel;

@Component
public class Consumer{
	
	@RabbitListener(queues = { MQConsts.QUEUE_1})
	public void handleMessage(UserInfoModel userInfo) throws Exception {
		// 处理消息
		System.out.println("receive msg : id=" + userInfo.getId() + ",message=" + userInfo.getUsername());
	}
}
