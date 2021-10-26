package br.com.alura.jms;

import java.util.Scanner;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.naming.InitialContext;

public class QueueTesteConsumer {
	public static void main(String[] args) throws Exception {
		InitialContext ctx = new InitialContext();
		QueueConnectionFactory cf = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
		QueueConnection conexao = cf.createQueueConnection();
		conexao.start();

		QueueSession sessao = conexao.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		Queue fila = (Queue) ctx.lookup("financeiro");
		QueueReceiver receiver = (QueueReceiver) sessao.createReceiver(fila);

		Message message = receiver.receive();
		System.out.println(message);

		Scanner scan = new Scanner(System.in);
		scan.nextLine();

		scan.close();
		sessao.close();
		conexao.close();
		ctx.close();
	}
}
