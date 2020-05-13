package com.zhongxingwang.center.user.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("excel信息导入结果")
public class ExcelImportResultView {
    @ApiModelProperty("导入总条数")
    private int totalNum;
    @ApiModelProperty("导入成功条数")
    private int successNum;
    @ApiModelProperty("导入失败条数")
    private int errorNum;
    @ApiModelProperty("导入失败的数据唯一token")
    private String errorToken;
}

