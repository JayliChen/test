package com.zhongxingwang.center.user.web.service.impl;

import com.zhongxingwang.center.user.web.entity.UserAccountEo;
import com.zhongxingwang.center.user.web.dao.UserAccountMapper;
import com.zhongxingwang.center.user.web.service.UserAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户账户信息表(UserAccount)表服务实现类
 *
 * @author 陈杰攀、
 * @since 2020-04-27 10:57:36
 */
@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {
    @Resource
    private UserAccountMapper userAccountMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserAccountEo queryByParentId(Long id) {
        return this.userAccountMapper.queryByParentId(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserAccountEo> queryAllByLimit(int offset, int limit) {
        return this.userAccountMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userAccountEo 实例对象
     * @return 实例对象
     */
    @Override
    public UserAccountEo insert(UserAccountEo userAccountEo) {
        this.userAccountMapper.insert(userAccountEo);
        return userAccountEo;
    }

    /**
     * 修改数据
     *
     * @param userAccountEo 实例对象
     * @return 实例对象
     */
    @Override
    public UserAccountEo update(UserAccountEo userAccountEo) {
        this.userAccountMapper.update(userAccountEo);
        return this.queryByParentId(userAccountEo.getParentId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.userAccountMapper.deleteById(id) > 0;
    }
}