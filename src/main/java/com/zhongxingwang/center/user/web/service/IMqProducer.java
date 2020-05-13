package com.zhongxingwang.center.user.web.service;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/26 16:17
 */
public interface IMqProducer {
    /**
     * 消息发送接口
     * @param tag
     * @param data
     * @return
     * @throws Exception
     */
    String sendMessage(String tag,Object data) throws Exception;
}
