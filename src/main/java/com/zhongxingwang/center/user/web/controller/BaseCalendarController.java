package com.zhongxingwang.center.user.web.controller;

import com.zhongxingwang.center.user.web.entity.BaseCalendar;
import com.zhongxingwang.center.user.web.service.BaseCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户订单表(BaseCalendar)表控制层
 *
 * @author 陈杰攀、
 * @since 2020-04-29 17:45:17
 */
@RestController
@RequestMapping("baseCalendar")
public class BaseCalendarController {
    /**
     * 服务对象
     */
    @Autowired
    private BaseCalendarService baseCalendarService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public BaseCalendar selectOne(Long id) {
        return this.baseCalendarService.queryById(id);
    }

}