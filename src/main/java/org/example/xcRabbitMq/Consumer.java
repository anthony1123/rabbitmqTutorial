package org.example.xcRabbitMq;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置服务地址
        factory.setHost("localhost");
        // 设置账号名
        factory.setUsername("guest");
        // 设置账号密码
        factory.setPassword("guest");
        // 端口号
        factory.setPort(5672);
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("接收到消息，消息内容为："+new String(message.getBody()));
        };
        CancelCallback cancelCallback = (consumerTag) ->{
            System.out.println("消费消息被中断");
        };

        /*
         * 消费消息
         * 1. 消费哪个队列
         * 2. 消费成功之后是否需要自动应答
         * 3. 接受消息的回调函数
         * 4. 取消消息的回调函数
         */
        channel.basicConsume("xc_queue_name",true, deliverCallback, cancelCallback);

    }
}
