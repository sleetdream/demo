package com.example.rabbitmqredis.listener;

import com.example.rabbitmqredis.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class CustomerRabbitListener {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

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
            //ex.printStackTrace();
            //被拒绝的是否重新入队列
            boolean requeueFlag = true;
            //消息标识
            String consumerTag = message.getMessageProperties().getConsumerTag();
            //重试次数
            String requeueTimes = redisTemplate.opsForValue().get("rabbitmqredis:requeuetimes:"+ consumerTag);
            if(StringUtils.isEmpty(requeueTimes)){
                //首次重试
                redisTemplate.opsForValue().set("rabbitmqredis:requeuetimes:"+ consumerTag,"1");
            }
            else{
                //重试次数限定
                Integer times = Integer.valueOf(requeueTimes);
                if(times < 3){
                    //在重试次数范围内
                    redisTemplate.opsForValue().set("rabbitmqredis:requeuetimes:"+ consumerTag,String.valueOf(times+1));
                }
                else{
                    //超出重试次数
                    requeueFlag = false;
                    redisTemplate.delete("rabbitmqredis:requeuetimes:"+ consumerTag);
                }
            }
            //拒绝消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, requeueFlag);
            System.out.println("Listener:" + RabbitMQConfig.ORDER_QUEUE + " Nack Message:" + message.toString());
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
