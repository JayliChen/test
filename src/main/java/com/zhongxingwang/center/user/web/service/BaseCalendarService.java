package com.zhongxingwang.center.user.web.service;

import com.zhongxingwang.center.user.web.entity.BaseCalendar;
import java.util.List;

/**
 * 用户订单表(BaseCalendar)表服务接口
 *
 * @author 陈杰攀、
 * @since 2020-04-29 17:45:17
 */
public interface BaseCalendarService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BaseCalendar queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<BaseCalendar> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param baseCalendar 实例对象
     * @return 实例对象
     */
    BaseCalendar insert(BaseCalendar baseCalendar);

    /**
     * 修改数据
     *
     * @param baseCalendar 实例对象
     * @return 实例对象
     */
    BaseCalendar update(BaseCalendar baseCalendar);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}