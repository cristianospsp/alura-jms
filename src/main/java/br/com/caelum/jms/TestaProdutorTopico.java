package br.com.caelum.jms;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;
import java.io.StringWriter;

/**
 * Created by Cristiano on 26/08/16.
 */
public class TestaProdutorTopico {

	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination topico = (Destination) context.lookup("loja");

		MessageProducer producer = session.createProducer(topico);

		Pedido pedido = new PedidoFactory().geraPedidoComValores();

//		StringWriter writer = new StringWriter();
//		JAXB.marshal(pedido, writer);
//		String xml = writer.toString();
//		System.out.println(xml);

		Message message = session.createObjectMessage(pedido);
		//message.setBooleanProperty("ebook", true);
		producer.send(message);

		session.close();
		connection.close();
		context.close();
	}
}