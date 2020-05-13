package com.zhongxingwang.center.user.common;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/15 9:31
 */
public enum  ErrorType {
    //资源参数问题
    NOT_FOUND(404, "不存在此资源"),
    PARAMS_NULL(407, "参数不能为空"),
    FUNCTION_NOT_FOUND(500, "方法找不到"),
    DATABASE_ERROR(501, "数据库异常"),
    CONNECTION_ERROR(502, "网络请求失败"),
    METHOD_NOT_ALLOWED(405, "参数不合法"),
    OPERATION_EXCEL_ERROR(405, "导入文件异常"),


    //身份方面问题
    AUTHENTICATION_FAILED(101, "身份认证失败"),
    REQUEST_METHOD_ERROR(102, "请求类型不支持"),
    OPERATION_SCOPE_FAILED(103, "不支持此操作"),
    //系统基本码
    ERROR(100, "系统异常"),
    SUCCESS(200, "成功"),
    SOURCE_NULL(201, "数据为空"),;

    private int code;

    private String msg;

    ErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
