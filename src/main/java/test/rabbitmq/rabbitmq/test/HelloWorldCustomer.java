package test.rabbitmq.rabbitmq.test;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author tangjing
 * @date 2021/01/19 14:35
 */
public class HelloWorldCustomer {
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
        //3 通过connection创建一个Channel
        Channel channel = connection.createChannel();
        //4 声明（创建）一个队列
        String queueName = "test001";
        channel.queueDeclare(queueName, true, false, false, null);
        //5 创建消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("消费端:" + msg);
            }
        };
        //6 订阅消息
        channel.basicConsume(queueName, true, consumer);


    }
}
