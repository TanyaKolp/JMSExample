import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by tania on 20.03.17.
 */
public class TopicProducer implements Runnable {
    //Connection Factory which will help in connecting to ActiveMQ serer
    ActiveMQConnectionFactory connectionFactory = null;
    String topic = null;
String text = null;
    public TopicProducer(ActiveMQConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public TopicProducer(ActiveMQConnectionFactory connectionFactory, String topic, String text) {
        this.connectionFactory = connectionFactory;
        this.topic = topic;
        this.text = text;
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
            Destination destination = session.createTopic(topic);

            // Create a MessageProducer from
            //the Session to the Topic or Queue
            MessageProducer producer =
                    session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            // Create a messages for the current climate
            String text = this.text;
            TextMessage message = session.createTextMessage(text);

            // Send the message to topic
            producer.send(message);

            // Do the cleanup
            session.close();
            connection.close();
        } catch (JMSException jmse) {
            System.out.println("Exception: " + jmse.getMessage());
        }
    }
}
