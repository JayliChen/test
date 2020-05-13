package com.zhongxingwang.center.user.web.controller;

import com.zhongxingwang.center.user.redis.RedisUtil;
import com.zhongxingwang.center.user.web.entity.ProductEo;
import com.zhongxingwang.center.user.web.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 商品表(Product)表控制层
 *
 * @author 陈杰攀、
 * @since 2020-04-27 10:51:28
 */
@RestController
@RequestMapping("product")
@Api(tags = {"商品接口"})
public class ProductController {
    /**
     * 服务对象
     */
    @Resource
    private ProductService productService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    @ApiOperation(value = "通过主键查询单条数据")
    public ProductEo selectOne(Long id) {
        return this.productService.queryById(id);
    }


    /**
     * 新增商品
     * @param productEo
     * @return
     */
    @ApiOperation(value = "新增商品")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public  String  insert(@RequestBody ProductEo productEo) {
       return String.valueOf(this.productService.insert(productEo).getId());
    }

}