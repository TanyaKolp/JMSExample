import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;


/**
 * Created by tania on 20.03.17.
 */
public class ActiveMQMain {
    //    @Resource(lookup = "jms/ConnectionFactory")
//    private static ActiveMQConnectionFactory connectionFactory;
    public static void main(String[] args) {
        //Create the connection factory
        ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");

        //Create the consumer. It will wait to listen to the Topic
        String[] topics = new String[]{"CLIMATE", "TRAFFIC", "CLIMATE", "CLIMATE"};
        for (int i = 0; i < topics.length; i++) {
            Thread topicConsumerThread =
                    new Thread(new TopicConsumer(connectionFactory, topics[i]));
            topicConsumerThread.start();
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Create a message. As soon as the message is published on the Topic,
//it will be consumed by the consumer
        String[] texts = new String[] {"Today is Hot", "free"};
        for (int i = 0; i < texts.length; i++) {
            Thread topicProducerThread =
                    new Thread(new TopicProducer(connectionFactory,topics[i], texts[i]));
            topicProducerThread.start();
        }
    }

    public static void initLog() {
        System.setProperty(SimpleLogger.ROOT_LOGGER_NAME, "TRACE");

        final org.slf4j.Logger log = LoggerFactory.getLogger(ActiveMQMain.class);

        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warning");
        log.error("error");
    }
}
