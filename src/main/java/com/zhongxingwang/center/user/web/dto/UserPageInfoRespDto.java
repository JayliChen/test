package com.zhongxingwang.center.user.web.dto;

import com.zhongxingwang.center.user.common.PageInfoBaseDto;
import com.zhongxingwang.center.user.web.entity.BaseEo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPageInfoRespDto extends BaseEo {
    private String name;
    private String userName;
    private String passWord;
    private Integer age;
    private Integer sex;
    private String phone;
    private String identityCard;
    private String remark;
}
