package com.zhongxingwang.center.user.web.dto;

import com.zhongxingwang.center.user.common.PageInfoBaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPageInfoReqDto extends PageInfoBaseDto {
    private String name;
    private Integer minAge;
    private Integer maxAge;
    private Integer sex;
    private String identityCard;

}
