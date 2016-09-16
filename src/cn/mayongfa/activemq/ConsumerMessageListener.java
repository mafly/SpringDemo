package cn.mayongfa.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 消费者监听类
 * 
 * @author Mafly
 */
@Component
public class ConsumerMessageListener implements MessageListener {

	private Logger log = Logger.getLogger(ConsumerMessageListener.class);

	@Override
	public void onMessage(Message arg0) {
		// 监听发送到消息队列的文本消息，作强制转换。
		TextMessage textMessage = (TextMessage) arg0;
		try {
			System.out.println("接收到的消息内容是：" + textMessage.getText());

			// TODO: 你喜欢的任何事情...

		} catch (JMSException e) {
			log.error("", e);
		}

	}

}
