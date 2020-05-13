package com.zhongxingwang.center.user.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 项目参数工具类
 *
 * @author wxlbobo
 * @date
 */
public class HttpServletUtil {

    /**
     * 获取request
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     *
     * @return request
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取 ServletRequestAttributes
     *
     * @return ServletRequestAttributes
     */
    private static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /** 返回错误信息 */
    public static void setErrorResponse() {
        //返回错误信息
        HttpServletResponse response = getResponse();
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, userId, token");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    }
}

