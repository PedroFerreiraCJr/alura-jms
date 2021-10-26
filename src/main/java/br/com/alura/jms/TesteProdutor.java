package br.com.alura.jms;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

/**
 * A versão do JMS utilizado nesta implementação é a versão 1.1;
 * 
 * @author Pedro Junior
 *
 */
public class TesteProdutor {
	public static void main(String[] args) throws Exception {
		// A configuração do objeto InitialContext também é possível através de uma
		// instância de Properties recebida no construtor.
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.financeiro", "fila.financeiro");

		InitialContext context = new InitialContext(properties);
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = (Destination) context.lookup("financeiro");
		MessageProducer producer = session.createProducer(destination);

		// O serviço do ActiveMQ faz o balanceamento de carga para os consumidor
		// conectados na fila de forma que
		// cada consumidor só receba determinada mensagem um única vez. Ou seja, somente
		// um dos consumidors recebe
		// a mensagem.
		for (int i = 0; i < 100; i++) {
			Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
			producer.send(message);
		}

		session.close();
		conn.close();
		context.close();
	}
}
