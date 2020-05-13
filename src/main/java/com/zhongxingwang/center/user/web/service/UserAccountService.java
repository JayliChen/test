package com.zhongxingwang.center.user.web.service;

import com.zhongxingwang.center.user.web.entity.UserAccountEo;
import java.util.List;

/**
 * 用户账户信息表(UserAccount)表服务接口
 *
 * @author 陈杰攀、
 * @since 2020-04-27 10:57:36
 */
public interface UserAccountService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserAccountEo queryByParentId(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserAccountEo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userAccountEo 实例对象
     * @return 实例对象
     */
    UserAccountEo insert(UserAccountEo userAccountEo);

    /**
     * 修改数据
     *
     * @param userAccountEo 实例对象
     * @return 实例对象
     */
    UserAccountEo update(UserAccountEo userAccountEo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}