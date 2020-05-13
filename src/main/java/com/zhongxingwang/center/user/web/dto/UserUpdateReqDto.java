package com.zhongxingwang.center.user.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserUpdateReqDto {
    @NotNull
    private Long id;
    private String userName;
    private String passWord;
    @Length(min = 2,max = 20,message = "用户名长度必须在2-20个字内！")
    private String name;
    @Min(value = 0,message = "年龄需大于0岁")
    @Max(value = 255,message = "年龄需小于255岁")
    private Integer age;
    @Range(min=0, max=1)
    private Integer sex;
    @Pattern(regexp = "0?(13|14|15|18|17)[0-9]{9}",message = "手机号码格式不对！")
    private String phone;
    @Pattern(regexp = "\\d{17}[\\d|x]|\\d{15}",message = "身份证号码格式不对！")
    private String identityCard;
    @Length(max = 200,message = "备注必须在0-200个字内")
    private String remark;

}
