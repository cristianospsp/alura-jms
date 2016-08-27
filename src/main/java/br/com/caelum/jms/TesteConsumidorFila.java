package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.*;
import javax.naming.InitialContext;

/**
 * Created by Cristiano on 26/08/16.
 */
public class TesteConsumidorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		final Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila );

		consumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {

				TextMessage textMessage = (TextMessage)message;
				try {
					//message.acknowledge();
					System.out.println(textMessage.getText());
					session.rollback();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

		});


		new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();
	}

}
