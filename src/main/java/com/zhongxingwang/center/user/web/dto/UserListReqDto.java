package com.zhongxingwang.center.user.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserListReqDto{
    private String name;
    private Integer minAge;
    private Integer maxAge;
    private Integer sex;
    private String identityCard;

}
