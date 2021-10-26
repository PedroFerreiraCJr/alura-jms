package br.com.alura.jms;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.naming.InitialContext;

/**
 * A versão do JMS utilizado nesta implementação é a versão 1.1;
 * 
 * @author Pedro Junior
 *
 */
public class TesteQueueBrowser {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = (Destination) context.lookup("financeiro");
		QueueBrowser browser = session.createBrowser((Queue) destination);
		@SuppressWarnings("unchecked")
		Enumeration<Message> messages = (Enumeration<Message>) browser.getEnumeration();
		while (messages.hasMoreElements()) {
			System.out.println(messages.nextElement());
		}

		session.close();
		conn.close();
		context.close();
	}
}
