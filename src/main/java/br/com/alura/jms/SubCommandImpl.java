package br.com.alura.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class SubCommandImpl implements Command {
	private Session session;
	private Destination destination;

	@Override
	public void execute() throws Exception {
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener((Message message) -> {
			//System.out.println(message.getClass());
			// org.apache.activemq.command.ActiveMQTextMessage
			TextMessage text = (TextMessage) message;
			try {
				System.out.println(text.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void prepare(Object... args) {
		this.session = (Session) args[0];
		this.destination = (Destination) args[1];
	}
}
