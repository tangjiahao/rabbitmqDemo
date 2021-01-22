package test.rabbitmq.rabbitmq.test.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * fanout类型的交换机，把所有发送到该交换器的消息路由到所有与该交换器绑定的队列中，不处理路由
 *
 * @author tangjing
 * @date 2021/01/21 17:04
 */
public class FanoutExchangeProducer {
    public static void main(String[] args) throws Exception {
        //1 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 也可通过yml文件设置，不需要每次设置
        // connectionFactory.setHost("192.168.0.55");
        // connectionFactory.setPort(5672);
        // connectionFactory.setVirtualHost("/");
        // connectionFactory.setUsername("tangjing");
        // connectionFactory.setPassword("tangjing666");
        //2 创建Connection
        Connection connection = connectionFactory.newConnection();
        //3 创建Channel
        Channel channel = connection.createChannel();
        //4 声明
        String exchangeName = "test_fanout_exchange";
        //5 发送
        for(int i = 0; i < 10; i ++) {
            String msg = "Test Fanout Exchange Message"+i+System.currentTimeMillis();
            channel.basicPublish(exchangeName, "", null , msg.getBytes());
        }
        channel.close();
        connection.close();
    }
}
