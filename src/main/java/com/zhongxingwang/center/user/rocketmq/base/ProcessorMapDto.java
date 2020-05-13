package com.zhongxingwang.center.user.rocketmq.base;

import com.zhongxingwang.center.user.web.service.IMessageProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/26 15:45
 */
public class ProcessorMapDto {

    public static Map<String, IMessageProcessor> messageProcessorMap = new HashMap<>();
    public static Map<String,IMessageProcessor> getMessageProcessorMap(){
        return messageProcessorMap;
    }
    public static void setMessageProcessorMap(Map<String,IMessageProcessor> messageProcessorMap){
        ProcessorMapDto.messageProcessorMap = messageProcessorMap;
    }
    public static void addMessageProcessorMap(String tag,IMessageProcessor messageProcessor){
        messageProcessorMap.put(tag,messageProcessor);
    }
}
