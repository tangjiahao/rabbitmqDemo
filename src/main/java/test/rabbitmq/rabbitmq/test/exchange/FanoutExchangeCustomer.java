package test.rabbitmq.rabbitmq.test.exchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

/**
 * @author tangjing
 * @date 2021/01/21 17:07
 */
public class FanoutExchangeCustomer {
    public static void main(String[] args) throws Exception {
        //1 创建一个ConnectionFactory, 并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // connectionFactory.setHost("192.168.0.55");
        // connectionFactory.setPort(5672);
        // connectionFactory.setVirtualHost("/");
        // connectionFactory.setUsername("tangjing");
        // connectionFactory.setPassword("tangjing666");
        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //4 定义
        String exchangeName = "test_fanout_exchange";
        //5 指定类型为fanout
        String exchangeType = "fanout";
        String queueName = "test_fanout_queue";
        String queueName0 = "acequeue";
        //不设置路由键，没有路由情况下也能收到证明并不处理任何的路由键
        String routingKey = "";
        // 交换器名称，类型，是否持久（重启服务器数据不会丢失，存在了交换器），没有队列是否自动删除，是否内部使用，AMQP扩展参数
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.queueDeclare(queueName0, false, false, false, null);
        channel.queueBind(queueName0, exchangeName, routingKey);
        //5 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("消费端:" + msg);
            }
        };
        channel.basicConsume(queueName,true,consumer);
    }
}
