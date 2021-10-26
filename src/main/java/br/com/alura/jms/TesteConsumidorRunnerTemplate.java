package br.com.alura.jms;

public class TesteConsumidorRunnerTemplate {
	public static void main(String[] args) throws Exception {
		RunnerTemplate runner = new RunnerTemplateCommandImpl(new StartCommandImpl());
		runner.run();
	}
}
