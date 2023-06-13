package org.example.xcRabbitMq.topic;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerTopic {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("接收到消息，消息内容为："+new String(message.getBody()));
        };

        CancelCallback cancelCallback = (consumerTag) ->{
            System.out.println("消费消息被中断");
        };

        channel.basicConsume("xc_queue_name_topic_1",true, deliverCallback, cancelCallback);
        channel.basicConsume("xc_queue_name_topic_2",true, deliverCallback, cancelCallback);
        channel.basicConsume("xc_queue_name_topic_3",true, deliverCallback, cancelCallback);
        channel.basicConsume("xc_queue_name_topic_4",true, deliverCallback, cancelCallback);
    }
}
