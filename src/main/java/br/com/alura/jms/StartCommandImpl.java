package br.com.alura.jms;

import java.util.Map;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Session;
import javax.naming.InitialContext;

public class StartCommandImpl implements Command {
	private InitialContext context;
	private Connection conn;
	private Command subCommand;

	public StartCommandImpl() {
		this(new SubCommandImpl());
	}

	public StartCommandImpl(Command subCommand) {
		super();
		this.subCommand = subCommand;
	}

	@Override
	public void execute() throws Exception {
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = (Destination) context.lookup("financeiro");

		if (subCommand != null) {
			try {
				subCommand.prepare(session, destination);
				subCommand.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Scanner scan = new Scanner(System.in);
		scan.nextLine();

		scan.close();
		session.close();
	}

	@Override
	public void prepare(Object... args) {
		this.context = (InitialContext) args[0];
		this.conn = (Connection) args[1];
	}
	
	@Override
	public void prepare(Map<String, Object> args) {
		this.context = (InitialContext) args.get("initialContext");
		this.conn = (Connection) args.get("conn");
	}
}
