package com.zhongxingwang.center.user.web.dto;


import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    @NotNull(message = "名字不能为空！")
    @Length(min = 2,max = 20,message = "用户名长度必须在2-20个字内！")
    private String name;

    @NotNull(message = "用户名不能为空！")
    private String userName;

    @NotNull(message = "密码不能为空！")
    private String passWord;

    @Min(value = 0,message = "年龄需大于0岁")
    @Max(value = 255,message = "年龄需小于255岁")
    private Integer age;

    @Range(min=0, max=1)
    private Integer sex;

    @Pattern(regexp = "0?(13|14|15|18|17)[0-9]{9}",message = "手机号码格式不对！")
    private String phone;
    @NotNull(message = "身份证号码不能为空！")
    @Pattern(regexp = "\\d{17}[\\d|x]|\\d{15}",message = "身份证号码格式不对！")
    private String identityCard;
    @Length(max = 100,message = "备注必须在0-100个字内")
    private String remark;
    /**
     * 银行卡号
     */
    private String bankCardNumber;
    /**
     * 余额
     */
    private BigDecimal balance;

}
