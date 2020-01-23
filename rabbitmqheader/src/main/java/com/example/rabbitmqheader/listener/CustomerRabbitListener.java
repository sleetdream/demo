package com.example.rabbitmqheader.listener;


import com.example.rabbitmqheader.config.RabbitMQConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomerRabbitListener {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 监听订单队列
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void orderListener(Message message, Channel channel) throws IOException{
        try{
            int a = 0/0;//构造出错信息
            //应答消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("Listener:"+RabbitMQConfig.ORDER_QUEUE+" Ack Message:"+message.toString());
        } catch (Exception ex){
            //重试次数
            String retryTimes = (String) message.getMessageProperties().getHeaders().get("retryTimes");
            if(StringUtils.isEmpty(retryTimes)){
                //首次重试
                message.getMessageProperties().getHeaders().put("retryTimes","1");
            }
            else{
                //重试次数限定
                Integer times = Integer.valueOf(retryTimes);
                if(times < 3){
                    //在重试次数范围内
                    message.getMessageProperties().getHeaders().put("retryTimes",String.valueOf(times+1));
                }
                else{
                    //超出重试次数
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
                    System.out.println("Listener:" + RabbitMQConfig.ORDER_QUEUE + " Nack Message:" + message.toString());
                    return;
                }
            }


            //重发消息
            rabbitTemplate.convertAndSend(message.getMessageProperties().getReceivedExchange(), message.getMessageProperties().getReceivedRoutingKey(), message);
            //应答消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("Listener:" + RabbitMQConfig.ORDER_QUEUE + " Publish Message:" + message.toString());
        }
    }

    /**
     * 监听失败队列
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMQConfig.FAIL_QUEUE)
    public void failListener(Message message, Channel channel) throws IOException {
        try{

            /*
            * 将失败消息存入数据库
            * ......
            */

            /**
             * basicAck 消息应答
             * deliveryTag:该消息的index
             * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("Listener:" + RabbitMQConfig.FAIL_QUEUE + " Ack Message:" + message.toString());
        }catch (Exception ex){
            /**
             * basicNack 消息拒绝
             * deliveryTag:该消息的index
             * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
             * requeue：被拒绝的是否重新入队列
             */
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
        }

    }

}
