package com.zhongxingwang.center.user.web.service;

import com.zhongxingwang.center.user.web.entity.ProductEo;

import java.util.List;

/**
 * 商品表(Product)表服务接口
 *
 * @author 陈杰攀、
 * @since 2020-04-27 10:51:28
 */
public interface ProductService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProductEo queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ProductEo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param productEo 实例对象
     * @return 实例对象
     */
    ProductEo insert(ProductEo productEo);

    /**
     * 修改数据
     *
     * @param productEo 实例对象
     * @return 实例对象
     */
    ProductEo update(ProductEo productEo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}