import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by tania on 20.03.17.
 */
public class QueueSender implements Runnable {
    ActiveMQConnectionFactory connectionFactory = null;

    public QueueSender(ActiveMQConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    public void run() {
        try {
            // First create a connection
            Connection connection =
                    connectionFactory.createConnection();
            connection.start();

            // Now create a Session
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            // Let's create a topic. If the topic exist,
            //it will return that
            Destination destination = session.createQueue("QUEUE");
            // Create a MessageProducer from
            //the Session to the Topic or Queue
            MessageProducer producer =
                    session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            // Create a messages for the current QUEUE

            String text = "queue";
            TextMessage message = session.createTextMessage(text);

            // Send the message to queue
            producer.send(message);

            // Do the cleanup
            session.close();
            connection.close();
        } catch (JMSException jmse) {
            System.out.println("Exception: " + jmse.getMessage());
        }
    }
}
