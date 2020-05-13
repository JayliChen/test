package com.zhongxingwang.center.user.rocketmq.server;

import com.zhongxingwang.center.user.common.Enumeration;
import com.zhongxingwang.center.user.config.JmsConfig;
import com.zhongxingwang.center.user.rocketmq.base.ByteUtils;
import com.zhongxingwang.center.user.rocketmq.process.UserDetailProcess;
import com.zhongxingwang.center.user.rocketmq.process.UserPlaceOrderProcess;
import com.zhongxingwang.center.user.web.service.IMessageProcessor;
import com.zhongxingwang.center.user.rocketmq.base.ProcessorMapDto;
import com.zhongxingwang.center.user.web.service.impl.UserServiceImpl;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/26 11:40
 */


@Component
public class Consumer {

 private static final Logger log = LoggerFactory.getLogger(Consumer.class);
    /**
     * 消费者实体对象
     */
    private DefaultMQPushConsumer consumer;
    /**
     * 消费者组
     */
    public static final String CONSUMER_GROUP = "test_consumer";
    /**
     * 通过构造函数 实例化对象
     */
    public Consumer() throws MQClientException {

        //设置不同业务对应不同的消息tag,用于消息回调
        ProcessorMapDto.addMessageProcessorMap(Enumeration.USER_MQ_TAG,new UserDetailProcess());
        ProcessorMapDto.addMessageProcessorMap(Enumeration.USER_PLACE_ORDER,new UserPlaceOrderProcess());

        final Map<String, IMessageProcessor> messageProcessorMap = ProcessorMapDto.getMessageProcessorMap();
        consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        consumer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        String tags = getSubExpression(messageProcessorMap.keySet().toArray());
        consumer.subscribe(JmsConfig.TOPIC, tags);
        // //注册消费的监听 并在此监听中消费信息，并返回消费的状态信息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            // msgs中只收集同一个topic，同一个tag，并且key相同的message
            // 会把不同的消息分别放置到不同的队列中
            for (Message msg : msgs) {
                String result = ((IMessageProcessor)messageProcessorMap.get(msg.getTags())).process(ByteUtils.deSerialize(msg.getBody()));
                log.info("Consumer-获取消息-主题topic为={}, 消费消息为={},消费结果为={}", msg.getTopic(), ByteUtils.deSerialize(msg.getBody()),result);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        System.out.println("消费者 启动成功=======");
    }

    public static String getSubExpression(Object[] routingKeys) {
        StringBuilder sb = new StringBuilder();
        if(routingKeys != null) {
            for(int i = 0; i < routingKeys.length; ++i) {
                if(i == routingKeys.length - 1) {
                    sb.append(routingKeys[i]);
                } else {
                    sb.append(routingKeys[i]).append("||");
                }
            }
        }
        return sb.toString();
    }
}
