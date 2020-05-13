package com.zhongxingwang.center.user.web.dao;

import com.zhongxingwang.center.user.web.dto.UserDetailRespDto;
import com.zhongxingwang.center.user.web.dto.UserListReqDto;
import com.zhongxingwang.center.user.web.dto.UserPageInfoReqDto;
import com.zhongxingwang.center.user.web.entity.UserEo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserEo> {

    List<UserEo> getPageList(UserPageInfoReqDto userPageInfoReqDto);

    List<UserEo> list(UserListReqDto userListReqDto);

    int insertOne(UserEo userEo);

    void insertBatch(List<UserEo> userEoList);

    List<String> getIdentityCard();

    UserDetailRespDto selectById(Long id);
}
