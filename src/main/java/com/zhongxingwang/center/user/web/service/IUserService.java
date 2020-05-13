package com.zhongxingwang.center.user.web.service;

import com.zhongxingwang.center.user.web.dto.*;
import com.zhongxingwang.center.user.web.entity.UserEo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {


    /**
     * 新增用户
     * @param userDto
     * @return
     */
    Long add(UserDto userDto);

    /**
     * 根据组建id删除用户
     * @param id
     */
    void delete(Long id);

    /**
     * 根据主键id获取用户信息
     * @param id
     * @return
     */
    UserDetailRespDto detail(Long id);

    /**
     * 根据主键id跟新传进来的用户信息
     * @param userUpdateReqDto
     */
    void update(UserUpdateReqDto userUpdateReqDto);

    /**
     * 分页查询用户列表
     * @param userPageInfoReqDto
     * @return
     */
    PageInfo<UserPageInfoRespDto> pageList(UserPageInfoReqDto userPageInfoReqDto);

    /**
     * 分页查询用户列表
     * @param userListReqDto
     * @return
     */
    List<UserPageInfoRespDto> list(UserListReqDto userListReqDto);

    /**
     * 批量导入用户
     * @param file
     */
    ExcelImportResultView importUser( MultipartFile file ) throws Exception;

    /**
     * 下载用户导入模板
     */
    void downLoadUserTemplate();

    /**
     * 导出用户导入异常数据
     * @param errorToken
     * @throws Exception
     */
    void downLoadError(String errorToken) throws Exception;


    /**
     * 导出用户数据
     * @param userListReqDto
     */
    void downLoadUser(UserListReqDto userListReqDto);

    /**
     * 序列化测试接口
     * @param userDto
     * @return
     */
    Long serializable(UserDto userDto) throws IOException;

    /**
     * 测试rocketmq消息推送与消费
     * @param userDto
     */
    String send(UserDto userDto) throws Exception;



}
