package br.com.alura.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;

import br.com.alura.jms.modelo.Pedido;

/**
 * A versão do JMS utilizado nesta implementação é a versão 1.1;
 * 
 * @author Pedro Junior
 *
 */
public class TesteConsumidorTopicoComercial {
	public static void main(String[] args) throws Exception {
		// propriedade que deve ser adicionada para que seja possível saber quais classe
		// podem ser serializadas
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "java.lang,br.com.alura.jms.modelo");
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.setClientID("comercial");
		conn.start();

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topico = (Topic) context.lookup("loja");
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");
		consumer.setMessageListener((Message message) -> {
			System.out.println(message.getClass());
			ObjectMessage objectMessage = (ObjectMessage) message;
			try {
				Pedido pedido = (Pedido) objectMessage.getObject();
				System.out.println(pedido.getCodigo());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		});

		// recebe uma única mensagem
		// Message message = consumer.receive();
		// System.out.println("Message: " + message);

		Scanner scan = new Scanner(System.in);
		scan.nextLine();

		scan.close();
		session.close();
		conn.close();
		context.close();
	}
}
