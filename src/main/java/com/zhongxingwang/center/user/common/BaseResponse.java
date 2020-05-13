package com.zhongxingwang.center.user.common;



/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/15 9:28
 */
public class BaseResponse<T> {

    private T data;
    private int code;
    private String msg;

    public BaseResponse(){}

    public BaseResponse(T data) {
        super();
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public BaseResponse(T data, int code, String msg) {
        super();
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
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
    public BaseResponse(T data, ErrorType errorType){
        this.code=errorType.getCode();
        this.msg=errorType.getMsg();
        this.data=data;
    }
    public BaseResponse(ErrorType errorType){
        this.code=errorType.getCode();
        this.msg=errorType.getMsg();
    }

}