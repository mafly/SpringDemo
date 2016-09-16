package cn.mayongfa.activemq;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * ActiveMQ 消息生产类
 * 
 * @author Mafly
 *
 */
@Component
public class MessageSender {

	private Logger log = Logger.getLogger(MessageSender.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	private String Queue = "default_queue";

	private String GoldQueue = "gold_queue";

	private Gson gson = new Gson();

	/**
	 * 用户登录消息
	 */
	public void userLogin(long id, String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", id);
		map.put("username", username);

		System.out.println("发送了一条消息。");
		// 发送到金币队列
		sendMessage(gson.toJson(map), 1);
	}

	/**
	 * 发送到消息队列
	 * 
	 * @param messgae
	 * @param type
	 *            类型，0:默认队列 1：金币队列 ...
	 */
	public void sendMessage(final String messgae, int type) {
		try {
			String destination = this.Queue;
			if (type == 1) {
				destination = GoldQueue;
			}
			jmsTemplate.send(destination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage = session.createTextMessage(messgae);
					return textMessage;
				}
			});
		} catch (Exception e) {
			log.error("", e);
		}
	}
}
