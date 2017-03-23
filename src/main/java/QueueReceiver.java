import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Created by tania on 20.03.17.
 */
public class QueueReceiver implements Runnable {
    ActiveMQConnectionFactory connectionFactory = null;

    public QueueReceiver(ActiveMQConnectionFactory connectionFactory) {
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
            Destination topicDestination =
                    session.createQueue("QUEUE");

            // Create a MessageProducer from the Session
            //to the Topic or Queue
            MessageConsumer messageConsumer =
                    session.createConsumer(topicDestination);

            //Get the message
            Message message = messageConsumer.receive();

            TextMessage textMessage = (TextMessage) message;

            System.out.println(textMessage.getText());

            // Do the cleanup
            session.close();
            connection.close();
        } catch (JMSException jmse) {
            System.out.println("Exception: " + jmse.getMessage());
        }

    }
}
