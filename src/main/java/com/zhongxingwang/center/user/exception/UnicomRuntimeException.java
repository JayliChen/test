package com.zhongxingwang.center.user.exception;

import com.zhongxingwang.center.user.common.ErrorType;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/15 10:04
 */
public class UnicomRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected int code;

    protected String msg;

    protected String message;//打印出的日志信息

    public UnicomRuntimeException(ErrorType enums, String message) {
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.message = message;
    }

    public UnicomRuntimeException(ErrorType enums) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public UnicomRuntimeException() {
        super();
    }

    public UnicomRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnicomRuntimeException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
