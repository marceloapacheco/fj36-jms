package br.com.caleum.jms;

import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RegistraFinanceiroNoTopico {

	public static void main(String args[]) throws NamingException {
		
		InitialContext ic = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) ic.lookup("jms/RemoteConnectionFactory");
		Topic topic = (Topic) ic.lookup("jms/TOPICO.LIVRARIA");
		
		try(JMSContext context = factory.createContext("jms", "jms2")) {
			context.setClientID("financeiro");
			JMSConsumer consumer = (JMSConsumer) context.createDurableConsumer(topic, "AssinaturaNotas");
			consumer.setMessageListener(new TratadorDeMensagem());
			context.start();
			Scanner scanner = new Scanner(System.in);
			System.out.println("Financeiro esperando as mensagens");
			System.out.println("Aperter Enter para fechar a conexão");
			scanner.nextLine();
			scanner.close();
			context.stop();
 		}
		
	}
	
	
}
