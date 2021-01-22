package test.rabbitmq.rabbitmq.test.topicExchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author tangjing
 * @date 2021/01/22 14:26
 */
public class TopicExchangeCustomer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "topic_exchange";
        String queueName = "topic_queue";
        //因为*号代表匹配一个单词，生产者中routingKey3将匹配不到,#可以匹配多规格和零个
        //String bindKey = "ace.*";
        String bindKey = "ace.#";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true, false, false, null);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, exchangeName, bindKey);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("消费端:" + msg);
            }
        };
        //参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queueName, true, consumer);
    }


}
