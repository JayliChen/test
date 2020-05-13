package com.zhongxingwang.center.user.web.service.impl;

import com.zhongxingwang.center.user.common.Enumeration;
import com.zhongxingwang.center.user.config.JmsConfig;
import com.zhongxingwang.center.user.rocketmq.base.ByteUtils;
import com.zhongxingwang.center.user.rocketmq.server.Producer;
import com.zhongxingwang.center.user.web.service.IMqProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/26 16:18
 */
@Service
public class MqProducerImpl implements IMqProducer {

    @Autowired
    private Producer producer;


    /**
     * 消息发送接口
     * @param tag
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public String sendMessage(String tag,Object data) throws Exception {
        Message message = new Message(JmsConfig.TOPIC,tag, ByteUtils.serialize(data));
        //发送
        SendResult sendResult = producer.getProducer().send(message);
        return sendResult.getMsgId();
    }

}
