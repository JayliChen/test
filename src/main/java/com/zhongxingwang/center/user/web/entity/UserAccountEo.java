package com.zhongxingwang.center.user.web.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户账户信息表(UserAccount)实体类
 *
 * @author 陈杰攀、
 * @since 2020-04-27 10:57:36
 */
@Data
public class UserAccountEo extends BaseEo implements Serializable {
    private static final long serialVersionUID = 828823838846261070L;

    /**
     * 用户表id
     */
    private Long parentId;
    /**
     * 银行卡号
     */
    private String bankCardNumber;
    /**
     * 余额
     */
    private BigDecimal balance;

}