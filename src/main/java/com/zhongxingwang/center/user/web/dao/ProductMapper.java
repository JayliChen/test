package com.zhongxingwang.center.user.web.dao;

import com.zhongxingwang.center.user.web.entity.ProductEo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品表(Product)表数据库访问层
 *
 * @author 陈杰攀、
 * @since 2020-04-27 10:51:28
 */
@Mapper
@Repository
public interface ProductMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProductEo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ProductEo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param productEo 实例对象
     * @return 对象列表
     */
    List<ProductEo> queryAll(ProductEo productEo);

    /**
     * 新增数据
     *
     * @param productEo 实例对象
     * @return 影响行数
     */
    int insert(ProductEo productEo);

    /**
     * 修改数据
     *
     * @param productEo 实例对象
     * @return 影响行数
     */
    int update(ProductEo productEo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}