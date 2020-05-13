package com.zhongxingwang.center.user.web.controller;

import com.github.pagehelper.PageInfo;
import com.zhongxingwang.center.user.web.dto.*;
import com.zhongxingwang.center.user.web.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@Api(tags = {"用户接口"})
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    /**
     * 新增用户
     *
     * @param userDto
     * @return
     */
    @ApiOperation(value = "新增用户")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody @Valid UserDto userDto) {
        return String.valueOf(userService.add(userDto));
    }

    /**
     * 根据组建id删除用户
     * @param id
     */
    @ApiOperation(value = "根据主键id删除用户信息")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(@RequestParam("id") Long id) {
        userService.delete(id);
    }

    /**
     * 根据主键id获取用户信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据主键id获取用户信息")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public UserDetailRespDto detail(@RequestParam("id") Long id) {
        return userService.detail(id);
    }

    /**
     * 根据主键id跟新传进来的用户信息
     *
     * @param userUpdateReqDto
     */
    @ApiOperation(value = "根据主键id更新传进来的用户信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestBody @Valid UserUpdateReqDto userUpdateReqDto) {
        userService.update(userUpdateReqDto);
    }

    /**
     * 分页查询用户列表
     *
     * @param userPageInfoReqDto
     * @return
     */
    @ApiOperation(value = "分页查询用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public PageInfo<UserPageInfoRespDto> list(@RequestBody UserPageInfoReqDto userPageInfoReqDto) {
        return userService.pageList(userPageInfoReqDto);
    }

    @ApiOperation(value = "导入用户数据")
    @RequestMapping(value = "/importUser", method = RequestMethod.POST)
    public ExcelImportResultView importUser(@ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile file) throws Exception {
        return userService.importUser(file);
    }

    /**
     * 导出用户导入模板
     * @param
     * @throws Exception
     */
    @ApiOperation(value = "导出用户导入模板")
    @RequestMapping(value = "/downLoadUserTemplate", produces = "application/octet-stream", method = RequestMethod.GET)
    public void downLoadUserTemplate() throws Exception {
        userService.downLoadUserTemplate();
    }

    @ApiOperation(value = "导出用户导入异常数据")
    @RequestMapping(value = "/downLoadError", produces = "application/octet-stream", method = RequestMethod.GET)
    public void downLoadError(@RequestParam @ApiParam(name = "errorToken", value = "导入失败数据的唯一标识", required = true) String errorToken) throws Exception {
        userService.downLoadError(errorToken);
    }

    /**
     * 导出用户数据
     *
     * @param userListReqDto
     * @throws Exception
     */
    @ApiOperation(value = "导出用户数据")
    @RequestMapping(value = "/downLoadUser", produces = "application/octet-stream", method = RequestMethod.GET)
    public void downLoadUser(UserListReqDto userListReqDto) throws Exception {
        userService.downLoadUser(userListReqDto);
    }


    /**
     * 序列化接口
     *
     * @param userDto
     * @return
     * @throws IOException
     */
//    @ApiOperation(value = "序列化接口")
//    @RequestMapping(value = "/serializable", method = RequestMethod.POST)
//    public Long serializable(@RequestBody @Valid UserDto userDto) throws IOException {
//        return userService.serializable(userDto);
//    }


    /**
     * 测试消息推送与消费
     * @param userDto
     * @throws Exception
     */
//    @ApiOperation(value = "消息队列测试接口")
//    @RequestMapping(value = "/sendMq", method = RequestMethod.POST)
//    public String sendMq(@RequestBody @Valid UserDto userDto) throws Exception {
//       return userService.send(userDto);
//    }

}
