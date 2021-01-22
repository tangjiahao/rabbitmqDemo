package test.rabbitmq.rabbitmq.test.topicExchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author tangjing
 * @date 2021/01/22 14:26
 */
public class TopicExchangeProuducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "topic_exchange";
        // 定义三个routingKey 其中第三个我们再消费者中故意匹配不成功
        String rountKey1 = "ace.save";
        String rountKey2 = "ace.update";
        String rountKey3 = "ace.select.one";
        String msg = "test topic exchange msg - ";
        channel.basicPublish(exchangeName, rountKey1, null, (msg + rountKey1).getBytes());
        channel.basicPublish(exchangeName, rountKey2, null, (msg + rountKey2).getBytes());
        channel.basicPublish(exchangeName, rountKey3, null, (msg + rountKey3).getBytes());

    }
}
