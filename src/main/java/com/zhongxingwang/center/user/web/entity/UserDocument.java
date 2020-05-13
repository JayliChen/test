package com.zhongxingwang.center.user.web.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户订单表(UserDocument)实体类
 *
 * @author 陈杰攀、
 * @since 2020-04-27 16:00:58
 */
@Data
public class UserDocument extends BaseEo implements Serializable {
    private static final long serialVersionUID = -78501131720851661L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 单价
     */
    private BigDecimal priceUnit;


}