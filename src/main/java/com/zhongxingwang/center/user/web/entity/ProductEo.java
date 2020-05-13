package com.zhongxingwang.center.user.web.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 商品表(Product)实体类
 *
 * @author 陈杰攀、
 * @since 2020-04-27 10:51:28
 */
@Data
public class ProductEo extends BaseEo implements Serializable {
    private static final long serialVersionUID = 137552138578995625L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 库存
     */
    private String count;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 余额
     */
    private BigDecimal price;

}