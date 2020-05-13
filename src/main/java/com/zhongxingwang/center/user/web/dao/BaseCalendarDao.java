package com.zhongxingwang.center.user.web.dao;

import com.zhongxingwang.center.user.web.entity.BaseCalendar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户订单表(BaseCalendar)表数据库访问层
 *
 * @author 陈杰攀、
 * @since 2020-04-29 17:45:17
 */
//@Mapper
//@Repository
public interface BaseCalendarDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BaseCalendar queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<BaseCalendar> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param baseCalendar 实例对象
     * @return 对象列表
     */
    List<BaseCalendar> queryAll(BaseCalendar baseCalendar);

    /**
     * 新增数据
     *
     * @param baseCalendar 实例对象
     * @return 影响行数
     */
    int insert(BaseCalendar baseCalendar);

    /**
     * 批量新增
     * @param baseCalendarList
     * @return
     */
    int insertBatch(List<BaseCalendar> baseCalendarList);

    /**
     * 修改数据
     *
     * @param baseCalendar 实例对象
     * @return 影响行数
     */
    int update(BaseCalendar baseCalendar);

    /**
     * 根据yyyyMMdd批量更新节假日状态
     * @param baseCalendarList
     * @return
     */
    int updateBatchByYearToDay(List<BaseCalendar> baseCalendarList);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}