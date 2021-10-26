package br.com.alura.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;

public class TesteConsumidorCommand {
	public static void main(String[] args) throws Exception {
		run(new StartCommandImpl());

	}

	private static void run(Command command) throws Exception {
		InitialContext initialContext = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();

		try {
			command.prepare(initialContext, conn);
			command.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		conn.close();
		initialContext.close();
	}
}
