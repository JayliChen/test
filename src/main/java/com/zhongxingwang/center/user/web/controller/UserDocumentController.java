package com.zhongxingwang.center.user.web.controller;

import com.zhongxingwang.center.user.web.dto.PlaceOrderReqDto;
import com.zhongxingwang.center.user.web.entity.UserDocument;
import com.zhongxingwang.center.user.web.service.UserDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户订单表(UserDocument)表控制层
 *
 * @author 陈杰攀、
 * @since 2020-04-27 16:00:58
 */
@RestController
@RequestMapping("userDocument")
@Api(tags = {"用户-订单接口"})
public class UserDocumentController {
    /**
     * 服务对象
     */
    @Autowired
    private UserDocumentService userDocumentService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserDocument selectOne(Long id) {
        return this.userDocumentService.queryById(id);
    }


    /**
     * 用户下单接口
     * @param placeOrderReqDto
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "用户下单接口")
    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
    public String placeOrder(@RequestBody @Valid PlaceOrderReqDto placeOrderReqDto) throws Exception {
        return userDocumentService.placeOrder(placeOrderReqDto);
    }

}