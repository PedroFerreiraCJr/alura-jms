package br.com.alura.jms;

import java.util.Map;

public abstract class RunnerTemplate {
	private Command command;

	public RunnerTemplate(Command command) {
		super();
		this.command = command;
	}

	public final void run() throws Exception {
		final Map<String, Object> prepareResult = prepare();
		try {
			command.prepare(prepareResult);
			command.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finalize(prepareResult);
	}

	public abstract Map<String, Object> prepare() throws Exception;

	public abstract void finalize(Map<String, Object> map) throws Exception;
}
