package com.zhongxingwang.center.user.web.controller;


import com.zhongxingwang.center.user.web.entity.UserAccountEo;
import com.zhongxingwang.center.user.web.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户账户信息表(UserAccount)表控制层
 *
 * @author 陈杰攀、
 * @since 2020-04-27 10:57:36
 */
@RestController
@RequestMapping("userAccount")
@Api(tags = {"用户账户接口"})
public class UserAccountController {
    /**
     * 服务对象
     */
    @Resource
    private UserAccountService userAccountService;

    /**
     * 新增账户
     * @param userAccountEo
     * @return
     */
    @ApiOperation(value = "新增账户")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public UserAccountEo insert(@RequestBody @Valid UserAccountEo userAccountEo) {
        return userAccountService.insert(userAccountEo);
    }

}