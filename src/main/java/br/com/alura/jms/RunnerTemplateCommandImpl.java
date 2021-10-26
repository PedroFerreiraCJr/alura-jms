package br.com.alura.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;

public class RunnerTemplateCommandImpl extends RunnerTemplate {

	public RunnerTemplateCommandImpl(Command command) {
		super(command);
	}

	@Override
	public Map<String, Object> prepare() throws Exception {
		InitialContext initialContext = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();

		Map<String, Object> result = new HashMap<>();
		result.put("initialContext", initialContext);
		result.put("conn", conn);

		return result;
	}

	@Override
	public void finalize(Map<String, Object> map) throws Exception {
		InitialContext initialContext = (InitialContext) map.get("initialContext");
		Connection conn = (Connection) map.get("conn");
		initialContext.close();
		conn.close();
	}
}