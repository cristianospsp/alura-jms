package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

/**
 * Created by Cristiano on 27/08/16.
 */
public class ConsumidorDLQ {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination fila = (Destination) context.lookup("DLQ");
		MessageConsumer consumer = session.createConsumer(fila );

		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				System.out.println(message);
			}
		});


		new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();
	}
}