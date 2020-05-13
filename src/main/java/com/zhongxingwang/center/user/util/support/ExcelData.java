package com.zhongxingwang.center.user.util.support;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/20 11:46
 */

@Data
public class ExcelData implements Serializable {

    private static final long serialVersionUID = 4454016249210520899L;

    /**
     * 表头
     */
    private List<String> titles;

    /**
     * 数据
     */
    private List<List<Object>> rows;

    /**
     * 页签名称
     */
    private String name;

}

