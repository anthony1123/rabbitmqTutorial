package org.example.xcRabbitMq.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerFanout {
    public static void main(String[] args) throws IOException, TimeoutException {
        String exchangeFanoutName = "xc_exchange_fanout_name";
        String queueName_1 = "xc_queue_name_fanout_1";
        String queueName_2 = "xc_queue_name_fanout_2";
        String queueName_3 = "xc_queue_name_fanout_3";
        String queueName_4 = "xc_queue_name_fanout_4";
        String key_1 = "key_1";
        String key_2 = "key_2";
        String key_3 = "key_3";
        String key_4 = "key_4";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 创建交换机
        channel.exchangeDeclare(exchangeFanoutName, BuiltinExchangeType.FANOUT, true, false, null);

        // 创建队列
        channel.queueDeclare(queueName_1, true, false, false, null);
        channel.queueDeclare(queueName_2, true, false, false, null);
        channel.queueDeclare(queueName_3, true, false, false, null);
        channel.queueDeclare(queueName_4, true, false, false, null);

        // 将交换机与队列进行绑定

        channel.queueBind(queueName_1, exchangeFanoutName, key_1);
        channel.queueBind(queueName_2, exchangeFanoutName, key_1);
        channel.queueBind(queueName_3, exchangeFanoutName, key_3);
        channel.queueBind(queueName_4, exchangeFanoutName, key_4);

        /*
         * 发送信息
         * 1. 发送到那个交换机
         * 2. 队列名称，直连模式下可以直接用路由键进行代替
         * 3. 其他参数信息
         * 4. 发送消息的消息体，字节流
         */
        channel.basicPublish(exchangeFanoutName, key_1,null, "这是key_1的消息".getBytes());


        channel.close();
        connection.close();
    }
}
