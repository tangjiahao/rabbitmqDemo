package test.rabbitmq.rabbitmq.test.directExchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author tangjing
 * @date 2021/01/22 10:38
 */
public class DirectExchangeCustomer2 {
    public static void main(String[] args) throws Exception {
        //1 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory() ;
        //2 创建Connection
        Connection connection = connectionFactory.newConnection();
        //3 创建Channel
        Channel channel = connection.createChannel();
        //4 声明
        String exchangeName = "test_direct_exchange";
        //定义为直连
        String exchangeType = "direct";
        //定义队列名
        String queueName = "queue2";
        //设置routingKey 也可以说是 bindingKey 很多时候我们可以理解成同一个东西
        String routingKey = "test.direct2";
        //表示声明了一个交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false, false, null);
        //表示声明了一个队列
        channel.queueDeclare(queueName, false, false, false, null);
        //建立一个绑定关系:
        channel.queueBind(queueName, exchangeName, routingKey);
        //5 创建消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println(queueName+"消费端:" + msg);
            }
        };
        //参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queueName, true, consumer);

        // 思考题，一个队列能否获得两个队列获得的所有消息？提示：routingkey
    }
}
