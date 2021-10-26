package br.com.alura.jms;

import java.util.Scanner;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.naming.InitialContext;

public class QueueTesteConsumerDLQ {
	public static void main(String[] args) throws Exception {
		InitialContext ctx = new InitialContext();
		QueueConnectionFactory cf = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
		QueueConnection conexao = cf.createQueueConnection();
		conexao.start();

		QueueSession sessao = conexao.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		Queue fila = (Queue) ctx.lookup("DLQ");
		MessageConsumer consumer = sessao.createConsumer(fila);
		consumer.setMessageListener((Message message) -> {
			System.out.println(message);
		});

		Scanner scan = new Scanner(System.in);
		scan.nextLine();

		scan.close();
		sessao.close();
		conexao.close();
		ctx.close();
	}
}
