package com.zhongxingwang.center.user.web.dao;

import com.zhongxingwang.center.user.web.entity.UserAccountEo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户账户信息表(UserAccount)表数据库访问层
 *
 * @author 陈杰攀、
 * @since 2020-04-27 10:57:36
 */
@Mapper
@Repository
public interface UserAccountMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserAccountEo queryByParentId(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserAccountEo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userAccountEo 实例对象
     * @return 对象列表
     */
    List<UserAccountEo> queryAll(UserAccountEo userAccountEo);

    /**
     * 新增数据
     *
     * @param userAccountEo 实例对象
     * @return 影响行数
     */
    int insert(UserAccountEo userAccountEo);

    /**
     * 批量新增账户
     * @param userAccountEoList
     * @return
     */
    int  insertBatch(List<UserAccountEo> userAccountEoList);

    /**
     * 修改数据
     *
     * @param userAccountEo 实例对象
     * @return 影响行数
     */
    int update(UserAccountEo userAccountEo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}