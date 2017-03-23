import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by tania on 20.03.17.
 */
public class TopicConsumer implements Runnable {

    ActiveMQConnectionFactory connectionFactory = null;
    String topic = null;

    public TopicConsumer(ActiveMQConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public TopicConsumer(ActiveMQConnectionFactory connectionFactory, String topic) {
        this.connectionFactory = connectionFactory;
        this.topic = topic;
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
            //create a topic. If the topic exist,
            //it will return that
            Destination topicDestination =
                    session.createTopic(topic);
            // Create a MessageProducer from the Session
            //to the Topic or Queue
            MessageConsumer messageConsumer =
                    session.createConsumer(topicDestination);
            //Get the message
            Message message = messageConsumer.receive();
            TextMessage textMessage = (TextMessage) message;
            System.out.println(topicDestination + " - " +textMessage.getText());
            System.out.println(message);
            // Do the cleanup
            session.close();
            connection.close();
        } catch (JMSException jmse) {
            System.out.println("Exception: " + jmse.getMessage());
        }
    }
}

