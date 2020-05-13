package com.zhongxingwang.center.user.web.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/28 14:12
 */
@Data
@ApiModel("用户导入模板")
public class UserImportTemplateDto {
    @Excel(name = "名称", width = 20, fixedIndex = 0)
    @NotNull(message = "名称不能为空")
    private String name;
    @Excel(name = "用户名称", width = 20, fixedIndex = 1)
    @NotNull(message = "用户名称不能为空")
    private String userName;
    @Excel(name = "密码", width = 20, fixedIndex = 2)
    @NotNull(message = "密码不能为空")
    private String passWord;
    @Excel(name = "年龄", width = 20, fixedIndex = 3)
    @NotNull(message = "年龄不能为空")
    private Integer age;
    @Excel(name = "性别", width = 20, fixedIndex = 4)
    @NotNull(message = "性别不能为空")
    private Integer sex;
    @Excel(name = "电话", width = 20, fixedIndex = 5)
    @NotNull(message = "电话不能为空")
    private String phone;
    @Excel(name = "身份证号码", width = 20, fixedIndex = 6)
    @NotNull(message = "身份证号码不能为空")
    private String identityCard;
    @Excel(name = "备注", width = 20, fixedIndex = 7)
    private String remark;
    @Excel(name = "银行卡号", width = 20, fixedIndex = 8)
    private String bankCardNumber;
    @Excel(name = "余额", width = 20, fixedIndex = 9)
    private BigDecimal balance;
}
