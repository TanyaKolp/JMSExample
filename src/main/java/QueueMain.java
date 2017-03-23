import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Created by tania on 20.03.17.
 */
public class QueueMain {
    //    @Resource(lookup = "jms/ConnectionFactory")
//    private static ActiveMQConnectionFactory connectionFactory;
    public static void main(String[] args) {
        //Create the connection factory
        ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");

        //Create the consumer.
        Thread queueConsumerThread =
                new Thread(new QueueReceiver(connectionFactory));
        queueConsumerThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Create a message.
        Thread queueProducerThread =
                new Thread(new QueueSender(connectionFactory));
        queueProducerThread.start();
    }

}
