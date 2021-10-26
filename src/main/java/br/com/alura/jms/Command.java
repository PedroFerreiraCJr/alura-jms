package br.com.alura.jms;

import java.util.Map;

public interface Command {
	public abstract void execute() throws Exception;

	public abstract void prepare(Object... args);

	default void prepare(Map<String, Object> args) {
		
	}
}
