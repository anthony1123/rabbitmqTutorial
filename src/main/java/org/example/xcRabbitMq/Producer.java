package org.example.xcRabbitMq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static String exchangeName = "xc_exchange_name";
    public static String queueName = "xc_queue_name";
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
        /* 创建交换机
         * 1. 交换机名称
         * 2. 交换机类型，direct，topic，fanout，和readers
         * 3. 制定交换机是否要持久化，若设置为true，则交换机的元数据需要持久化
         * 4. 制定交换机在没有队列绑定时，是否需要删除，设置为false表示不删除
         * 5. Map<String, Onject>类型，用来指定我们交换机其他的一些结构化承诺书，我们在这里设置为null
         */
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false, null);
        /*
         * 生成一个队列
         * 1. 队列名称
         * 2. 队列的元数据是否需要持久化，队列名称等元数据的持久化
         * 3. 表示队列是不是私有的，如果是私有的，只有创建他的应用程序才能消费消息
         * 4. 队列在没有消费者订阅的情况下是否自动删除
         * 5. 队列的一些结构化数据，比如声明死信队列，磁盘队列会用到
         */
        channel.queueDeclare(queueName, true, false, false, null);
        /*
         * 将交换机与队列进行绑定
         * 1. 队列名称
         * 2. 交换机名称
         * 3. 路由键，在我们的直连模式下，可以为队列名称
         */
        channel.queueBind(queueName,exchangeName,queueName);

        // 发送信息
        String message = "hello rabbitmq";
        /*
         * 发送信息
         * 1. 发送到那个交换机
         * 2. 队列名称
         * 3. 其他参数信息
         * 4. 发送消息的消息体，字节流
         */
        channel.basicPublish(exchangeName,queueName,null, message.getBytes());
        channel.close();
        connection.close();
    }
}
