package com.zhongxingwang.center.user.web.dto;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/27 15:06
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 用户下单对象
 */
@Data
public class PlaceOrderReqDto implements Serializable {
    private Long productId;
    private Long userId;
    private Integer count;
}
