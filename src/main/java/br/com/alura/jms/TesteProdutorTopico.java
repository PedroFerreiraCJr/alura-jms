package br.com.alura.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.alura.jms.modelo.Pedido;
import br.com.alura.jms.modelo.PedidoFactory;

/**
 * A versão do JMS utilizado nesta implementação é a versão 1.1;
 * 
 * @author Pedro Junior
 *
 */
public class TesteProdutorTopico {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topico = (Destination) context.lookup("loja");
		MessageProducer producer = session.createProducer(topico);

		// essa abordagem dá certo para mensagens de texto
		/*
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		StringWriter writer = new StringWriter();
		JAXB.marshal(pedido, writer);

		String xml = writer.toString();

		Message message = session.createTextMessage(xml);
		*/
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		Message message = session.createObjectMessage(pedido);
		
		// message.setBooleanProperty("ebook", false);
		producer.send(message);

		session.close();
		conn.close();
		context.close();
	}
}
