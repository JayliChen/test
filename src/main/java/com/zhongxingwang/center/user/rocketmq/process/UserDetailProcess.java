package com.zhongxingwang.center.user.rocketmq.process;

import com.zhongxingwang.center.user.web.dto.UserDto;
import com.zhongxingwang.center.user.web.service.IMessageProcessor;
import com.zhongxingwang.center.user.web.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/27 15:21
 */
public class UserDetailProcess implements IMessageProcessor<UserDto> {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailProcess.class);
    @Override
    public String process(UserDto userDto) {
        logger.info("消息消费成功={}",userDto);
        return "success";
    }
}
