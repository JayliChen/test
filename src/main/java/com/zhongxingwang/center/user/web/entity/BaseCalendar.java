package com.zhongxingwang.center.user.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户订单表(BaseCalendar)实体类
 *
 * @author 陈杰攀、
 * @since 2020-04-29 17:45:17
 */

@Data
public class BaseCalendar implements Serializable {
    private static final long serialVersionUID = -28014003879016617L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 没有-的yyyymmdd
     */
    private Integer yearToDay;
    /**
     * 有-的yyyy-MM-dd
     */
    private String yearMonthDay;
    /**
     * 年份
     */
    private Integer baseYear;
    /**
     * 月份
     */
    private Integer baseMonth;
    /**
     * 天
     */
    private Integer baseDay;
    /**
     * 周几
     */
    private Integer baseWeekday;
    /**
     * 类型（0工作日1周末2节假日）
     */
    private Integer baseType;



}