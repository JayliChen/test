package com.zhongxingwang.center.user.web.service.impl;

import com.zhongxingwang.center.user.redis.RedisUtil;
import com.zhongxingwang.center.user.web.entity.ProductEo;
import com.zhongxingwang.center.user.web.dao.ProductMapper;
import com.zhongxingwang.center.user.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品表(Product)表服务实现类
 *
 * @author 陈杰攀、
 * @since 2020-04-27 10:51:28
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisUtil  redisUtil;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProductEo queryById(Long id) {
        return this.productMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ProductEo> queryAllByLimit(int offset, int limit) {
        return this.productMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param productEo 实例对象
     * @return 实例对象
     */
    @Override
    public ProductEo insert(ProductEo productEo) {
        this.productMapper.insert(productEo);
        redisUtil.set(String.valueOf(productEo.getId()),Integer.valueOf(productEo.getCount()));
        return productEo;
    }

    /**
     * 修改数据
     *
     * @param productEo 实例对象
     * @return 实例对象
     */
    @Override
    public ProductEo update(ProductEo productEo) {
        this.productMapper.update(productEo);
        return this.queryById(productEo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.productMapper.deleteById(id) > 0;
    }
}