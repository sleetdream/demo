package com.example.rabbitmqredis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    /*订单交换机*/
    public final static String ORDER_EXCHANGE = "order_exchange";

    /*订单队列*/
    public final static String ORDER_QUEUE = "order_queue";
    /*失败队列，用于保存出错信息*/
    public final static String FAIL_QUEUE = "fail_queue";


    /**
     *Queue 可以有4个参数
     *      1.队列名
     *      2.durable       持久化消息队列 ,rabbitmq重启的时候不需要创建新的队列 默认true
     *      3.exclusive     表示该消息队列是否只在当前connection生效,默认是false
     *      4.auto-delete   表示消息队列没有在使用时将被自动删除 默认是false
     */

    /**
     * 定义订单队列
     * @return
     */
    @Bean
    public Queue orderQueue(){
        Map<String, Object> args = new HashMap<>();
        //       x-dead-letter-exchange    声明  死信队列Exchange
        args.put("x-dead-letter-exchange", ORDER_EXCHANGE);
        //       x-dead-letter-routing-key    声明 死信队列抛出异常重定向队列的routingKey(TKEY_R)
        args.put("x-dead-letter-routing-key", FAIL_QUEUE);
        Queue queue = new Queue(ORDER_QUEUE,true,false,false, args);
        return queue;
    }


    /**
     * 定义转发队列
     * @return
     */
    @Bean
    public Queue failQueue(){
        Queue queue = new Queue(FAIL_QUEUE,true,false,false);
        return queue;
    }

    /**
     * 定义订单交换机
     * @return
     */
    @Bean
    public TopicExchange orderExchange(){
        //属性参数 交换机名称 是否持久化 是否自动删除 配置参数
        return new TopicExchange(ORDER_EXCHANGE, true, false);
    }

    /**
     * 绑定订单队列到交换机
     * @return
     */
    @Bean
    public Binding BindingOrder(){
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ORDER_QUEUE);
    }

    /**
     * 绑定延迟队列到交换机
     * @return
     */
    @Bean
    public Binding BindingDelay(){ return BindingBuilder.bind(failQueue()).to(orderExchange()).with(FAIL_QUEUE); }


    /* ======================== 定制一些处理策略 =============================*/
    /**
     * 定制化amqp模版
     *
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
     * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        Logger log = LoggerFactory.getLogger(RabbitTemplate.class);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 消息发送失败返回到队列中, yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);

        // 消息返回, yml需要配置 publisher-returns: true
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            //log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
            System.out.println("消息："+correlationId+" 发送失败, 应答码："+replyCode+" 原因："+replyText+" 交换机: "+exchange+"  路由键: "+routingKey);
        });

        // 消息确认, yml需要配置 publisher-confirms: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                //log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
                System.out.println("消息发送到exchange成功,id: "+correlationData.getId());
            } else {
                //log.debug("消息发送到exchange失败,原因: {}", cause);
                System.out.println("消息发送到exchange失败,原因: "+cause);
            }
        });

        return rabbitTemplate;
    }
}

