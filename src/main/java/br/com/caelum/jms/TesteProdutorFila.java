package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;

/**
 * Created by Cristiano on 26/08/16.
 */
public class TesteProdutorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination fila = (Destination) context.lookup("financeiro");

		MessageProducer producer = session.createProducer(fila);

		Message message = session.createTextMessage("<pedido><id>13</id></pedido>");
		producer.send(message);


//		for (int i = 0; i < 1000; i++) {
//
//			Message message = session.createTextMessage("<pedido><id>"  +  i + "</id></pedido>");
//			producer.send(message);
//		}

		//new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();
	}

}
