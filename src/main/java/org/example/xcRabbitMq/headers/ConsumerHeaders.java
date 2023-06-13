package org.example.xcRabbitMq.headers;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ConsumerHeaders {
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

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("x-match","any");
        headerMap.put("name","xiaochuan");
        headerMap.put("sex","male");

        channel.queueBind("xc_queue_name_headers_1", "xc_exchange_headers_name", "", headerMap);

        channel.basicConsume("xc_queue_name_headers_1",true,deliverCallback,cancelCallback);
    }
}
