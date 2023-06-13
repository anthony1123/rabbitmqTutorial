package org.example.rabbitMqOffical.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] argv) throws Exception {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); //设置主机名
        try (Connection connection = factory.newConnection(); //创建连接
             Channel channel = connection.createChannel()) { //创建通道
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello Worl2313d!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}