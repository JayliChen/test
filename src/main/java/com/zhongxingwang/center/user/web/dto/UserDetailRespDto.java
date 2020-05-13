package com.zhongxingwang.center.user.web.dto;

import com.zhongxingwang.center.user.web.entity.UserAccountEo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class UserDetailRespDto {

    private String userName;
    private String realPassWord;
    private byte[] passWord;
    private String name;
    private Integer age;
    private Integer sex;
    private String phone;
    private String identityCard;
    private String remark;
    public Long id;
    public String createPerson;
    public Date createTime;
    public String updatePerson;
    public Date updateTime;
    public Integer dr;
    List<UserAccountEo> userAccountEoList;

}
