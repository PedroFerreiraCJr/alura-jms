package br.com.alura.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * A versão do JMS utilizado nesta implementação é a versão 1.1;
 * 
 * @author Pedro Junior
 *
 */
public class TesteConsumidor {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener((Message message) -> {
			System.out.println(message.getClass());
			TextMessage text = (TextMessage) message;
			try {
				System.out.println(text.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		});
		
		// recebe uma única mensagem
		//Message message = consumer.receive();
		//System.out.println("Message: " + message);

		Scanner scan = new Scanner(System.in);
		scan.nextLine();

		scan.close();
		session.close();
		conn.close();
		context.close();
	}
}
