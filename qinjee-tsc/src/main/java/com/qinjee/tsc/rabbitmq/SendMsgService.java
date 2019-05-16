package com.qinjee.tsc.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qinejee.consts.MQConsts;

@Service("sendMsgService")
public class SendMsgService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(Object message) {
		rabbitTemplate.convertAndSend(MQConsts.EXCHANGE_QINJEE_1, MQConsts.ROUTINGKEY_QINJEE_1, message);
	}
}
