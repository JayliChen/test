package com.zhongxingwang.center.user.rocketmq.process;

import com.zhongxingwang.center.user.config.SpringUtil;
import com.zhongxingwang.center.user.web.dto.PlaceOrderReqDto;
import com.zhongxingwang.center.user.web.service.IMessageProcessor;
import com.zhongxingwang.center.user.web.service.impl.UserDocumentServiceImpl;
import com.zhongxingwang.center.user.web.service.impl.UserServiceImpl;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/27 15:29
 */
public class UserPlaceOrderProcess implements IMessageProcessor<PlaceOrderReqDto> {

    /**
     * 用户下单抢购做后续操作
     * @param placeOrderReqDto
     * @return
     */
    @Override
    public String process(PlaceOrderReqDto placeOrderReqDto) {
        UserDocumentServiceImpl userDocumentService = SpringUtil.getBean(UserDocumentServiceImpl.class);
        userDocumentService.placeOrderCallBack(placeOrderReqDto);
        return "success";
    }
}
