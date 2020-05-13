package com.zhongxingwang.center.user.web.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/28 14:13
 */
@Data
@ApiModel("用户导入")
public class UserImportDto extends UserImportTemplateDto{
    @Excel(name = "导入失败原因", width = 50)
    private String errorMsg;
}
