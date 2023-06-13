package org.example.xcRabbitMq.headers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ProducerHeaders {
    public static void main(String[] args) throws IOException, TimeoutException {
        String exchangeName = "xc_exchange_headers_name";
        String queueName_1 = "xc_queue_name_headers_1";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 创建交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.HEADERS, true, false, null);


        // 创建队列
        channel.queueDeclare(queueName_1, true, false, false, null);

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("name","xiaochuanxx");
        headerMap.put("sex","male");
        AMQP.BasicProperties.Builder properities = new AMQP.BasicProperties().builder().headers(headerMap);

        channel.basicPublish(exchangeName,"",properities.build(),"all message".getBytes());
        channel.close();
        connection.close();
        System.out.println("消息发送成功");
    }
}
